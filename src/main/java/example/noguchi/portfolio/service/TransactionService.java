
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.noguchi.portfolio.entity.Transaction;
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
        return transactionRepository.getTotalBalance(id);
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
    public List<Transaction> findAllById(Integer id){
        return transactionRepository.findAllById(null);
    }
}
