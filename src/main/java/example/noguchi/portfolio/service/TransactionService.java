
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import example.noguchi.portfolio.entity.Transaction;
import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }
    // ユーザーの残高を取得
    public Integer getTotalBalance(Integer id) {
        Integer totalBalance = transactionRepository.getTotalBalance(id);
        totalBalance = (totalBalance == null) ? 0 : totalBalance;
        return totalBalance;
    }
    // 入出金処理
    @Transactional
    public Transaction save(Transaction transaction) {
        transaction.setDeleteFlg(false);
        LocalDateTime now = LocalDateTime.now();
        transaction.setTransactionDate(now);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        return transactionRepository.save(transaction);

    }
    // 取引IDから取引を取得
    public Optional<Transaction> findById(Integer id) {
        return transactionRepository.findById(id);
    }
    // ユーザーの全取引を取得
    public List<Transaction> findByUser(User user){
        return transactionRepository.findByUser(user);
    }
    // ユーザーの取引時点の残高を取得してリストに格納
    public List<Integer> getTransactionBalance(User user){
        List<Transaction> transactionList = findByUser(user);
        List<Integer> balanceList = new ArrayList<>();
        Integer totalPrice = 0;
        for (Transaction transaction: transactionList){
            totalPrice += transaction.getPrice();
            balanceList.add(totalPrice);
        }

        return balanceList;
    }
    // 変更処理
    @Transactional
    public Transaction update(Integer id,Transaction transaction) {
        Optional<Transaction> baseTransaction = transactionRepository.findById(id);
        transaction.setDeleteFlg(false);
        LocalDateTime now = LocalDateTime.now();
        transaction.setTransactionDate(baseTransaction.get().getTransactionDate());
        transaction.setTransactionId(id);
        transaction.setUser(baseTransaction.get().getUser());
        transaction.setCreatedAt(transactionRepository.findById(id).get().getCreatedAt());
        transaction.setUpdatedAt(now);

        return transactionRepository.save(transaction);
    }
    // 削除処理
    @Transactional
    public void delete(Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        transaction.get().setDeleteFlg(true);
        LocalDateTime now = LocalDateTime.now();
        transaction.get().setUpdatedAt(now);

        transactionRepository.save(transaction.get());
    }
    // 全ユーザーの残高を取得
    public List<Integer> findAllUserBalance(){
        List<User> userList = userService.findAllUser();
        List<Integer> allUserBalance = new ArrayList<>();
        for (User user:userList) {
            allUserBalance.add(getTotalBalance(user.getId()));
        }
        return allUserBalance;
    }
    // 全ユーザーの残高の平均を取得
    public Integer averageBalance() {
        return (int) findAllUserBalance().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getAverage();
    }
    // 全ユーザーの残高からトップの金額を取得
    public Integer topBalance() {
        return findAllUserBalance().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getMax();
    }
    // 全ユーザーの最高残高を取得
    public List<Integer> allUserBestBalance() {
        List<User> userList = userService.findAllUser();
        List<Integer> transactionBalanceList = new ArrayList<>();
        for (User user:userList) {
            if (getTransactionBalance(user).isEmpty()) {
                transactionBalanceList.add(0);
            } else {
                transactionBalanceList.add(getTransactionBalance(user)
                        .stream().mapToInt(Integer::intValue)
                        .summaryStatistics().getMax());
            }

        }
        return transactionBalanceList;
    }
    // 全ユーザーの最高残高の平均を取得
    public Integer averageBestBalance() {
        return (int) allUserBestBalance().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getAverage();
    }
    // 全ユーザーの最高残高からトップの金額を取得
    public Integer topBestBalance() {
        return allUserBestBalance().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getMax();
    }
    // ログインユーザーの最高残高を取得
    public Integer myBestBalance(User user) {
        List<Integer> transactionList = getTransactionBalance(user);
        if (transactionList.isEmpty()) {
            return 0;
        } else {
            return getTransactionBalance(user).stream()
                    .mapToInt(Integer::intValue)
                    .summaryStatistics().getMax();
        }
    }
    // ユーザーの入金回数を取得
    public Integer countPayment(User user) {
        Integer count = 0;
        List<Transaction> targetList = transactionRepository.findByUser(user);
        for (Transaction target : targetList) {
            if(target.getPrice()>0) {
                count += 1;
            }
        }
        return count;
    }
    // 全ユーザーの入金回数を取得
    public List<Integer> countPaymentAllUser() {
        List<Integer> countList = new ArrayList<>();
        List<User> userList = userService.findAllUser();
        for(User user : userList) {
            countList.add(countPayment(user));
        }
        return countList;
    }
    // 全ユーザーの入金回数の平均を取得
    public Integer averageCountPaymentAllUser() {
        return (int) countPaymentAllUser().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getAverage();
    }
    // 全ユーザーの入金回数の最多回数を取得
    public Integer bestCountPayment() {
        return countPaymentAllUser().stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getMax();
    }
    // 残高のランキング
    public Integer rankingBalance(Integer id) {
        List<Integer> balanceList = findAllUserBalance();
        Collections.sort(balanceList, Collections.reverseOrder());
        return balanceList.indexOf(getTotalBalance(id))+1;
    }
    // 最高残高のランキング
    public Integer rankingBestBalance(User user) {
        List<Integer> BestBalanceList = allUserBestBalance();
        Collections.sort(BestBalanceList, Collections.reverseOrder());
        return BestBalanceList.indexOf(myBestBalance(user))+1;
    }
    // 最高残高のランキング
    public Integer rankingCountPayment(User user) {
        List<Integer> countList = countPaymentAllUser();
        Collections.sort(countList, Collections.reverseOrder());
        return countList.indexOf(countPayment(user))+1;
    }
}
