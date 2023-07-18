package org.javakid.Finder.services.crud;

import org.javakid.Finder.entity.User;

public interface UserCrudService {

    User saveUser(User user);
    User getUserById(Long id);
    void deleteUserById(Long id);
}
