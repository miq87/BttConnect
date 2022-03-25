package pl.miq3l;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.CaseUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import pl.miq3l.domain.OrderUnit;
import pl.miq3l.domain.OrderDetail;
import pl.miq3l.domain.Product;

public class PhConnect {

    private static PhConnect INSTANCE;
    private Map<String, String> cookies;
    private final List<OrderUnit> orderUnits;
    private final File cookiesJsonFile;
    private Map<String, String> cfgVars;
    private final ObjectMapper mapper;
    private String baseUri;

    private PhConnect() {
        cookiesJsonFile = new File("src/main/resources/cookies.json");
        cookies = new HashMap<>();
        orderUnits = new ArrayList<>();
        mapper = new ObjectMapper();
        loadConfigFile();
    }

    public static PhConnect getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PhConnect();
        }
        return INSTANCE;
    }

    private void loadConfigFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            cfgVars = mapper.readValue(
                    new File("src/main/resources/config.json"),
                    new TypeReference<>(){});
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String getBaseUri() {
        try {
            Response response = Jsoup.connect(System.getenv("PH_URL") + cfgVars.get("introLoginUrl"))
                    .userAgent(cfgVars.get("userAgent")).method(Connection.Method.GET)
                    .followRedirects(true).execute();
            baseUri = response.url().toString();
            System.out.println("Base URI: " + baseUri);
            cookies.putAll(response.cookies());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return baseUri;
    }

    private Map<String, String> prepareFormData() {
        Map <String, String> formData = new HashMap<>();
        try {
            Document doc = Jsoup.connect(baseUri).get();
            Elements inputs = doc.select("form input");
            for (Element input : inputs) {
                formData.put(input.attr("name"), input.attr("value"));
            }
            formData.put("UID", System.getenv("PH_UID"));
            formData.put("PWD", System.getenv("PH_PWD"));
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return formData;
    }

    private void loadCookiesFromFile() {
        try {
            cookies = mapper.readValue(cookiesJsonFile, new TypeReference<>() {});
            System.out.println("Used cookies from the file: " + cookiesJsonFile.getPath());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveCookiesToFile() {
        try {
            mapper.writeValue(cookiesJsonFile, cookies);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void login() {
        if(cookies.size() > 0)
            return;

        if(cookiesJsonFile.exists()) {
            loadCookiesFromFile();
            return;
        }
        try {
            Response response = Jsoup.connect(getBaseUri())
                    .userAgent(cfgVars.get("userAgent")).method(Connection.Method.POST)
                    .data(prepareFormData()).followRedirects(true).execute();
            this.cookies.putAll(response.cookies());
            saveCookiesToFile();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Map<String, String> getSearchValues(String strValues) {
        Map<String, String> searchFormValues = new HashMap<>();
        String[] strArr = strValues.split("&");
        for (String str : strArr) {
            String[] strVal = str.split("=");
            searchFormValues.put(strVal[0], strVal.length > 1 ? strVal[1] : "");
        }
        return searchFormValues;
    }

    private Product mapToProduct(Map<String, String> map) {
        Map<String, String> camelCaseMap = new HashMap<>();

        map.forEach((key, value) -> {
            value = value.replace("*", "")
                    .replace("(", "-")
                    .replace(")", "")
                    .replace("%", "")
                    .replace("€", "");
            camelCaseMap.put(CaseUtils.toCamelCase(key, false, ' '), value);
        });
        Product product = mapper.convertValue(camelCaseMap, Product.class);

        product.setDateAdded(Timestamp.valueOf(LocalDateTime.now()));

        return product;
    }

    private OrderUnit mapToOrderUnit(Element el) {
        Map<String, String> orderUnitMap = new HashMap<>();
        final List<String> cols = OrderUnit.getFields();

        Elements els = el.select("td");

        for (int i = 0; i < els.size(); i++) {
            if(i == 0) {
                orderUnitMap.put("orderUrl", els.get(i).select("a").attr("abs:href"));
                orderUnitMap.put(cols.get(i), els.get(i).select("a").text());
            }
            else {
                orderUnitMap.put(cols.get(i+1), els.get(i).text());
            }
        }
        return mapper.convertValue(orderUnitMap, OrderUnit.class);
    }

    private OrderDetail mapToOrderDetail(Element el) {
        List<String> cols = OrderDetail.getFields();
        Map<String, String> map = new HashMap<>();

        try {
            String trackingUrl = Objects.requireNonNull(Objects.requireNonNull(el.nextElementSibling())
                            .select("b:contains(Tracking Number)").first()).parents().get(0)
                    .select("a").attr("href");
            map.put("trackingUrl", trackingUrl);
        }
        catch (NullPointerException e) {
            map.put("trackingUrl", "");
        }

        Elements tds = el.select("td");
        int i = 0;
        for (Element td : tds) {
            map.put(cols.get(i++), td.text().replace("€", ""));
        }
        return mapper.convertValue(map, OrderDetail.class);
    }

    public Product getProductFromPh(String productCode) {
        String strValues = "CatalogID=26082039&SParameter=5&SOperator=1" +
                "&SValueMultiple=&hdnSValueMultiple=&SValue=" +
                productCode + "&Find.x=10&Find.y=9&catalodIDchg=f";

        Map<String, String> searchFormValues = getSearchValues(strValues);
        Map<String, String> productDetails = new HashMap<>();

        try {
            Response response = Jsoup.connect(System.getenv("PH_URL") + cfgVars.get("searchFormUrl"))
                    .userAgent(cfgVars.get("userAgent")).method(Connection.Method.POST)
                    .cookies(cookies).followRedirects(true)
                    .data(searchFormValues)
                    .execute();
            Document doc = response.parse();

            Objects.requireNonNull(doc.select("font:contains(Available)").first())
                    .parents().get(3).select("div > font")
                    .forEach(c -> {
                        String[] strDetails = c.text().split(":");
                        productDetails.put(strDetails[0], strDetails.length > 1 ? strDetails[1].trim() : "");
                    });
        }
        catch (IOException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
        return mapToProduct(productDetails);
    }

    public List<OrderUnit> getOrderUnits(int limit) {
        orderUnits.clear();
        try {
            Document doc = Jsoup.connect(System.getenv("PH_URL") + cfgVars.get("orderListUrl"))
                    .userAgent(cfgVars.get("userAgent")).cookies(cookies).get();

            Objects.requireNonNull(doc.select("font:contains(Sales Order)").first())
                    .parents().get(2).select("tr")
                    .stream().skip(2).limit(limit)
                    .map(this::mapToOrderUnit).forEach(orderUnits::add);
        }
        catch (IOException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
        return orderUnits;
    }

    public List<OrderDetail> getOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(orderUnits.size() < 1) {
            System.out.println("Order List is empty");
            return orderDetails;
        }

        Optional<OrderUnit> order = orderUnits.stream()
                .filter(c -> c.getCustomerPo().equals(customerPo)).findFirst();

        if(order.isPresent()) {
            String orderUrl = order.get().getOrderUrl();

            try {
                Document doc = Jsoup.connect(orderUrl)
                        .userAgent(cfgVars.get("userAgent")).cookies(cookies).get();

                doc.select("font:contains(Part)")
                        .parents().get(2).select("tr[bgcolor=#EDEDF7]")
                        .stream()
                        .map(this::mapToOrderDetail)
                        .forEach(orderDetails::add);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return orderDetails;
    }

    public static void main(String[] args) {
        PhConnect ph = PhConnect.getInstance();
        ph.login();
        ph.getOrderUnits(4)
                .parallelStream()
                .map(c -> ph.getOrderDetailByCustomerPo(c.getCustomerPo()))
                .forEach(c -> {
                    System.out.println(c);
                    System.out.println("------------");
                });
        System.out.println(ph.getProductFromPh("590P-53327032-P00-U4V0"));
    }
}
