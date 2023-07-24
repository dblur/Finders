package org.javakid.Finder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javakid.Finder.enums.ERole;
import org.javakid.Finder.payload.requests.SignInRequest;
import org.javakid.Finder.payload.requests.SignUpRequest;
import org.javakid.Finder.payload.responses.JwtResponse;
import org.javakid.Finder.services.security.auth.AuthService;
import org.javakid.Finder.services.security.auth.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockBean private AuthService authService;
    @MockBean private RegistrationService registrationService;
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    private JwtResponse jwtResponse;
    private SignInRequest signInRequest;
    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        signInRequest = new SignInRequest("email@email.com", "p@Ass1wordd");
        jwtResponse = new JwtResponse("accessToken", "refreshToken");
        signUpRequest = new SignUpRequest("email@email.com", "p@Ass1wordd", ERole.CANDIDATE);
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(authService);
        assertNotNull(registrationService);
    }

//    @Test
//    void signIn() throws Exception {
//        String jwtResponse = mapper.writeValueAsString(this.jwtResponse);
//        String signInRequest = mapper.writeValueAsString(this.signInRequest);
//
//        Mockito.when(authService.login(this.signInRequest)).thenReturn(this.jwtResponse);
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(signInRequest)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(jwtResponse));
//    }

    @Test
    void signUp() throws Exception {
        String signUpRequest = mapper.writeValueAsString(this.signUpRequest);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-up")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(signUpRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getNewAccessToken() {
    }

    @Test
    void getNewRefreshToken() {
    }
}