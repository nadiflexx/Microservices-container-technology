package com.nadiflexx.springcloud.msvc.equipos.clients;

import com.nadiflexx.springcloud.msvc.equipos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001/users")
public interface UserClientRest {
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    @PostMapping
    User save(@RequestBody User user);

    @GetMapping("/usersByTeam")
    List<User> getAllUsersById(@RequestParam Iterable<Long> ids);
}


