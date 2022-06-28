package pl.miq3l.bttconnect.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.miq3l.bttconnect.components.ExcelHandler;
import pl.miq3l.bttconnect.service.PartsService;

import javax.annotation.PostConstruct;

@Configuration
public class InitialDataConfiguration {

    private final PartsService partsService;
    private final ExcelHandler eh;

    @Autowired
    InitialDataConfiguration(PartsService partsService, ExcelHandler eh) {
        this.partsService = partsService;
        this.eh = eh;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Started after Spring boot application !");
        eh.read();
        partsService.saveAll(eh.getParts());
    }

}
