import { React, useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard.js";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { PieChart } from "react-minimal-pie-chart";

import "./HomePage.scss";

import { TeamTile } from "../components/TeamTile.js";

export const HomePage = () => {
  const [teams, setTeams] = useState([]);

  useEffect(
    () => {
      const fetchAllTeams = async () => {
        const response = await fetch(`http://localhost:8080/api/team/`);
        const data = await response.json();
        setTeams(data);
      };
      fetchAllTeams();
    },
    [] //This empty array as a second argument tells: Call useEffect only when something inside change
  );

  return (
    <div className="HomePage">
      <div className="header-section">
        <h1 className="app-name">Indian Premier League Dashboard</h1>
      </div>
      <div className="team-grid">
          { teams.map(team => <TeamTile teamName={team.teamName} />) }
      </div>
    </div>
  );
};

export default HomePage;