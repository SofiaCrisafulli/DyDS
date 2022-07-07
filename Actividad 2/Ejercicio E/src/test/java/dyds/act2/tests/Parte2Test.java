package dyds.act2.tests;

import dyds.act2.parte2.*;
import dyds.act2.tests.utils.StubbedLocalSource;
import dyds.act2.tests.utils.StubbedRemoteSource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Parte2Test {
    ClubLocalSource localSource;
    ClubManagement manager;

    @Before
    public void setUp() throws Exception {
        // Arrange.
        localSource = new StubbedLocalSource();
        ClubRemoteSource remoteSource = new StubbedRemoteSource();
        ClubRepository repo = new ClubRepository(localSource, remoteSource);
        manager = new ClubManagement(repo);
    }

    @Test
    public void testNonInLocalRepo() {
        assertEquals(null, localSource.getClub(1));
    }


    @Test
    public void testLocalRepoFetchAndStore() {
        Club a =  manager.getClub(1);
        assertNotEquals(null, localSource.getClub(1));
    }

    @Test
    public void testObjectFetchAndStore() {
        Club a =  manager.getClub(1);
        assertNotEquals(null, a);
    }

    @Test
    public void testObjectFieldsFetchAndStore() {
        Club a =  manager.getClub(2);
        assertEquals(a.getTeam().getTeamName(), "Stream Gold");
    }

    @Test
    public void testObjectFieldsFetchAndStore2() {
        Club a =  manager.getClub(1);
        assertEquals(a.getTeam().getTeamName(), "Nariz Seniors");
    }

    @Test
    public void testNonExistingElement() {
        Club b = manager.getClub(999);
        assertEquals(null, b);
    }

    @Test
    public void testOldRefetchedCopy() {
        List<Player> lista = new ArrayList<>();
        Team team = new Team("Walking Club", lista, 0,0,0,0);
        Club oldClub = new Club(2, team, "El Cubo", 1900);
        localSource.storeClub(oldClub);
        oldClub.setLocalRepoTimeStamp(0);
        Club c =  manager.getClub(2);
        assertEquals("Stream Gold",c.getTeam().getTeamName());
    }
}
