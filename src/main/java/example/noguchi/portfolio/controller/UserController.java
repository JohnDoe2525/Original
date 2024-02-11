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

import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.entity.User.UpdateValidation;
import example.noguchi.portfolio.service.UserDetail;
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
    public String userinfo(Model model,@AuthenticationPrincipal UserDetail userDetail) {
        model.addAttribute("id", userDetail.getEmployee().getId());
        return "user/userinfo";
    }
    // ユーザーネーム確認変更画面を表示
    @GetMapping(value = "/setting/menu/userinfo/username/{id}")
    public String username(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute User user ) {
        if (id == null) {
            Integer userid = userDetail.getEmployee().getId();
            user.setName(userService.findById(userid).getName());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", userService.findById(id));
        }
        return "user/update_username";
    }
    // ユーザーネーム更新処理
    @PostMapping(value = "/setting/user/update")
    public String updateUserName(@Validated(UpdateValidation.class) User user,BindingResult res,Model model,@AuthenticationPrincipal UserDetail userDetail) {
        // 入力チェック
        if (res.hasErrors()) {
            return username(null,model,userDetail,user);
        }
        User baseUser = userService.findById(userDetail.getEmployee().getId());
        baseUser.setName(user.getName());
        userService.update(baseUser);
        return "redirect:/gamanbanking/setting/menu/userinfo";
    }
}
