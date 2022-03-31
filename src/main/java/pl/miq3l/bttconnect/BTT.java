package pl.miq3l.bttconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.miq3l.bttconnect.domain.Inverter;
import pl.miq3l.bttconnect.domain.Product;

import java.io.IOException;
import java.util.*;

public class BTT {
    private static BTT INSTANCE;
    private final ObjectMapper mapper;
    private final List<Product> products;
    private final TreeMap<String, Inverter> inverters;

    private BTT() {
        mapper = new ObjectMapper();
        products = new ArrayList<>();
        inverters = new TreeMap<>();
    }

    public static BTT getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BTT();
        }
        return INSTANCE;
    }

    public Inverter mapToInverter(Element el) {
        Map<String, String> inverterParameters = new HashMap<>();

        Elements tds = el.select("td");

        for (int i = 1; i < tds.size(); i++) {
            inverterParameters.put(Inverter.getFields().get(i-1), tds.get(i).text());
        }
        return mapper.convertValue(inverterParameters, Inverter.class);
    }

    public TreeMap<String, Inverter> loadAllInvertersFromBTT() {
        StringBuilder sb = new StringBuilder("https://www.bttautomatyka.pl/index.php/cennik/?go=ac10");
        inverters.clear();

        try {
            Jsoup.connect(sb.toString()).get()
                    .select(".pricelist_ac10 > table > tbody > tr")
                    .stream().map(this::mapToInverter)
                    .forEach(c -> inverters.put(c.getPart(), c));

            Jsoup.connect(sb.append("_ip66").toString()).get()
                    .select(".pricelist_ac10 > table > tbody > tr")
                    .stream().map(this::mapToInverter)
                    .forEach(c -> inverters.put(c.getPart(), c));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inverters;
    }

//    public List<Product> loadAllProductsFromPh(int limit) {
//        Set<String> inverterCodes = loadAllInvertersFromBTT().keySet();
//        PhConnect ph = PhConnect.getInstance();
//        ph.login();
//        products.clear();
//
//        if(limit > inverterCodes.size()) {
//            limit = inverterCodes.size();
//        }
//
//        ProgressBar pb = new ProgressBar("BTT", limit);
//
//        inverterCodes.parallelStream().limit(limit).forEach(c -> {
//            products.add(ph.getProductFromPh(c));
//            pb.step();
//            pb.setExtraMessage("Reading...");
//        });
//        pb.close();
//        return products;
//    }
//
//    public static void main(String[] args) {
//        BTT.getInstance().loadAllInvertersFromBTT().forEach((key, value) -> {
//            System.out.println(key + " = " + value);
//        });
//        BTT.getInstance().loadAllProductsFromPh(5).forEach(System.out::println);
//    }
}
