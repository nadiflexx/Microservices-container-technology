package com.nadiflexx.springcloud.msvc.equipos.clients;

import com.nadiflexx.springcloud.msvc.equipos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001/users")
public interface UserClientRest {
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    @PostMapping
    User save(@RequestBody User user);
}
