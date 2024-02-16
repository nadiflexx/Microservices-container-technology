package com.nadiflexx.springcloud.msvc.equipos.service;



import com.nadiflexx.springcloud.msvc.equipos.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team saveTeam(Team team);

    List<Team> getAllTeams();

    Optional<Team> getTeamById(Long id);

    void deleteTeam(Long id);

    boolean findUser(Long id);
}
