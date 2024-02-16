package com.nadiflexx.springcloud.msvc.users.msvcusuarios.repository;


import com.nadiflexx.springcloud.msvc.users.msvcusuarios.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}