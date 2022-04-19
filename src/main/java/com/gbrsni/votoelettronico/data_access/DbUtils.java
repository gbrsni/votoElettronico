package com.gbrsni.votoelettronico.data_access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class DbUtils {
	
	/**chiuse il resultSet rs*/
	public static void closeStatement(ResultSet rs) {
		try { 
			rs.close(); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	/**chiude il prepareStatement p */
	public static void closeResultSet(PreparedStatement ps) {
	try {
        ps.close();
    } catch (SQLException e) { 
    	e.printStackTrace();
    }
	}
}
