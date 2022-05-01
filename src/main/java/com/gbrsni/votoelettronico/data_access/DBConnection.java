package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**la classe DBConnection implementa il design pattern singleton. Fornisce un metodo get per creare un istanza di connessione con il db*/
public class DBConnection {
	private static Connection instance;
	
	public static Connection getConnection() {
		if (instance == null) {
			try {
				instance = DriverManager.getConnection("jdbc:mysql://localhost/votoElettronico?user=root&password=admin&serverTimezone=UTC");
				System.out.println("Connessione al database votoElettoronico effettuata con successo");
			} catch (SQLException e) {
				System.out.println("Errore nell'ottenimento della connessione al database");
				e.printStackTrace();
			}
		}
		return instance;
	}
}
