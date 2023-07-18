package org.javakid.Finder.services.user;

import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.payload.UserRequest;

public interface UserService {

    UserDto getUserById(Long id);
    void deleteUserById(Long id);
    UserDto saveUser(UserRequest userRequest);
}
