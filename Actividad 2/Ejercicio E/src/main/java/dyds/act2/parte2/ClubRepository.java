package dyds.act2.parte2;

public class ClubRepository {
	
	public static final int maxTimeToKeepALocalRecord = 10;
	
	private ClubLocalSource clubLocalSource;
	private ClubRemoteSource clubRemoteSource;

	
	public ClubRepository(ClubLocalSource clubLocalSource, ClubRemoteSource clubRemoteSource) {
		super();
		this.clubLocalSource = clubLocalSource;
		this.clubRemoteSource = clubRemoteSource;
	}

	public ClubLocalSource getFilmLocalSource() {
		return clubLocalSource;
	}

	public ClubRemoteSource getFilmRemoteSource() {
		return clubRemoteSource;
	}

	public void setClubLocalSource(ClubLocalSource clubLocalSource){
		this.clubLocalSource = clubLocalSource;
	}

	public void setClubRemoteSource(ClubRemoteSource clubRemoteSource){
		this.clubRemoteSource = clubRemoteSource;
	}

	public ClubLocalSource getClubLocalSource() {
		return clubLocalSource;
	}
}
