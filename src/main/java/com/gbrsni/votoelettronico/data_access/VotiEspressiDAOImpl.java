package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
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
			Logging.infoMessage(this.getClass(), "Ottenuto voto espresso per elettore " + elettore.toString() + " per la sessione con id " + sessioneDiVoto.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante la ricerca del voto espresso da " + elettore.toString() + " per la sessione di voto " + sessioneDiVoto.toString() + "\n" + e.toString());
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
					Logging.warnMessage(this.getClass(), "Errore durante selezione voti espressi per elettore " +  elettore.getUsername() + "\n" + e.toString());
				}
			}
			Logging.infoMessage(this.getClass(), "Ottenute tutte le sessioni in cui ha votato l'elettore " + elettore.toString());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante selezione voti espressi per elettore " +  elettore.getUsername() + "\n" + e.toString());
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
			Logging.infoMessage(this.getClass(), "Inserito voto espresso per l'elettore " + elettore.toString() + " nella sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'aggiunta della votazione per elettore " +  elettore.getUsername() + " per la sessione " + sessione.getId() + "\n" + e.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	@Override
	public int getNumberVotesBySessione(SessioneDiVoto sessione) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int res = 0;
		try {
			ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM votiespressi WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
			Logging.infoMessage(this.getClass(), "Ottenuto numero voti espressi per la sessione " + sessione.toString());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(),"Errore durante l'ottenimento del numero totala di voti espressi per la sessione " + sessione.toString() + "\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
}
