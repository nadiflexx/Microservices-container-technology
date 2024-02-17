package com.nadiflexx.springcloud.msvc.users.msvcusuarios.service.implementation;

import com.nadiflexx.springcloud.msvc.users.msvcusuarios.entity.User;
import com.nadiflexx.springcloud.msvc.users.msvcusuarios.exceptions.DataNotFoundException;
import com.nadiflexx.springcloud.msvc.users.msvcusuarios.repository.UserRepository;
import com.nadiflexx.springcloud.msvc.users.msvcusuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) throw new DataNotFoundException("No users found");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found " + id)));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        repository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found " + id));
        repository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsersById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }
}
