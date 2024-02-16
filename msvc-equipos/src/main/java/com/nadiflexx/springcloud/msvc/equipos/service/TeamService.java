package com.nadiflexx.springcloud.msvc.equipos.service;


import com.nadiflexx.springcloud.msvc.equipos.models.User;
import com.nadiflexx.springcloud.msvc.equipos.models.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team saveTeam(Team team);

    List<Team> getAllTeams();

    Optional<Team> getTeamById(Long id);

    void deleteTeam(Long id);

    Optional<User> asignUser(User user, Long teamId);

    Optional<User> createUser(User user, Long teamId);

    Optional<User> deleteUser(User user, Long teamId);

}
