package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiEspressiDAOImpl implements VotiEspressiDAO {
	private Connection connection = DBConnection.getConnection();
	
	// Attenzione! Restituisce false anche se viene sollevata un'eccezione durante l'esecuzione del metodo
	@Override
	public boolean existsVotoEspresso(SessioneDiVoto sessioneDiVoto, Elettore elettore) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(elettore);

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votiespressi WHERE sessioni = ? and  elettori = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setString(2, elettore.getUsername());
			ResultSet rs = ps.executeQuery();
			
			boolean risultato = rs.next();
			ps.close();

			return risultato;
			
		} catch (SQLException e) {
			System.out.println("Errore durante la ricerca del voto espresso da " + elettore.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<SessioneDiVoto> allExistsVotoEspressoByElettore(Elettore elettore) {
		Objects.requireNonNull(elettore);
		List<SessioneDiVoto> s = new ArrayList<>();;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votiespressi WHERE elettori = ?");
			ps.setString(1, elettore.getUsername());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
				s.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, rs.getString("modvoto"), rs.getString("modvittoria"), rs.getString("stato"), rs.getInt("nvoti")));
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Errore durante selezione voti espressi per elettore " +  elettore.getUsername());
			e.printStackTrace();
		}
		return s;
	}
}
