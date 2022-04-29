package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.bttconnect.domain.Part;
import pl.miq3l.bttconnect.service.PartsService;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartApi {

    private final PartsService partsService;

    @Autowired
    public PartApi(PartsService partsService) {
        this.partsService = partsService;
    }

    @GetMapping("/all")
    public List<Part> findAll() {
        return partsService.findAll();
    }
}
