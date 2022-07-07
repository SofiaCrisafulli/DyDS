package dyds.act2.parte2;

import java.awt.*;

public class Club {
    private int uniqueIdentifier;
    private String stadiumName;
    private int foundingYear;
    private Image image;
    private Team team;
    private long localRepoTimeStamp;

    public Club(int uniqueIdentifier, Team team,  String stadiumName, int foundingYear) {
        super();
        this.uniqueIdentifier = uniqueIdentifier;
        this.stadiumName = stadiumName;
        this.foundingYear = foundingYear;
        this.team = team;
    }

    public int getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(int uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public int getFoundingYear() {
        return foundingYear;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public long getLocalRepoTimeStamp() {
        return localRepoTimeStamp;
    }

    public void setLocalRepoTimeStamp(long localRepoTimeStamp) {
        this.localRepoTimeStamp = localRepoTimeStamp;
    }
}
