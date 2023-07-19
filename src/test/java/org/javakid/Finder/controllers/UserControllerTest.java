package org.javakid.Finder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.enums.Sex;
import org.javakid.Finder.payload.CompanyRequest;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.services.crud.impl.UserCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mvc;
    private ObjectMapper mapper;
    @Mock private UserCrudService userCrudService;
    @InjectMocks private UserController userController;

    private Long id;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
        id = 1L;
        userRequest = new UserRequest(
                "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new CompanyRequest(
                "name", "email",
                "phone", "city", "country", "scope")
        );
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(userCrudService);
    }

    @Test
    void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userCrudService, times(1)).getUserDtoById(id);
        verifyNoMoreInteractions(userCrudService);
    }

    @Test
    void post() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse();
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        verify(userCrudService, times(1)).deleteUserById(id);
        verifyNoMoreInteractions(userCrudService);
    }
}