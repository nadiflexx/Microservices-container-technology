package com.nadiflexx.springcloud.msvc.users.msvcusuarios.service;


import com.nadiflexx.springcloud.msvc.users.msvcusuarios.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsersById(Iterable<Long> ids);
}
