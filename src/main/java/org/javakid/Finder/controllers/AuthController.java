package org.javakid.Finder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.javakid.Finder.payload.requests.SignInRequest;
import org.javakid.Finder.payload.requests.RefreshJwtRequest;
import org.javakid.Finder.payload.requests.SignUpRequest;
import org.javakid.Finder.payload.responses.JwtResponse;
import org.javakid.Finder.services.security.auth.RegistrationService;
import org.javakid.Finder.services.security.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sign In & Sign Up")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RegistrationService registrationService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Authenticate user")
    public JwtResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.login(signInRequest);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        registrationService.register(signUpRequest);
    }

    @PostMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get new access token")
    public JwtResponse getNewAccessToken(@Valid @RequestBody RefreshJwtRequest request) throws AuthException {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get new refresh token")
    public JwtResponse getNewRefreshToken(@Valid @RequestBody RefreshJwtRequest request) throws AuthException {
        return authService.refresh(request.getRefreshToken());
    }
}
