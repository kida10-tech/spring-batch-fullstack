import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard.js";
import { MatchSmallCard } from "../components/MatchSmallCard";

export const MatchPage = () => {

    const [matches, setMatches] = useState([]);
    const { teamName, year } = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                const response = await fetch(`http://localhost:8080/api/team/${teamName}/matches?year=${year}`);
                const data = await response.json();
                setMatches(data);
            };
            fetchMatches();
        }, []
     )

  return (  
    <div className="MatchPage">
      <h1>MatchPage</h1>
      {
          matches.map(match => <MatchDetailCard teamName={teamName} match={match} />)
      }
    </div>
  );
};

export default MatchPage;