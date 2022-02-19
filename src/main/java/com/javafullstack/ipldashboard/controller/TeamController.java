package com.javafullstack.ipldashboard.controller;

import com.javafullstack.ipldashboard.model.MatchEntity;
import com.javafullstack.ipldashboard.model.TeamEntity;
import com.javafullstack.ipldashboard.repository.MatchRepository;
import com.javafullstack.ipldashboard.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/team/{teamName}/matches")
    public List<MatchEntity> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);
        return this.matchRepository.getMatchesByTeamBetweenDates(
                teamName,
                startDate,
                endDate
        );
    }

    @GetMapping("/team")
    public Iterable<TeamEntity> getAllTeam() {
        return teamRepository.findAll();
    }
}
