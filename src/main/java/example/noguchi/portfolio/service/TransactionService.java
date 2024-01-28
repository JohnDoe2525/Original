
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // 入金処理
    @Transactional
    public Transaction save(Transaction transaction) {
        transaction.setDeleteFlg(false);
        LocalDateTime now = LocalDateTime.now();
        transaction.setTransactionDate(now);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        return transactionRepository.save(transaction);

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
}
