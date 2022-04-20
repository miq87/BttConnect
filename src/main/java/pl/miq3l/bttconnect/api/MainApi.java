package pl.miq3l.bttconnect.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainApi {
    @GetMapping
    public String index() {
        return "It's ok";
    }
}
