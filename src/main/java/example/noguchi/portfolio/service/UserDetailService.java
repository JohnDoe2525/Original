package example.noguchi.portfolio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Exception:Username Not Found");
        }
        return new UserDetail(user.get());
    }
}