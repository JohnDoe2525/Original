package example.noguchi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    // 新規登録処理
    @PostMapping(value = "/add")
    public String add(@Validated User user,BindingResult res,Model model) {
        // 入力チェック
        if (res.hasErrors()) {
            return create(user,model);
        }
        userService.save(user);
        return "redirect:/login";
    }

    // ユーザー情報確認変更画面を表示
    @GetMapping(value = "/setting/menu/userinfo")
    public String userinfo() {
        return "user/userinfo";
    }
}
