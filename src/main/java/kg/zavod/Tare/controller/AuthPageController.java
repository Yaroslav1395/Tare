package kg.zavod.Tare.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthPageController {
    private static final Logger logger = LoggerFactory.getLogger(AuthPageController.class);

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        return "login";
    }
}
