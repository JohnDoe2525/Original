
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.entity.User.Role;
import example.noguchi.portfolio.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 新規ユーザー登録
    @Transactional
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.GENERAL);
        user.setDeleteFlg(false);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }
    // 全ユーザーの取得
    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    // 全ユーザー数の取得
    public Integer countUsers() {
        return findAllUser().size();
    }
    // ユーザーの取得
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }
    // ユーザーネームを取得
    public String getUserName(User user) {
        return user.getName();
    }
    // ユーザー情報のアップデート
    @Transactional
    public User update(User user,@AuthenticationPrincipal UserDetail userDetail) {
        User baseUser = findById(userDetail.getEmployee().getId());
        if(user.getName() != null) {
            baseUser.setName(user.getName());
        } else if(user.getPassword() != null) {
            baseUser.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if(user.getMailAddress() != null) {
            baseUser.setMailAddress(user.getMailAddress());
        }
        LocalDateTime now = LocalDateTime.now();
        user.setUpdatedAt(now);
        return userRepository.save(baseUser);
    }
}
