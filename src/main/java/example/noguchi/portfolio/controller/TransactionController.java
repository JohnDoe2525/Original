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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService,CategoryService categoryService,UserService userService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // ホーム画面を表示
    @GetMapping(value = "/home")
    public String home(Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute Transaction transaction,@ModelAttribute("message") String message) {
        // ユーザーの残高表示用＆桁区切り変換
        Integer userId = userDetail.getEmployee().getId();
        // ユーザID表示用
        model.addAttribute("loginUser", userService.findById(userId));
        model.addAttribute("totalBalance", String.format("%,d 円",transactionService.getTotalBalance(userId)));
        // カテゴリ選択用リスト
        model.addAttribute("categoryList",categoryService.getAllCategory());
        model.addAttribute("message", message);
        return "transaction/home";
    }

    // 設定メニュー画面を表示
    @GetMapping(value = "/setting/menu")
    public String setting() {
        return "setting/menu";
    }

    // 入金処理
    @PostMapping(value = "/home/add")
    public String deposit(Model model,@Validated Transaction transaction,BindingResult res,@AuthenticationPrincipal UserDetail userDetail,RedirectAttributes redirectAttributes) {
        // 入力チェック
        if(res.hasErrors()) {
            return home(model,userDetail,transaction,null);
        }
        // 残高上限チェック
        Integer userId = userDetail.getEmployee().getId();
        Integer totalBalance = transactionService.getTotalBalance(userId);
        if(totalBalance + transaction.getPrice() > 999999999) {
            model.addAttribute("limitError","残高が上限に達しています");
            return home(model,userDetail,transaction,null);
        }
        // カテゴリー未選択エラー
        if(transaction.getCategory().getId() == null) {
            model.addAttribute("categoryError","カテゴリーを選択してください");
            return home(model,userDetail,transaction,null);
        }
        // 入金処理呼び出し
        transaction.setUser(userDetail.getEmployee());
        transactionService.save(transaction);
        redirectAttributes.addFlashAttribute("message", "入金が完了しました");
        return "redirect:/gamanbanking/home";
    }

    // 出金画面を表示
    @GetMapping(value = "/home/withdraw")
    public String withdrawView(Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute Transaction transaction) {
        // ユーザーの残高表示用＆桁区切り変換
        Integer userId = userDetail.getEmployee().getId();
        // ユーザーID表示用
        model.addAttribute("loginUser", userService.findById(userId));
        String totalBalance = String.format("%,d 円",transactionService.getTotalBalance(userId));
        if (totalBalance == null) {
            model.addAttribute("totalBalance", 0);
        } else {
            model.addAttribute("totalBalance", totalBalance);
        }
        // カテゴリ選択用リスト
        model.addAttribute("categoryList",categoryService.getAllCategory());
        return "transaction/withdraw";
    }

    // 出金処理
    @PostMapping(value = "/home/withdraw/add")
    public String withdraw(Model model,@Validated Transaction transaction,BindingResult res,@AuthenticationPrincipal UserDetail userDetail,RedirectAttributes redirectAttributes) {
        // 入力チェック
        if(res.hasErrors()) {
            return home(model,userDetail,transaction,null);
        }
        // 残高下限チェック
        Integer userId = userDetail.getEmployee().getId();
        Integer totalBalance = transactionService.getTotalBalance(userId);
        if(totalBalance + transaction.getPrice() < -999999999) {
            model.addAttribute("limitError","残高が下限に達しています");
            return home(model,userDetail,transaction,null);
        }
        // カテゴリー未選択エラー
        if(transaction.getCategory().getId() == null) {
            model.addAttribute("categoryError","カテゴリーを選択してください");
            return home(model,userDetail,transaction,null);
        }
        // 出金処理呼び出し(金額を負の値に)
        transaction.setPrice(transaction.getPrice()*-1);
        transaction.setUser(userDetail.getEmployee());
        transactionService.save(transaction);
        redirectAttributes.addFlashAttribute("message", "出金が完了しました");
        return "redirect:/gamanbanking/home";
    }

    // 通帳画面表示
    @GetMapping(value = "/home/list")
    public String list(Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute Transaction transaction,@ModelAttribute("message") String message) {
        Integer userid = userDetail.getEmployee().getId();
        User user = userService.findById(userid);
        model.addAttribute("loginUser", user);
        model.addAttribute("transactionList", transactionService.findByUser(user));
        model.addAttribute("balanceList",transactionService.getTransactionBalance(user));
        model.addAttribute("message", message);
        return "transaction/list";
    }

    // 編集画面
    @GetMapping(value = "/home/detail/{id}")
    public String detail(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail,@ModelAttribute Transaction transaction) {
        Integer userid = userDetail.getEmployee().getId();
        User user = userService.findById(userid);
        model.addAttribute("loginUser", user);
        model.addAttribute("categoryList",categoryService.getAllCategory());
        if(id == null) {
            model.addAttribute("transaction", transaction);
        } else {
            model.addAttribute("transaction",transactionService.findById(id).get());
        }
        return "transaction/detail";
    }

    // 更新処理
    @PostMapping(value = "/home/update/{id}")
    public String update(@PathVariable("id") Integer id,Model model,@Validated Transaction transaction,BindingResult res,@AuthenticationPrincipal UserDetail userDetail,RedirectAttributes redirectAttributes) {
        // 入力チェック
        if(res.hasErrors()) {
            return detail(null,model,userDetail,transaction);
        }
        // 残高チェック
        Integer userId = userDetail.getEmployee().getId();
        Integer totalBalance = transactionService.getTotalBalance(userId);
        Integer target = totalBalance + transaction.getPrice();
        if(target < -999999999 || target > 999999999) {
            model.addAttribute("limitError","残高が制限に達しています");
            return detail(null,model,userDetail,transaction);
        }
        redirectAttributes.addFlashAttribute("message", "変更が完了しました");
        transactionService.update(id,transaction);
        return "redirect:/gamanbanking/home/list";
    }

    // 取引の削除処理
    @PostMapping(value = "/home/delete/{id}")
    public String update(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "削除が完了しました");
        transactionService.delete(id);
        return "redirect:/gamanbanking/home/list";
    }

    // 統計画面を表示
    @GetMapping(value = "/home/statistics/{id}")
    public String statistics(Model model,@PathVariable("id") Integer id) {
        // 統計
        // 残高 // 平均
        model.addAttribute("averageBalance", transactionService.averageBalance());
        // 残高 // TOP
        model.addAttribute("topBalance", transactionService.topBalance());
        // 残高 // あなた
        model.addAttribute("myBalance", transactionService.getTotalBalance(id));
        // 最高残高 // 平均
        model.addAttribute("averageBestBalance", transactionService.averageBestBalance());
        // 最高残高 // TOP
        model.addAttribute("topBestBalance", transactionService.topBestBalance());
        // 最高残高 //　あなた
        model.addAttribute("myBestBalance", transactionService.myBestBalance(userService.findById(id)));
        // 入金回数 // 平均
        model.addAttribute("averageCountPayment", transactionService.averageCountPaymentAllUser());
        // 入金回数 // TOP
        model.addAttribute("topCountPayment", transactionService.bestCountPayment());
        // 入金回数 // あなた
        model.addAttribute("myCountPayment", transactionService.countPayment(userService.findById(id)));
        // ランキング
        // 残高
        model.addAttribute("rankingBalance", transactionService.rankingBalance(id));
        // 最高残高
        model.addAttribute("rankingBestBalance",transactionService.rankingBestBalance(userService.findById(id)));
        // 入金回数
        model.addAttribute("rankingCountPayment",transactionService.rankingCountPayment(userService.findById(id)));
        // ランキング用ユーザー数
        model.addAttribute("countUsers", userService.countUsers());
        model.addAttribute("id", id);
        return "transaction/statistics";
    }
}
