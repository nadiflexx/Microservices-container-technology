package com.nadiflexx.springcloud.msvc.equipos.controller;

import com.nadiflexx.springcloud.msvc.equipos.models.User;
import com.nadiflexx.springcloud.msvc.equipos.models.entity.Team;
import com.nadiflexx.springcloud.msvc.equipos.service.TeamService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("teams")
public class TeamController {

    @Autowired
    private TeamService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveTeam(@Valid @RequestBody Team team, BindingResult result) {
        if (result.hasErrors()) return validate(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTeam(team));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(service.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return service.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @Valid @RequestBody Team team, BindingResult result) {
        if (result.hasErrors()) return validate(result);
        return service.getTeamById(id)
                .map(teamDesired -> {
                    teamDesired.setEquipo(team.getEquipo());
                    teamDesired.setDorsal(team.getDorsal());

                    Team teamUpdate = service.saveTeam(teamDesired);
                    return new ResponseEntity<>(teamUpdate, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeamById(@PathVariable Long id) {
        service.deleteTeam(id);
        return new ResponseEntity<>("Team deleted succesfully", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/asignUser/{teamId}")
    public ResponseEntity<?> asignUser(@RequestBody User user, @PathVariable Long teamId) {
        Optional<User> userFound;

        try {
            userFound = service.asignUser(user, teamId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "User not found or comunication error " + e.getMessage()));
        }
        if (userFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userFound.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createUser/{teamId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long teamId) {
        Optional<User> userFound;

        try {
            userFound = service.createUser(user, teamId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "User could not be created or maybe it's a comunication error " + e.getMessage()));
        }
        if (userFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userFound.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteUser/{teamId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long teamId) {
        Optional<User> userFound;

        try {
            userFound = service.deleteUser(user, teamId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "User not found or comunication error " + e.getMessage()));
        }
        if (userFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userFound.get());
        }
        return ResponseEntity.notFound().build();
    }
}
