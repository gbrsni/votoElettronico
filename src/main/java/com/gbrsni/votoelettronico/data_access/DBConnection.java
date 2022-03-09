package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Questa classe implementa il design patter Singleton
public class DBConnection {
	private static Connection instance;
	
	public static Connection getConnection() {
		if (instance == null) {
			try {
				instance = DriverManager.getConnection("jdbc:Mysql://localhost:3306/test?user=user&password=mynameischef12&serverTimezone=UTC");
			} catch (SQLException e) {
				System.out.println("Errore nell'ottenimento della connessione al database");
				e.printStackTrace();
			}
		}
		
		return instance;
	}
}
