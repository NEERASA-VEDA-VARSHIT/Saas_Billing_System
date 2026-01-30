package com.project.saas_billing_system.controller.v1.auth;

import com.project.saas_billing_system.dto.auth.LoginRequest;
import com.project.saas_billing_system.dto.auth.LoginResponse;
import com.project.saas_billing_system.service.auth.AuthService;
import com.project.saas_billing_system.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
