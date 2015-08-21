package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	
	
	public void parseFile(File file){
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {

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
		       
		    	SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy HH:mm:ss");
		    	Date date = dt.parse(dateString.concat((" ".concat(timeString))));
		       
		       
		       
		       
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
