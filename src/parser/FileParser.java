package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.accessibility.internal.resources.accessibility;

import database.DamageEvent;
import database.Event;
import database.KillEvent;
import database.PostgresConnection;

public class FileParser {
	
	List<String> actions = new ArrayList<String>();
	//List<String> actions = new ArrayList<String>();
	PostgresConnection pgc;
	
	public FileParser(PostgresConnection pgc){
		actions.add("say");
		actions.add("connected");
		actions.add("STEAM");
		actions.add("joined");
		actions.add("changed");
		actions.add("spawned");
		actions.add("entered");
		//actions.add("triggered");
		actions.add("picked");
		actions.add("killed");
		actions.add("committed");
		//actions.add("triggered");
		actions.add("damage");
		
		this.pgc = pgc;
	}
	
	
	public void parseFile(File file){
		
		
		String line = "";
		KillEvent killEvent= new KillEvent();
		DamageEvent damageEvent= new DamageEvent();
		String sqlDamage = damageEvent.getTableSQL(); String sqlKill = killEvent.getTableSQL();
		boolean hasDamage = false;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {	    
			String id = file.getName().replace("log_", "").replace(".log", "");
			
			ResultSet rs = pgc.getRs("SELECT id FROM matchlog WHERE id="+id);
			if(rs.next()){
				System.out.println("Log already on the database: "+file.getName());
				return;
			}
			String player="";
			String actionString = "";
			String targetString = "";
			String positionAttack = "";
			String positionDeath = "";
			String weapon = "";
			int damage = 0;
			long roundTime = 0;
			boolean startMatch = false;
			
			//jump to 3th line (map line)
			
			while((line = br.readLine()) != null && !line.matches(".*Map.*")){
				line = br.readLine();
			}
			
			if(line == null){
				System.out.println("End of file "+file.getName());
				return;
			}
			
			String map = line.replace("\"", "").split(" ")[7];
			//jump to mode line
			line = br.readLine();
			
			String mode = line.replace("\"", "").split(" ")[7];
			String sql = "INSERT INTO matchlog (id, filename, map, mode) VALUES ("
					+id+",'"+file.getName()+"','"+map+"','"+mode+"');";
			
			pgc.executeSQL(sql);
			
			Date startRound = new Date();
			
			
			
		    while ((line = br.readLine()) != null) {
		    	
		    	while(!line.startsWith("L") || line.matches(".*say.*")){
		    		line = br.readLine();
		    	}
		    	
		    	Pattern datePattern = Pattern.compile("\\b[0-9]{2}/[0-9]{2}/[0-9]{4}\\b");
		    	Pattern timePattern = Pattern.compile("\\b[0-9]{2}:[0-9]{2}:[0-9]{2}\\b");
		    	Matcher regexMatcher = datePattern.matcher(line);
		    	String dateString ="", timeString = "";
		        if (regexMatcher.find()) {
		        	dateString = regexMatcher.group();
		        }
		        
		        regexMatcher = timePattern.matcher(line);
		        if (regexMatcher.find()) {
		        	timeString = regexMatcher.group();
		        }
		       
		    	SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		    	Date date = dt.parse(dateString.concat((" ".concat(timeString))));
		    	
		    	if(line.matches(".*Round_Start.*")){
		    		System.out.println("Round started at "+date);
		    		startRound = date;
		    		startMatch = true;
		    	}else if(line.matches(".*Round_Win.*")){
		    		System.out.println("Round ended at "+date);
		    		startMatch = false;
		    	}
		    	
		    	if(startMatch){

		    		roundTime = date.getTime()-startRound.getTime();
			    	boolean findPlayer = false, findAction = false, findTarget=false, findPosition = false;
			    	line = line.replace("\"", "").replace("'", "").replace("(", "").replace(")", "");
			    	
			    	String[] splitLine = line.split(" ");
			    	
			    	for(int i = 4; i < splitLine.length; i++){
			    		
			    		
			    		
			    		if(actions.contains(splitLine[i]) && !findAction){
		    				findPlayer=true;
		    				findAction=true;
		    				actionString = splitLine[i];
		    				//System.out.println(actionString);
		    			}else if(!findPlayer){
			    			player = player.concat(splitLine[i]);
			    		}else if (!findTarget){
			    			if(splitLine[i].equals("with")){
			    				findTarget=true;
			    				weapon = splitLine[i+1];
			    				
			    			}else if(findAction && splitLine[i].equals("damage") && !splitLine[i+1].equals("against")){
			    				findTarget=true;
			    				//System.out.println("teeest"+splitLine[i+1]);
			    				damage = new Integer(splitLine[i+1]);
			    				//System.out.println(damage);
			    			}else if(actionString.equals("killed")){
		    					targetString = targetString.concat(splitLine[i]);
		    				}else if(actionString.equals("damage") && !splitLine[i].equals("against")){
		    					targetString = targetString.concat(splitLine[i]);
		    				}
			    		}else if(splitLine[i].equals("weapon")){
	    					weapon = splitLine[i+1];
		    			}
			    		else if(splitLine[i].contains("attacker_position")){
			    			positionAttack = splitLine[i+1]+" "+splitLine[i+2]+" "+splitLine[i+3];
			    		}else if(splitLine[i].contains("victim_position")){
			    			positionDeath = splitLine[i+1]+" "+splitLine[i+2]+" "+splitLine[i+3];
			    		}
			    		
			    		
			    	}
		    		
			    	//
			    	if(actionString.equals("killed")){
			    		
			    		long[] locationAttack = new long[3];
			    		long[] locationDeath = new long[3];
			    		try{
			    			locationAttack[0] = new Long(positionAttack.split(" ")[0]);
			    		}catch(Exception e){
			    			System.out.println("cant parser: "+line);
			    		}
			    		 
			    		locationAttack[1] = new Long(positionAttack.split(" ")[1]);
			    		locationAttack[2] = new Long(positionAttack.split(" ")[2]);
			    		
			    		locationDeath[0] = new Long(positionDeath.split(" ")[0]);
			    		locationDeath[1] = new Long(positionDeath.split(" ")[1]);
			    		locationDeath[2] = new Long(positionDeath.split(" ")[2]);
			    		killEvent = new KillEvent(line, date, actionString, player, targetString, weapon, locationDeath, locationAttack,roundTime);
			    		//killEvent.insertKillEvent(pgc,file.getName());
			    		
			    		sqlKill = sqlKill.concat(killEvent.getKillEventSQL(file.getName())+",");
			    		
			    	}else if(actionString.equals("damage")){
			    		
			    		hasDamage = true;
			    		damageEvent = new DamageEvent(line, date, actionString, player, targetString, weapon, damage,roundTime);
			    		//damageEvent.insertDamageEvent(pgc,file.getName());
			    		sqlDamage = sqlDamage.concat(damageEvent.getDamageEventSQL(file.getName())+",");
			    	}
			    		
			    		/*System.out.println("Date time is "+date);
			    		System.out.println("Player is "+ player);
			    		System.out.println("Action is "+ actionString);
			    		System.out.println("Target is "+ targetString);
			    		System.out.println("Position of attack is "+ positionAttack);
			    		System.out.println("Position of death is "+ positionDeath);
			    		System.out.println("Weapon is "+ weapon);
			    		System.out.println(line);*/
			    		
			    	
			    	player="";
					actionString = "";
					targetString = "";
					positionAttack = "";
					positionDeath = "";   
		    	}
		       
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(line);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(line);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Parser failed at line: "+line);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pgc.executeSQL(sqlKill.substring(0,sqlKill.length()-1));
		if(hasDamage){
			pgc.executeSQL(sqlDamage.substring(0,sqlDamage.length()-1));
		}
			
	}

}
