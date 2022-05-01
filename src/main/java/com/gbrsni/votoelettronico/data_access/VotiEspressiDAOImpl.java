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
import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneCategorico;
import com.gbrsni.votoelettronico.models.SessioneCategoricoPreferenze;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.SessioneOrdinale;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class VotiEspressiDAOImpl implements VotiEspressiDAO {
	private Connection connection = DBConnection.getConnection();
	
	/**resituisce true se esiste un voto dell'elettore per la sessione, false altrimenti*/
	@Override
	public boolean existsVotoEspresso(SessioneDiVoto sessioneDiVoto, Elettore elettore) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(elettore);
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean risultato = false;
		try {
			ps = connection.prepareStatement("SELECT * FROM votiespressi WHERE sessioni = ? and  elettori = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setString(2, elettore.getUsername());
			rs = ps.executeQuery();
			risultato = rs.next();
		} catch (SQLException e) {
			System.out.println("Errore durante la ricerca del voto espresso da " + elettore.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return risultato;
	}
	
	/**resistuisce tutte le sessioni per cui l'elettore ha espresso il voto*/
	@Override
	public List<SessioneDiVoto> allExistsVotoEspressoByElettore(Elettore elettore) {
		Objects.requireNonNull(elettore);
		List<SessioneDiVoto> s = new ArrayList<>();;
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM votiespressi  inner join sessioni ON votiespressi.sessioni = sessioni.id Where votiespressi.elettori = ?");
			ps.setString(1, elettore.getUsername());
			rs = ps.executeQuery();
			while(rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					s.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
				} catch (Exception e) {
					System.out.println("Errore durante selezione voti espressi per elettore " +  elettore.getUsername());
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore durante selezione voti espressi per elettore " +  elettore.getUsername());
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return s;
	}
	
	/**aggiunge il voto dell'elettore per la sessione di voto indicata*/
	@Override
	public void addVotoEspresso(SessioneDiVoto sessione, Elettore elettore) {
		Objects.requireNonNull(elettore);
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votiespressi (sessioni,elettori) VALUES (?,?)");
			ps.setInt(1, sessione.getId());
			ps.setString(2, elettore.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiunta della votazione per elettore " +  elettore.getUsername() + " per la sessione " + sessione.getId());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
}
