package main;

import java.io.File;

import database.PostgresConnection;

import parser.FileParser;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PostgresConnection pcg = new PostgresConnection("tf2loganalyzer", "postgres", "493149");
		
		FileParser fp = new FileParser(pcg);
		//File file = new File("/home/fernando/github/tf2log_analyzer/data/logs/log_988034.log");
		
		File folder = new File("/home/fernando/github/tf2log_analyzer/data/logs/");
		for(File file : folder.listFiles()){
			System.out.println("Adding log from file: "+file.getName());
			fp.parseFile(file);
		}
		
		/*folder = new File("/home/fernando/github/tf2log_analyzer/data/logs_old/");
		for(File file : folder.listFiles()){
			System.out.println("Adding log from file: "+file.getName());
			fp.parseFile(file);
		}*/
		
		//File file = new File("/home/fernando/github/tf2log_analyzer/data/logs/log_988034.log");
		

	}

}
