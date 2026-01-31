package com.project.saas_billing_system.dto.identity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response (no password)")
public class UserResponse {

    @Schema(description = "User ID")
    private Long id;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Phone")
    private String phone;

    @Schema(description = "Whether the user is enabled")
    private boolean enabled;

    @Schema(description = "User roles")
    private Set<String> roles;
}
