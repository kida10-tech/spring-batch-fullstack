package com.javafullstack.ipldashboard.repository;

import com.javafullstack.ipldashboard.model.TeamEntity;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamEntity, Long> {

    TeamEntity findByTeamName(String teamName);
}
