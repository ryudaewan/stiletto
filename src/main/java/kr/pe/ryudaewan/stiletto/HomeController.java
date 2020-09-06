package kr.pe.ryudaewan.stiletto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
        //String home(Model model) {
    String home(Model model) {
        //model.addAttribute("greeting_ko", "안녕하세요.");
        //TODO: 로그인 상태가 아니면 로그인 화면으로, 로그인이 되었으면 마이페이지로.
        String respUrl = "sign-in/signIn";

        /*model.addAttribute("greeting_en", "Greeting.");
        model.addAttribute("greeting_fr", "Bonjour.");
        model.addAttribute("greeting_de", "Halo.");
        model.addAttribute("greeting_zh-CN", "你好.");
        model.addAttribute("greeting_ja", "こんにちは.");
        model.addAttribute("greeting_vi", "Xin chào.");*/

        return respUrl;
    }
}
