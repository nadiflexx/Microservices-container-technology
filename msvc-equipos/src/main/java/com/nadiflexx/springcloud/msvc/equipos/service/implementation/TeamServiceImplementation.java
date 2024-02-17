package com.nadiflexx.springcloud.msvc.equipos.service.implementation;

import com.nadiflexx.springcloud.msvc.equipos.clients.UserClientRest;
import com.nadiflexx.springcloud.msvc.equipos.models.User;
import com.nadiflexx.springcloud.msvc.equipos.models.entity.Team;
import com.nadiflexx.springcloud.msvc.equipos.exceptions.DataNotFoundException;
import com.nadiflexx.springcloud.msvc.equipos.models.entity.TeamUser;
import com.nadiflexx.springcloud.msvc.equipos.repository.TeamRepository;
import com.nadiflexx.springcloud.msvc.equipos.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImplementation implements TeamService {


    @Autowired
    private TeamRepository repository;

    @Autowired
    private UserClientRest clientRest;

    @Override
    @Transactional
    public Team saveTeam(Team team) {
        return repository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        List<Team> teams = repository.findAll();
        if (teams.isEmpty()) throw new DataNotFoundException("No teams found");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> getTeamById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(() -> new DataNotFoundException("Team not found " + id)));
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        repository.findById(id).orElseThrow(() -> new DataNotFoundException("Team not found " + id));
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> asignUser(User user, Long teamId) {
        Optional<Team> teamRepository = repository.findById(teamId);

        if (teamRepository.isPresent()) {
            User userService = clientRest.getUserById(user.getId());

            Team team = teamRepository.get();
            TeamUser teamUser = new TeamUser();
            teamUser.setUserId(userService.getId());

            team.addTeamUser(teamUser);
            repository.save(team);

            return Optional.of(userService);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long teamId) {
        Optional<Team> teamRepository = repository.findById(teamId);

        if (teamRepository.isPresent()) {
            User newUserService = clientRest.save(user);

            Team team = teamRepository.get();
            TeamUser teamUser = new TeamUser();
            teamUser.setUserId(newUserService.getId());

            team.addTeamUser(teamUser);
            repository.save(team);

            return Optional.of(newUserService);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(User user, Long teamId) {
        Optional<Team> teamRepository = repository.findById(teamId);

        if (teamRepository.isPresent()) {
            User newUserService = clientRest.getUserById(user.getId());

            Team team = teamRepository.get();
            TeamUser teamUser = new TeamUser();
            teamUser.setUserId(newUserService.getId());

            team.removeTeamUser(teamUser);
            repository.save(team);

            return Optional.of(newUserService);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> getTeamByIdWithUsers(Long teamId) {
        Optional<Team> teamRepository = repository.findById(teamId);

        if (teamRepository.isPresent()) {
            Team team = teamRepository.get();
            if (!team.getTeamUsers().isEmpty()) {
                List<Long> idsUser = team.getTeamUsers().stream().map(TeamUser::getUserId).toList();

                List<User> users = clientRest.getAllUsersById(idsUser);

                team.setUsers(users);
            }
            return Optional.of(team);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteTeamUserByIdUser(Long id) {
        repository.deleteTeamUserByUserId(id);
    }

}
