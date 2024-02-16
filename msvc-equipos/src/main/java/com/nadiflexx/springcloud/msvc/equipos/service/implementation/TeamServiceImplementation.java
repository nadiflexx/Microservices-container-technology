package com.nadiflexx.springcloud.msvc.equipos.service.implementation;

import com.nadiflexx.springcloud.msvc.equipos.entity.Team;
import com.nadiflexx.springcloud.msvc.equipos.exceptions.DataNotFoundException;
import com.nadiflexx.springcloud.msvc.equipos.repository.TeamRepository;
import com.nadiflexx.springcloud.msvc.equipos.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImplementation implements TeamService {


    @Autowired
    private TeamRepository repository;

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
    public boolean findUser(Long id) {
        return true;
    }
}
