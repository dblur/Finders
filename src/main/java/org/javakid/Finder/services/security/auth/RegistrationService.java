package org.javakid.Finder.services.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.requests.SignUpRequest;
import org.javakid.Finder.services.crud.UserCrudService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserMapper mapper;
    private final UserCrudService userCrudService;

    public void register(SignUpRequest signUpRequest) {
        User user = mapper.toEntity(signUpRequest);
        log.info("Request mapped on User Entity");
        userCrudService.saveUser(user);
        log.info("User with email {} is registered", user.getEmail());
    }
}
