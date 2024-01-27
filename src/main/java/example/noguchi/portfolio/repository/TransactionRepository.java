package example.noguchi.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import example.noguchi.portfolio.entity.Transaction;
import example.noguchi.portfolio.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    //　ユーザーごとの残高を取得(取引額の合計)
    @Query(value = "SELECT SUM(price) FROM transactions where user_id = ?1", nativeQuery = true)
    public Integer getTotalBalance(Integer id);
    public List<Transaction> findByUser(User user);
}