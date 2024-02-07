
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    // ユーザーの取得
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }
}
