package com.gbrsni.votoelettronico.logging;
import org.apache.log4j.Logger;

public class Logging{
	
	private static Logger logger ; 
	
	public static void debugMessage(Class name, String message) {
		logger = Logger.getLogger(name);
		logger.debug(message);
	}
	
	public static void infoMessage(Class name,String message) {
		logger = Logger.getLogger(name);
		logger.info(message);
	}
	
	public static void warnMessage(Class name,String message) {
		logger = Logger.getLogger(name);
		logger.warn(message);
	}
	
	public static void errorMessage(Class name,String message) {
		logger = Logger.getLogger(name);
		logger.error(message);
	}
	
	public static void fatalMessage(Class name,String message) {
		logger = Logger.getLogger(name);
		logger.fatal(message);
	}
}