
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
