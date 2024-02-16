package com.nadiflexx.springcloud.msvc.equipos.models.entity;


import com.nadiflexx.springcloud.msvc.equipos.models.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String equipo;

    @NotEmpty
    @Column(unique = true)
    private String dorsal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private List<TeamUser> teamUsers;

    @Transient
    private List<User> users;

    public Team() {
        teamUsers = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addTeamUser(TeamUser teamUser) {
        teamUsers.add(teamUser);
    }

    public void removeTeamUser(TeamUser teamUser) {
        teamUsers.remove(teamUser);
    }
}