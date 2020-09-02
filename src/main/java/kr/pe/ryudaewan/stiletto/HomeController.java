package kr.pe.ryudaewan.stiletto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    String home(Model model) {
        log.info("Spring Boot Thymeleaf Configuration Example");
        model.addAttribute("greeting", "Hello, Stiletto");

        return "home";
    }
}
