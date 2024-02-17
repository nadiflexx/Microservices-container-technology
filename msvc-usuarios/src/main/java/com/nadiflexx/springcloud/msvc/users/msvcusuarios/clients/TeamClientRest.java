package com.nadiflexx.springcloud.msvc.users.msvcusuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "msvc-equipos", url = "localhost:8002/teams")
public interface TeamClientRest {

    @DeleteMapping("/deleteTeamUser/{teamId}")
    void deleteTeamUser(@PathVariable Long teamId);
}


