package com.javafullstack.ipldashboard.data;

import com.javafullstack.ipldashboard.model.TeamEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, TeamEntity> teamData = new HashMap<>();

            entityManager.createQuery("select m.team1, count(*) from MatchEntity m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new TeamEntity((String) e[0], (Long) e[1]))
                    .forEach(teamEntity -> teamData.put(teamEntity.getTeamName(), teamEntity));

            entityManager.createQuery("select m.team2, count(*) from MatchEntity m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        TeamEntity teamEntity = teamData.get((String) e[0]);
                        teamEntity.setTotalMatches(teamEntity.getTotalMatches() + (Long) e[1]);
                    });

            entityManager.createQuery("select m.matchWinner, count(*) from MatchEntity m group by m.matchWinner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        TeamEntity teamEntity = teamData.get((String) e[0]);
                        if(teamEntity != null) teamEntity.setTotalWins((Long) e[1]);
                    });

            teamData.values().forEach(teamEntity -> entityManager.persist(teamEntity));
            teamData.values().forEach(teamEntity -> System.out.println(teamEntity));

            //You can delete this part below after running successfully, it's only to check that all went fine.
            /*
            jdbcTemplate.query("SELECT team1, team2, date FROM match",
                    (rs, row) -> "Team 1 " + rs.getString(1)
                            + " Team 2 " + rs.getString(2)
                            + " Date " + rs.getString(3)
            ).forEach(str -> System.out.println(str));
            */
        }
    }
}
