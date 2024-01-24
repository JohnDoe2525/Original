package example.noguchi.portfolio.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
@SQLRestriction("delete_flg = false")
public class Transaction {

    // 取引ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    // 日付
    @Column(nullable = false)
    private LocalDateTime transactionDate;

    //金額
    @Column(nullable = false)
    @NotNull(message="金額を入力してください。")
    @Positive(message="金額を正しく入力してください。")
    @Max(value=999999999,message="金額が大きすぎます。")
    private Integer price;

    // 商品カテゴリ
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull
    private Category category;

    // メモ
    @Length(max=100)
    @Column(nullable=true)
    private String memo;

    // ユーザーID
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // 削除フラグ(論理削除を行うため)
    @Column(columnDefinition="TINYINT",nullable = false)
    private boolean deleteFlg;

    // 登録日時
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
