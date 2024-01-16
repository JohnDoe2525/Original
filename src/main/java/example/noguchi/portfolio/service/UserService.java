
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;

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
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 従業員保存
    @Transactional
    public User save(User user) {

        user.setRole(Role.GENERAL);

        user.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

}
