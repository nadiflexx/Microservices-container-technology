package com.nadiflexx.springcloud.msvc.equipos.repository;


import com.nadiflexx.springcloud.msvc.equipos.models.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Modifying
    @Query("delete from TeamUser t where t.userId=?1")
    void deleteTeamUserByUserId(Long id);
}