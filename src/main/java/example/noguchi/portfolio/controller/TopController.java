package example.noguchi.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TopController {

    // ログイン画面表示
    @GetMapping(value = "/login")
    public String login(Model model,@ModelAttribute("message") String message) {
        model.addAttribute("message", message);
        return "login/login";
    }

    // ログイン後のトップページ表示
    @GetMapping(value = "/")
    public String top() {
        return "redirect:/gamanbanking/home";
    }

}

