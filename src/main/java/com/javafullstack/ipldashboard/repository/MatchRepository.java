package com.javafullstack.ipldashboard.repository;

import com.javafullstack.ipldashboard.model.MatchEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, Long> {

    //Pageable is used to control how many results are requested per page
    List<MatchEntity> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    @Query("select m from MatchEntity m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc")
    List<MatchEntity> getMatchesByTeamBetweenDates(
            @Param("teamName") String teamName,
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd
    );

    /*
    List<MatchEntity> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
            String teamName1, LocalDate date1, LocalDate date2,
            String teamName2, LocalDate date3, LocalDate date4);
    */

    default List<MatchEntity> findLatestMatchesByTeam(String teamName, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
