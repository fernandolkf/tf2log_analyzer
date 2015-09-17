package database;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KillEvent {

	private String line;
	private Date timestamp;
	private String event;
	private String activePlayer;
	private String passivePlayer;
	private String weapon;
	private long[] locationAttack;
	private long[] locationDeath;
	private long roundTime;
	
	
	public KillEvent(String line, Date timestamp, String event,
			String activePlayer, String passivePlayer, String weapon,
			long[] locationDeath, long[] locationAttack, long roundTime) {
		super();
		this.line = line;
		this.timestamp = timestamp;
		this.event = event;
		this.activePlayer = activePlayer;
		this.passivePlayer = passivePlayer;
		this.weapon = weapon;
		this.locationAttack = locationAttack;
		this.locationDeath = locationDeath;
		this.roundTime=roundTime;
	}
	
	public KillEvent(){
		locationDeath = new long[3];
		locationAttack = new long[3];
	}


	public String getLine() {
		return line;
	}


	public void setLine(String line) {
		this.line = line;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public String getEvent() {
		return event;
	}


	public void setEvent(String event) {
		this.event = event;
	}


	public String getActivePlayer() {
		return activePlayer;
	}


	public void setActivePlayer(String activePlayer) {
		this.activePlayer = activePlayer;
	}


	public String getPassivePlayer() {
		return passivePlayer;
	}


	public void setPassivePlayer(String passivePlayer) {
		this.passivePlayer = passivePlayer;
	}


	public String getWeapon() {
		return weapon;
	}


	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}


	public long[] getLocationDeath() {
		return locationDeath;
	}


	public void setLocationDeath(long[] location) {
		this.locationDeath = location;
	}
	
	public long[] getLocationAttack() {
		return locationAttack;
	}


	public void setLocationAttack(long[] location) {
		this.locationAttack = location;
	}
	
	
	public void insertKillEvent(PostgresConnection pgc, String logfile){
		
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		String sql = "INSERT INTO killevent (logfile, line, activePlayer, passivePlayer, eventDescription, " +
				"weapon, date, x_deathposition, y_deathposition, z_deathposition, point_death," +
				"x_attackposition, y_attackposition, z_attackposition, point_attack, roundtime   ) VALUES ( ";
		
		sql = sql+"\'"+logfile+"\'"+",";
		sql = sql+"\'"+line.replace("\'", "")+"\'"+",";
		sql = sql+"\'"+activePlayer+"\'"+",";
		sql = sql+"\'"+passivePlayer+"\'"+",";
		sql = sql+"\'"+event+"\'"+",";
		sql = sql+"\'"+weapon+"\'"+",";
		sql = sql+"\'"+dt.format(timestamp)+"\'"+",";
		sql = sql+locationDeath[0]+",";
		sql = sql+locationDeath[1]+",";
		sql = sql+locationDeath[2]+",";
		sql = sql+"ST_MAKEPOINT("+locationDeath[0]+","+locationDeath[1]+")"+",";
		sql = sql+locationAttack[0]+",";
		sql = sql+locationAttack[1]+",";
		sql = sql+locationAttack[2]+",";
		sql = sql+"ST_MAKEPOINT("+locationAttack[0]+","+locationAttack[1]+"),";
		sql = sql+"interval '1 second' *"+roundTime;
		
		sql = sql+")";
		
		pgc.executeSQL(sql);
		
		
	}
	
	
	public String getTableSQL(){
		String sql = "INSERT INTO killevent (logfile, line, activePlayer, passivePlayer, eventDescription, " +
				"weapon, date, x_deathposition, y_deathposition, z_deathposition, point_death," +
				"x_attackposition, y_attackposition, z_attackposition, point_attack, roundtime   ) VALUES  ";
		return sql;
	}
	
	public String getKillEventSQL(String logfile){
		
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		String sql = "( ";
		
		sql = sql+"\'"+logfile+"\'"+",";
		sql = sql+"\'"+line.replace("\'", "")+"\'"+",";
		sql = sql+"\'"+activePlayer+"\'"+",";
		sql = sql+"\'"+passivePlayer+"\'"+",";
		sql = sql+"\'"+event+"\'"+",";
		sql = sql+"\'"+weapon+"\'"+",";
		sql = sql+"\'"+dt.format(timestamp)+"\'"+",";
		sql = sql+locationDeath[0]+",";
		sql = sql+locationDeath[1]+",";
		sql = sql+locationDeath[2]+",";
		sql = sql+"ST_MAKEPOINT("+locationDeath[0]+","+locationDeath[1]+")"+",";
		sql = sql+locationAttack[0]+",";
		sql = sql+locationAttack[1]+",";
		sql = sql+locationAttack[2]+",";
		sql = sql+"ST_MAKEPOINT("+locationAttack[0]+","+locationAttack[1]+"),";
		sql = sql+"interval '1 second' *"+roundTime;
		
		sql = sql+")";
		
		return sql;
		
		
	}

	public long getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(long roundTime) {
		this.roundTime = roundTime;
	}






}
