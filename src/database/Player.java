package database;

public class Player {

	private String steamId;
	//private String teamClass;
	private String team;
	private int kills;
	private int deaths;
	
	public Player(String steamId, String team, int kills, int deaths) {
		super();
		this.steamId = steamId;
		this.team = team;
		this.kills = kills;
		this.deaths = deaths;
	}

	public Player(){
		
	}

	public String getSteamId() {
		return steamId;
	}

	public void setSteamId(String steamId) {
		this.steamId = steamId;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
}
