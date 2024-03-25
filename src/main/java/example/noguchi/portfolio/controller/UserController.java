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
import example.noguchi.portfolio.entity.User.PasswordValidation;
import example.noguchi.portfolio.entity.User.UsernameValidation;
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
        // 重複チェック
        if (userService.useridExists(user)) {
           model.addAttribute("existsError", "このユーザーIDは既に使われています");
        }
        return "user/new";
    }

    // 新規登録処理
    @PostMapping(value = "/add")
    public String add(@Validated(UsernameValidation.class) User user,BindingResult res,Model model) {
        // 入力チェック
        if (res.hasErrors()) {
            return create(user,model);
        }
        // 重複チェック
        if (userService.useridExists(user)) {
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
    // ユーザーID確認変更画面を表示
    @GetMapping(value = "/setting/menu/userinfo/username/{id}")
    public String username(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute User user ) {
        if (id == null) {
            if (userService.useridExists(user)) {
                model.addAttribute("existsError", "このユーザー名は使用されています");
            }
            Integer userid = userDetail.getEmployee().getId();
            user.setName(userService.findById(userid).getName());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", userService.findById(id));
        }
        return "user/update_username";
    }
    // パスワード変更画面を表示
    @GetMapping(value = "/setting/menu/userinfo/password/{id}")
    public String password(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute User user ) {
        if (id == null) {
            model.addAttribute("unmatch", "パスワードが一致しません");
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", userService.findById(id));
        }
        return "user/update_password";
    }
    // メールアドレス変更画面を表示
/*     @GetMapping(value = "/setting/menu/userinfo/mailaddress/{id}")
    public String mailAddress(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute User user ) {
        if (id == null) {
            Integer userid = userDetail.getEmployee().getId();
            user.setMailAddress(userService.findById(userid).getMailAddress());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", userService.findById(id));
        }
        return "user/update_mailaddress";
    } */
    // ユーザーID更新処理
    @PostMapping(value = "/setting/user/update/username")
    public String updateUserName(@Validated(UsernameValidation.class) User user,BindingResult res,Model model,@AuthenticationPrincipal UserDetail userDetail) {
        // 入力チェック
        if (res.hasErrors()) {
            return username(null,model,userDetail,user);
        }
        // 重複チェック
        if (userService.useridExists(user)) {
            return username(null,model,userDetail,user);
        }
        userService.update(user,userDetail);
        return "redirect:/gamanbanking/setting/menu/userinfo";
    }
    // パスワード更新処理
    @PostMapping(value = "/setting/user/update/password")
    public String updatePassword(@Validated(PasswordValidation.class) User user,BindingResult res,Model model,@AuthenticationPrincipal UserDetail userDetail) {
        // 入力チェック
        if (res.hasErrors()) {
            return password(null,model,userDetail,user);
        }
        if(user.getPassword().equals(user.getConfirmPassword()) != true) {
            return password(null,model,userDetail,user);
        }
        userService.update(user,userDetail);
        return "redirect:/gamanbanking/setting/menu/userinfo";
    }
/*     // メールアドレス更新処理
    @PostMapping(value = "/setting/user/update/mailaddress")
    public String updateMailAddress(@Validated(MailAddressValidation.class) User user,BindingResult res,Model model,@AuthenticationPrincipal UserDetail userDetail) {
        // 入力チェック
        if (res.hasErrors()) {
            return mailAddress(null,model,userDetail,user);
        }
        userService.update(user,userDetail);
        return "redirect:/gamanbanking/setting/menu/userinfo";
    } */
}
