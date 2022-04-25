package pl.miq3l.bttconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.miq3l.bttconnect.domain.Inverter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BTT {
    private static BTT INSTANCE;
    private final ObjectMapper mapper = new ObjectMapper();
    private final TreeMap<String, Inverter> inverters = new TreeMap<>();

    private BTT() { }

    public static BTT getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BTT();
        }
        return INSTANCE;
    }

    public Inverter mapToInverter(Element el) {
        Map<String, String> inverterParameters = new HashMap<>();

        Elements tds = el.select("td");

        AtomicInteger i = new AtomicInteger();
        tds.stream().skip(1).limit(5).forEach(td -> {
            inverterParameters.put(Inverter.getFields().get(i.getAndIncrement()), td.text());
        });

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

}
