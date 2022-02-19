import { React, useEffect, useState } from "react";
import { TeamTile } from "../components/TeamTile.js";

import "./HomePage.scss";

export const HomePage = () => {
  const [teams, setTeams] = useState([]);

  useEffect(
    () => {
      const fetchAllTeams = async () => {
        const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/api/team/`);
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
          { teams.map(team => <TeamTile key={team.id} teamName={team.teamName} />) }
      </div>
    </div>
  );
};

export default HomePage;