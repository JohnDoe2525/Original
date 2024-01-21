package example.noguchi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import example.noguchi.portfolio.entity.Transaction;
import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.service.UserDetail;
import example.noguchi.portfolio.service.UserService;

@Controller
@RequestMapping("gamanbanking")
public class TransactionController {

    // ホーム画面を表示
    @GetMapping(value = "/home")
    public String home(Model model,@AuthenticationPrincipal UserDetail userDetail) {
        model.addAttribute("user", userDetail.getUsername());
        return "transaction/home";
    }
}
