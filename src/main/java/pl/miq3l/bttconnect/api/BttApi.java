package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.bttconnect.domain.Inverter;
import pl.miq3l.bttconnect.service.BttService;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/btt")
public class BttApi {

    private final BttService bttService;

    @Autowired
    public BttApi(BttService bttService) {
        this.bttService = bttService;
    }

    @GetMapping("/all")
    public List<Inverter> findAll() {
        return bttService.findAll();
    }

    @GetMapping("/loadAll")
    public TreeMap<String, Inverter> loadAllInvertersFromBTT() {
        return bttService.loadAllInvertersFromBTT();
    }

}
