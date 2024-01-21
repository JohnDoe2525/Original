
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    // 従業員保存
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

//    // 従業員検索
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByName(username);
//    }
}
