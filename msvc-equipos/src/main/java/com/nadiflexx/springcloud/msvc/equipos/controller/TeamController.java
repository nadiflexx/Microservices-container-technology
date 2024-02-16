package com.nadiflexx.springcloud.msvc.equipos.controller;

import com.nadiflexx.springcloud.msvc.equipos.entity.Team;
import com.nadiflexx.springcloud.msvc.equipos.service.TeamService;
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
@RequestMapping("teams")
public class TeamController {

    @Autowired
    private TeamService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveTeam(@Valid @RequestBody Team team, BindingResult result) {
        if (result.hasErrors()) return validate(result);
        //if (service.existsByUserId(team.getUserId()))
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTeam(team));
        //return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User id not found"));
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
                    //if (service.existsByUserId(team.getUserId())) {
                        teamDesired.setEquipo(team.getEquipo());
                        teamDesired.setDorsal(team.getDorsal());
                        teamDesired.setUserId(team.getUserId());

                        Team teamUpdate = service.saveTeam(teamDesired);
                        return new ResponseEntity<>(teamUpdate, HttpStatus.OK);
                    //}

                    //return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User id not found"));
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
}
