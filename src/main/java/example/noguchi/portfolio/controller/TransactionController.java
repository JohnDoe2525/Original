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
import example.noguchi.portfolio.service.CategoryService;
import example.noguchi.portfolio.service.TransactionService;
import example.noguchi.portfolio.service.UserDetail;
import example.noguchi.portfolio.service.UserService;

@Controller
@RequestMapping("gamanbanking")
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;

    @Autowired
    public TransactionController(TransactionService transactionService,CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    // ホーム画面を表示
    @GetMapping(value = "/home")
    public String home(Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute Transaction transaction) {
        // ユーザーネーム表示用
        model.addAttribute("loginUser", userDetail.getEmployee());
        // ユーザーの残高表示用
        Integer userId = userDetail.getEmployee().getId();
        Integer totalBalance = transactionService.getTotalBalance(userId);
        if (totalBalance == null) {
            model.addAttribute("totalBalance", 0);
        } else {
            model.addAttribute("totalBalance", totalBalance);
        }
        // カテゴリ選択用リスト
        model.addAttribute("categoryList",categoryService.getAllCategory());
        
        return "transaction/home";

    }

    @PostMapping(value = "/home/add")
    public String create(Model model,@Validated Transaction transaction,BindingResult res,@AuthenticationPrincipal UserDetail userDetail) {
        // 入力チェック
        if(res.hasErrors()) {
            return home(model,userDetail,transaction);
        }
        // 残高上限チェック
        Integer userId = userDetail.getEmployee().getId();
        Integer totalBalance = transactionService.getTotalBalance(userId);
        if(totalBalance + transaction.getPrice() > 999999999) {
            model.addAttribute("limitError","残高が上限に達しています");
            return home(model,userDetail,transaction);
        }
        // カテゴリー未選択エラー
        if(transaction.getCategory().getId() == null) {
            model.addAttribute("categoryError","カテゴリーを選択してください");
            return home(model,userDetail,transaction);
        }
        // 入金処理呼び出し
        transaction.setUser(userDetail.getEmployee());
        transactionService.save(transaction);
        return "redirect:/gamanbanking/home";
    }
}
