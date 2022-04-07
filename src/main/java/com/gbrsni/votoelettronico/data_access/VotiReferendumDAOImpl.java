package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiReferendumDAOImpl implements VotiReferendumDAO {
	
	private Connection connection = DBConnection.getConnection();

	@Override
	public int getNVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);		
		
		int nvoti = 0;
		String optString = "";
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT ? FROM votireferendum where sessioni = ?");		
			
			switch(opzione) {
			case SI:
				optString = "nvoti1";
				break;
			case NO:
				optString = "nvoti2";
			case ASTENSIONE:
				optString = "nastenuti";
			}

			ps.setString(1, optString);
			ps.setInt(2, sessioneDiVoto.getId());
			ResultSet rs = ps.executeQuery();	
			
			nvoti = rs.getInt(1);
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento dei voti della sessione " + sessioneDiVoto);
			e.printStackTrace();
			nvoti = -1;
		}
		
		return nvoti;
	}

	@Override
	public void increaseVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);
		
		int nvoti = getNVotiBySessioneOpzione(sessioneDiVoto, opzione);
		String optString = "";
		
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE votireferendum SET ? = ? WHERE sessioni = ?");
			
			switch(opzione) {
			case SI:
				optString = "nvoti1";
				break;
			case NO:
				optString = "nvoti2";
			case ASTENSIONE:
				optString = "nastenuti";
			}
			
			ps.setString(1, optString);
			ps.setInt(2, nvoti);
			ps.setInt(3, sessioneDiVoto.getId());
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'incremento dei voti per la sessione " + sessioneDiVoto);
			e.printStackTrace();
			return;
		}
		
		System.out.println("Incrementato il numero dei voti per la sessione" + sessioneDiVoto);
	}

}
