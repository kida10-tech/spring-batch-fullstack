package com.javafullstack.ipldashboard.controller;

import com.javafullstack.ipldashboard.model.TeamEntity;
import com.javafullstack.ipldashboard.repository.MatchRepository;
import com.javafullstack.ipldashboard.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    @GetMapping("/team/{teamName}")
    public TeamEntity getTeam(@PathVariable String teamName) {
        TeamEntity teamEntity = teamRepository.findByTeamName(teamName);
        teamEntity.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
        return teamEntity;
    }
}
