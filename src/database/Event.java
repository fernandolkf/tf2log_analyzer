package database;

import java.util.Date;

public class Event {

	/**
	 * @param args
	 */
	
	private String line;
	private Date timestamp;
	private String event;
	private Player activePlayer;
	private Player passivePlayer;
	private String description;
	private long[] location;
	
	
	public Event(){
		location = new long[3];
	}
	
	


	public Event(Date timestamp, String event, Player activePlayer,
			Player passivePlayer, String description, long[] location, String line) {
		super();
		this.timestamp = timestamp;
		this.event = event;
		this.activePlayer = activePlayer;
		this.passivePlayer = passivePlayer;
		this.description = description;
		this.location = location;
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


	public Player getActivePlayer() {
		return activePlayer;
	}


	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}


	public Player getPassivePlayer() {
		return passivePlayer;
	}


	public void setPassivePlayer(Player passivePlayer) {
		this.passivePlayer = passivePlayer;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public long[] getLocation() {
		return location;
	}


	public void setLocation(long[] location) {
		this.location = location;
	}




	public String getLine() {
		return line;
	}




	public void setLine(String line) {
		this.line = line;
	}

}
