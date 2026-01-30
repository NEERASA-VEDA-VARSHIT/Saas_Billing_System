package com.project.saas_billing_system.service.identity;

import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.identity.User;
import com.project.saas_billing_system.repository.identity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}
