package com.javafullstack.ipldashboard.data;

import com.javafullstack.ipldashboard.model.MatchEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInput, MatchEntity> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public MatchEntity process(final MatchInput matchInput) throws Exception {
        MatchEntity matchEntity = new MatchEntity();

        matchEntity.setId(Long.parseLong(matchInput.getId()));
        matchEntity.setCity(matchInput.getCity());

        matchEntity.setDate(LocalDate.parse(matchInput.getDate()));

        matchEntity.setPlayerOfMatch(matchInput.getPlayer_of_match());
        matchEntity.setVenue(matchInput.getVenue());

        //Set Team 1 and 2 depending on the innings order
        String firstInningsTeam, secondInningsTeam;

        if("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam2())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam2())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        matchEntity.setTeam1(firstInningsTeam);
        matchEntity.setTeam2(secondInningsTeam);

        matchEntity.setTossWinner(matchInput.getToss_winner());
        matchEntity.setTossDecision(matchInput.getToss_decision());
        matchEntity.setMatchWinner(matchInput.getWinner());
        matchEntity.setResult(matchInput.getResult());
        matchEntity.setResultMargin(matchInput.getResult_margin());
        matchEntity.setUmpire1(matchInput.getUmpire1());
        matchEntity.setUmpire2(matchInput.getUmpire2());

        return matchEntity;
    }
}

