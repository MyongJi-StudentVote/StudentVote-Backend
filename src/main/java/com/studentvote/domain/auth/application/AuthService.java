package com.studentvote.domain.auth.application;

import com.studentvote.domain.auth.dto.request.SignInRequest;
import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.domain.auth.dto.response.SignInResponse;
import com.studentvote.domain.auth.exception.AlreadyExistIdException;
import com.studentvote.domain.auth.exception.EmailNotFoundException;
import com.studentvote.domain.auth.exception.InvalidPasswordException;
import com.studentvote.domain.user.domain.Role;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.UserRepository;
import com.studentvote.global.config.security.jwt.JWTUtil;
import com.studentvote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

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

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.email();
        String password = signInRequest.password();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, password, null);

        Authentication authenticate = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Role role = user.getRole();

        String accessToken = jwtUtil.createJwt(email, role.toString(), 36000000000L);
        String refreshToken = jwtUtil.createJwt(email, role.toString(), 360000000000L);

        SignInResponse signInResponse = SignInResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .role(role)
                .build();

        return signInResponse;
    }
}
