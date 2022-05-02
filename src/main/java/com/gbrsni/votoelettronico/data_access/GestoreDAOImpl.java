package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;

public class GestoreDAOImpl implements GestoreDAO {	
	private Connection connection = DBConnection.getConnection();
	
	/**restituisce tutti i gestori dal database*/
	@Override
	public List<Gestore> getAllGestore() {
		List<Gestore> res = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM gestori");
			rs = ps.executeQuery();
			while (rs.next()) {
				res.add(new Gestore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"),rs.getString("codicefiscale")));
			}
			Logging.infoMessage(this.getClass(), "Ottenuti tutti i gestori dal database");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento di tutti gli gestori\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**aggiorna il gestore g presente nel database*/
	@Override
	public void updateGestore(Gestore g) {
		Objects.requireNonNull(g);
		PreparedStatement ps = null;		
		try {
			ps = connection.prepareStatement("UPDATE gestori SET nome = ?, cognome = ? WHERE username = ?");
			ps.setString(1, g.getNome());
			ps.setString(2, g.getCognome());
			ps.setString(3, g.getUsername());
			ps.setString(4, g.getCodiceFiscale());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Aggiornato gestore " + g.toString() + " nel database");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'aggiornamento del gestore " + g.toString() + "\n" + e.toString());
		}
		finally {DbUtils.closeStatement(ps); }
	}
	
	/**rimuove il gestore g dal database*/
	@Override
	public void deleteGestore(Gestore g) {
		Objects.requireNonNull(g);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM gestori WHERE username = ?");
			ps.setString(1, g.getUsername());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Rimosso gestore " + g.toString() + " dal database");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante la rimozione del gestore " + g.toString() + "\n" + e.toString());
		}
		finally {DbUtils.closeStatement(ps); }
	}
	
	/**aggiunge il gestore g al database*/
	@Override
	public void addGestore(Gestore g) {
		Objects.requireNonNull(g);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO gestori (username, nome, cognome,codicefiscale) VALUES (?, ?, ?,?)");
			ps.setString(1, g.getUsername());
			ps.setString(2, g.getNome());
			ps.setString(3, g.getCognome());
			ps.setString(4, g.getCodiceFiscale());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Inserito gestore " + g.toString() + " dal database");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento del gestore " + g.toString() + "\n" + e.toString());
			return;
		}
		finally {DbUtils.closeStatement(ps); }
	}

	@Override
	public void addGestore(String username, String nome, String cognome, String codiceFiscale) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		Objects.requireNonNull(codiceFiscale);
		Gestore g = new Gestore(username, nome, cognome, codiceFiscale);
		addGestore(g);
	}
	
	/**resistuice il gestore con username indicato dal database*/
	@Override
	public Gestore getGestoreByUsername(String username) {
		Objects.requireNonNull(username);
		Gestore g = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM votoelettronico.gestori WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) 
				g = new Gestore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"));
			Logging.infoMessage(this.getClass(), "Ottenuto gestore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento dell'gestore con username" + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return g;
	}
	
	/**Restituisce la SaltedPassword dato l'username del gestore dal database*/
	@Override
	public SaltedPassword getPasswordGestoreByUsername(String username) {
		Objects.requireNonNull(username);
		SaltedPassword sp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM votoelettronico.passwordgestori WHERE gestori = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();	
			if (rs.next()) 
				sp = new SaltedPassword(rs.getString("hash"), rs.getString("salt"));
			Logging.infoMessage(this.getClass(), "Ottenuta password dell'gestore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento della password dell'gestore con username " + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return sp;
	}
	
	/**salva la SaltedPassword per il gestore con username indicato nel database*/
	@Override
	public void setPasswordGestoreByUsername(String username, SaltedPassword sp) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(sp);
		PreparedStatement ps = null;
		try {
			String query;
			if (getPasswordGestoreByUsername(username) == null) {
				query = "INSERT INTO passwordgestori (salt, hash, gestori) VALUES (?, ?, ?)";
			} else {
				query = "UPDATE passwordgestori SET salt = ?, hash = ? WHERE gestori = ?";
			}
			ps = connection.prepareStatement(query);
			ps.setString(1, sp.getSalt());
			ps.setString(2, sp.getHash());
			ps.setString(3, username);
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Modificata password del gestore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante la modifica della password del gestore con username " + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
}
