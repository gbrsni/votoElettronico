package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gbrsni.votoelettronico.logging.Logging;

public class DbUtils {

	/** chiuse il PreparedStatement ps */
	public static void closeStatement(PreparedStatement ps) {
		try {
			ps.close();
		} catch (Exception e) {
			Logging.warnMessage(DbUtils.class, "Errore durante la chiusura statement\n" + e.toString());
		}
	}

	/** chiude il ResultSet rs*/
	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			Logging.warnMessage(DbUtils.class, "Errore durante la chiusura result set\n" + e.toString());
		}
	}
	
	/** chiude la connessione connection */
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			Logging.warnMessage(DbUtils.class, "Errore durante la chiusura connessione\n" + e.toString());
		}
	}
}
