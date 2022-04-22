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
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneCategorico;
import com.gbrsni.votoelettronico.models.SessioneCategoricoPreferenze;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.SessioneOrdinale;
import com.gbrsni.votoelettronico.models.StatoSessione;

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
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					switch (ModVoto.valueOf(rs.getString("modvoto"))) {
					case ORDINALE:
						s.add(new SessioneOrdinale(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVoto.valueOf(rs.getString("modvoto")), ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
						break;
					case CATEGORICO:
						s.add(new SessioneCategorico(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVoto.valueOf(rs.getString("modvoto")), ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
						break;
					case CATEGORICO_CON_PREFERENZE:
						s.add(new SessioneCategoricoPreferenze(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVoto.valueOf(rs.getString("modvoto")), ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
						break;
					default:
						throw new Exception("Modalità di voto non riconosciuta");
					}
				} catch (Exception e) {
					System.out.println("Errore durante selezione voti espressi per elettore " +  elettore.getUsername());
					e.printStackTrace();
				}
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Errore durante selezione voti espressi per elettore " +  elettore.getUsername());
			e.printStackTrace();
		}
		return s;
	}
}
