package org.javakid.Finder.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javakid.Finder.annotations.Password;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @Email
    @NotEmpty
    private String email;

    @Password
    @NotEmpty
    private String password;
}
