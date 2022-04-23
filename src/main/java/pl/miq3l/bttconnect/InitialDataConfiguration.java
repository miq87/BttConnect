package pl.miq3l.bttconnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.miq3l.bttconnect.service.InverterService;

import javax.annotation.PostConstruct;

@Configuration
public class InitialDataConfiguration {

    @Autowired
    private InverterService inverterService;

    @PostConstruct
    public void postConstruct() {
        System.out.println("Started after Spring boot application !");

        ExcelHandler eh = ExcelHandler.getInstance();
        eh.read();

        inverterService.saveAll(eh.getInverters());
    }

}
