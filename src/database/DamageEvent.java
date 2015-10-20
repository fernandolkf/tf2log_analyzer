package database;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DamageEvent {

	private String line;
	private Date timestamp;
	private String event;
	private String activePlayer;
	private String passivePlayer;
	private String weapon;
	private int damage;
	private long[] locationAttacker;
	private long[] locationVictim;
	private long roundTime;
	
	
	public DamageEvent(String line, Date timestamp, String event,
			String activePlayer, String passivePlayer, String weapon,
			int damage, long roundTime) {
		super();
		this.line = line;
		this.timestamp = timestamp;
		this.event = event;
		this.activePlayer = activePlayer;
		this.passivePlayer = passivePlayer;
		this.weapon = weapon;
		this.damage = damage;
		this.roundTime = roundTime;
	}
	
	public DamageEvent(){
		locationVictim = new long[3];
		locationAttacker = new long[3];
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
		return locationVictim;
	}


	public void setLocationDeath(long[] location) {
		this.locationVictim = location;
	}
	
	public long[] getLocationAttack() {
		return locationAttacker;
	}


	public void setLocationAttack(long[] location) {
		this.locationAttacker = location;
	}
	
	
	public void insertDamageEvent(PostgresConnection pgc, String logfile){
		
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		String sql = "INSERT INTO damageevent (logfile, line, activePlayer, passivePlayer, eventDescription, " +
				"weapon, date, damage, id_logfile, roundtime  ) VALUES ( ";
		
		sql = sql+"\'"+logfile+"\'"+",";
		sql = sql+"\'"+line.replace("\'", "")+"\'"+",";
		sql = sql+"\'"+activePlayer+"\'"+",";
		sql = sql+"\'"+passivePlayer+"\'"+",";
		sql = sql+"\'"+event+"\'"+",";
		sql = sql+"\'"+weapon+"\'"+",";
		sql = sql+"\'"+dt.format(timestamp)+"\'"+",";
		sql = sql+damage+",";
		sql = sql+"\'"+logfile.replace("log_", "").replace(".log", "")+"\'"+",";
		sql = sql+"interval '1 second' *"+roundTime;
		
		sql = sql+")";
		
		pgc.executeSQL(sql);
		
		
	}
	
	public String getTableSQL(){
		String sql = "INSERT INTO damageevent (logfile, line, activePlayer, passivePlayer, eventDescription, " +
				"weapon, date, damage, id_logfile, roundtime   ) VALUES ";
		return sql;
	}
	
	public String getDamageEventSQL(String logfile){
		
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		String sql = "( ";
		
		sql = sql+"\'"+logfile+"\'"+",";
		sql = sql+"\'"+line.replace("\'", "")+"\'"+",";
		sql = sql+"\'"+activePlayer+"\'"+",";
		sql = sql+"\'"+passivePlayer+"\'"+",";
		sql = sql+"\'"+event+"\'"+",";
		sql = sql+"\'"+weapon+"\'"+",";
		sql = sql+"\'"+dt.format(timestamp)+"\'"+",";
		sql = sql+damage+",";
		sql = sql+"\'"+logfile.replace("log_", "").replace(".log", "")+"\'"+",";
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
