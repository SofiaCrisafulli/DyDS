package dyds.act2.parte2;

public class ClubManagement {

    private ClubRepository clubRepository;

    public ClubManagement(ClubRepository clubRepository) {
        super();
        this.clubRepository = clubRepository;
    }

    public Club getClub(int uniqueIdentifier) {
        Club club = clubRepository.getFilmLocalSource().getClub(uniqueIdentifier);
        if (isNotInTheLocalBase(club) || isOld(club)) {
            club = clubRepository.getFilmRemoteSource().getClubRemote(uniqueIdentifier);
            clubRepository.getFilmLocalSource().storeClub(club);
        }
        return club;
    }

    private boolean isOld(Club club) {
        return (System.currentTimeMillis() - club.getLocalRepoTimeStamp() > ClubRepository.maxTimeToKeepALocalRecord * 24 * 60 * 1000);
    }

    private boolean isNotInTheLocalBase(Club club){
        return club == null;
    }

    public void setClubRepository(ClubRepository clubRepository){
        this.clubRepository = clubRepository;
    }
}
