package com.nadiflexx.springcloud.msvc.users.msvcusuarios.controller;

import com.nadiflexx.springcloud.msvc.users.msvcusuarios.entity.User;
import com.nadiflexx.springcloud.msvc.users.msvcusuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) return validate(result);
        if(service.getUserByEmail(user.getEmail()).isEmpty()) return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) return validate(result);
        return service.getUserById(id)
                .map(userDesired -> {
                    if(service.getUserByEmail(user.getEmail()).isEmpty()) {
                        userDesired.setNombre(user.getNombre());
                        userDesired.setPassword(user.getPassword());
                        userDesired.setEmail(user.getEmail());

                        User userUpdate = service.saveUser(userDesired);
                        return new ResponseEntity<>(userUpdate, HttpStatus.OK);
                    }
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists"));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>("User deleted succesfully", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/usersByTeam")
    public ResponseEntity<?> getAllUsersById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.getAllUsersById(ids));
    }
}
