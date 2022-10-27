package msa.pseudo.lotto.userserivce.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.userserivce.dto.JoinResponse;
import msa.pseudo.lotto.userserivce.dto.LoginResponse;
import msa.pseudo.lotto.userserivce.dto.UserDto;
import msa.pseudo.lotto.userserivce.model.User;
import msa.pseudo.lotto.userserivce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public LoginResponse login(String userId, String password) {
        password = passwordHash(userId, password);
        User user = userRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 유효하지 않습니다."));

        LocalDateTime lastLoginAt = user.getLastLoginAt();
        user.updateLastLogin();
        return new LoginResponse(user.getUserId(), lastLoginAt);
    }

    public JoinResponse join(String userId, String password) {
        password = passwordHash(userId, password);
        User user = new User(userId, password);
        user = userRepository.save(user);
        user.updateLastLogin();
        return new JoinResponse(user);
    }

    public List<UserDto> findByIdGreaterThan(Long id) {
        return userRepository.findByIdGreaterThan(id)
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    private String passwordHash(String userId, String password) {
        return Hashing.sha256().hashString(password + userId, StandardCharsets.UTF_8).toString();
    }


    public UserDto getUser(String userId) {
        return null;
    }
}
