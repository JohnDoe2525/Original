package example.noguchi.portfolio.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@SQLRestriction("delete_flg = false")
public class User {

    public interface UsernameValidation{
    };
    public interface PasswordValidation{
    };
    public interface MailAddressValidation{
    };

    public static enum Role {
        GENERAL("一般"), ADMIN("管理者");

        private String name;

        private Role(String name) {
            this.name = name;
        }

        public String getValue() {
            return this.name;
        }
    }

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList;

    // ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ユーザーID
    @Column(length = 20, nullable = false)

    @Pattern(regexp = "^[a-zA-Z0-9]+$",groups= {UsernameValidation.class})
    @Length(min=4,max = 20,message="4文字～20文字で入力してください",groups= {UsernameValidation.class})
    @Length(min=4,max = 20,message="4文字～20文字で入力してください")
    private String name;

    // メールアドレス
    @Column(length = 255, nullable = false)
    @Email(groups= {MailAddressValidation.class})
    @Email
    @NotEmpty(groups= {MailAddressValidation.class})
    @NotEmpty
    @Length(max = 100)
    private String mailAddress;

    // 権限
    @Column(columnDefinition="VARCHAR(10)", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // パスワード
    @Column(length = 255, nullable = false)
    @Length(min=4,message="4文字以上で入力してください")
    @Length(min=4,message="4文字以上で入力してください",groups= {PasswordValidation.class})
    private String password;

    //　パスワード確認用
    @Transient
    private String confirmPassword;

    // 削除フラグ(論理削除を行うため)
    @Column(columnDefinition="TINYINT", nullable = false)
    private boolean deleteFlg;

    // 登録日時
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
