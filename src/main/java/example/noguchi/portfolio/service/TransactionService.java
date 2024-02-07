
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public IntSummaryStatistics findAllUserBalance(){
        List<User> userList = userService.findAllUser();
        List<Integer> allUserBalance = new ArrayList<>();
        for (User user:userList) {
            allUserBalance.add(getTotalBalance(user.getId()));
        }
        return allUserBalance.stream().mapToInt(Integer::intValue).summaryStatistics();
    }
    // 全ユーザーの残高の平均を取得
    public Integer averageBalance() {
        return (int) findAllUserBalance().getAverage();
    }
    // 全ユーザーの残高からトップの金額を取得
    public Integer topBalance() {
        return findAllUserBalance().getMax();
    }
    // 全ユーザーの最多残高を取得
    public IntSummaryStatistics allUserBestBalance() {
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
        return transactionBalanceList.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
    }
    // 全ユーザーの最高残高の平均を取得
    public Integer averageBestBalance() {
        return (int) allUserBestBalance().getAverage();
    }
    // 全ユーザーの最高残高からトップの金額を取得
    public Integer topBestBalance() {
        return allUserBestBalance().getMax();
    }
    // ログインユーザーの最高残高を取得
    public Integer myBestBalance(User user) {
        return getTransactionBalance(user).stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics().getMax();
    }
}
