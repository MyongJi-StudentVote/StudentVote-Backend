package com.studentvote.domain.auth.application;

import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.domain.auth.exception.AlreadyExistIdException;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.UserRepository;
import com.studentvote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Message signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.email();
        String password = signUpRequest.password();
        String name = signUpRequest.name();

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new AlreadyExistIdException();
        }

        User user = User.of(email, bCryptPasswordEncoder.encode(password), name);

        userRepository.save(user);

        return Message
                .builder()
                .message("회원가입이 완료되었습니다.")
                .build();
    }
}
