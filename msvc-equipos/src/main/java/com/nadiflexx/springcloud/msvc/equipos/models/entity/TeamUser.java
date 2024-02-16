package com.nadiflexx.springcloud.msvc.equipos.models.entity;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "teams_user")
public class TeamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TeamUser)) return false;

        TeamUser o = (TeamUser) obj;

        return this.userId != null && this.userId.equals(o.userId);
    }
}