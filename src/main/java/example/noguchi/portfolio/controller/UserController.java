package example.noguchi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.service.UserService;

@Controller
@RequestMapping("gamanbanking")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 新規登録画面を表示
    @GetMapping(value = "/new")
    public String create(User user,Model model) {
        model.addAttribute("user", user);
        return "user/new";
    }

    @PostMapping(value = "/add")
    public String add(User user) {
        userService.save(user);
        return "redirect:/login";    }
}
