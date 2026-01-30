package com.project.saas_billing_system.service.auth;

import com.project.saas_billing_system.dto.auth.LoginRequest;
import com.project.saas_billing_system.dto.auth.LoginResponse;
import com.project.saas_billing_system.dto.auth.RegisterRequest;
import com.project.saas_billing_system.exception.BusinessException;
import com.project.saas_billing_system.model.identity.Role;
import com.project.saas_billing_system.model.identity.User;
import com.project.saas_billing_system.repository.identity.UserRepository;
import com.project.saas_billing_system.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.getRoles().add(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = tokenProvider.generateToken(authentication);
        return new LoginResponse(
                token,
                "Bearer",
                tokenProvider.getExpirationMs() / 1000,
                request.getEmail()
        );
    }
}
