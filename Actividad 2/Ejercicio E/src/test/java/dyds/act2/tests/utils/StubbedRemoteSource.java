package dyds.act2.tests.utils;

import dyds.act2.parte2.Club;
import dyds.act2.parte2.ClubRemoteSource;
import dyds.act2.parte2.Player;
import dyds.act2.parte2.Team;

import java.util.ArrayList;
import java.util.List;

public class StubbedRemoteSource implements ClubRemoteSource {
    @Override
    public Club getClubRemote(int id) {
        List<Player> lista1 = new ArrayList<Player>();
        List<Player> lista2 = new ArrayList<Player>();
        Team team1 = new Team("Nariz Seniors", lista1, 0,0,0,0);
        Team team2 = new Team("Stream Gold", lista2, 0,0,0,0);
        switch(id) {
            case 1 : return new Club(1, team1,  "Chocolatera", 1912);
            case 2 : return new Club(2, team2,  "Gran Monumento", 1911);
        }
        return null;
    }
}
