package org.javakid.Finder.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javakid.Finder.annotations.EmailNotExists;
import org.javakid.Finder.annotations.Password;
import org.javakid.Finder.annotations.RoleSubset;
import org.javakid.Finder.enums.ERole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Email
    @NotEmpty
    @EmailNotExists
    private String email;

    @NotEmpty
    @Password
    private String password;

    @NotNull
    @RoleSubset(anyOf = {ERole.RECRUITER, ERole.CANDIDATE})
    private ERole role;
}
