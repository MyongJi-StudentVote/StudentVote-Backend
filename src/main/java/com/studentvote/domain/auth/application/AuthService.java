package com.studentvote.domain.auth.application;

import com.studentvote.domain.auth.domain.Token;
import com.studentvote.domain.auth.domain.repository.TokenRepository;
import com.studentvote.domain.auth.dto.request.SignInRequest;
import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.auth.dto.response.SignInResponse;
import com.studentvote.domain.auth.dto.response.WhoAmIResponse;
import com.studentvote.domain.auth.exception.AlreadyExistIdException;
import com.studentvote.domain.auth.exception.EmailNotFoundException;
import com.studentvote.domain.auth.exception.InvalidPasswordException;
import com.studentvote.domain.user.domain.Governance;
import com.studentvote.domain.user.domain.Role;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.GovernanceRepository;
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
    private final TokenRepository tokenRepository;
    private final GovernanceRepository governanceRepository;

    private final JWTUtil jwtUtil;

    @Transactional
    public Message signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.email();
        String password = signUpRequest.password();
        String name = signUpRequest.name();
        Long governanceId = signUpRequest.governanceId();

        Governance governance = governanceRepository.findById(governanceId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 단과대/학과 id 입니다."));

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new AlreadyExistIdException();
        }

        User user = User.of(email, bCryptPasswordEncoder.encode(password), name, governance);

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

        tokenRepository.save(Token.builder().email(email).refreshToken(refreshToken).build());

        SignInResponse signInResponse = SignInResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .role(role)
                .build();

        return signInResponse;
    }

    public WhoAmIResponse whoAmI(CustomUserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.user().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."));

        WhoAmIResponse whoAmIResponse = WhoAmIResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .governanceId(user.getGovernance().getId())
                .governanceName(user.getGovernance().getGovernanceName())
                .build();

        return whoAmIResponse;
    }
}
