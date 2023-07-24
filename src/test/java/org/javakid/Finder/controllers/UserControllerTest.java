package org.javakid.Finder.controllers;

import org.javakid.Finder.services.crud.UserCrudService;
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

    @Mock private UserCrudService userCrudService;
    @InjectMocks private UserController userController;

    private Long id;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        id = 1L;
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(userCrudService);
    }

    @Test
    void shouldGetUserById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userCrudService, times(1)).getUserDtoById(id);
        verifyNoMoreInteractions(userCrudService);
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