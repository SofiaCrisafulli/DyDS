package dyds.act2.parte2;

import java.awt.*;
import java.util.List;

public class Team {
    private String teamName;
    private List<Player> firstDivisionTeam;
    private int teamWon;
    private int teamLost;
    private int goalsMade;
    private int goalsReceived;


    public Team(String teamName, List<Player> firstDivisionTeam, int teamWon, int teamLost, int goalsMade, int goalsReceived) {
        this.teamName = teamName;
        this.firstDivisionTeam = firstDivisionTeam;
        this.teamWon = teamWon;
        this.teamLost = teamLost;
        this.goalsMade = goalsMade;
        this.goalsReceived = goalsReceived;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getFirstDivisionTeam() {
        return firstDivisionTeam;
    }

    public void setFirstDivisionTeam(List<Player> firstDivisionTeam) {
        this.firstDivisionTeam = firstDivisionTeam;
    }

    public void addMatchInfo(boolean win) {
        if (win)
            teamWon++;
        else
            teamLost++;
    }

    public void setGoalsMade(int goalsM) {
        goalsMade += goalsM;
    }

    public void setGoalsReceived(int goalsR) {
        goalsReceived += goalsR;
    }
}
