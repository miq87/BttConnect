package pl.miq3l.bttconnect.api;

import org.apache.maven.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping
public class MainApi {
    @GetMapping
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("OK");
    }
}
