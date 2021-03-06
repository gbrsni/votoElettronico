package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SaltedPassword;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class ElettoreDAOImpl implements ElettoreDAO {
	private Connection connection = DBConnection.getConnection();
	
	/**restituisce tutti gli elettori*/
	@Override
	public List<Elettore> getAllElettore() {
		List<Elettore> res = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM elettori");
			rs = ps.executeQuery();
			while (rs.next()) {
				res.add(new Elettore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("tesseraelettorale")));
			}
			Logging.infoMessage(this.getClass(), "Ottenuti tutti gli elettori");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento di tutti gli elettori\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}

	/**aggiorna l'elettore e presente nel database*/
	@Override
	public void updateElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE elettori SET nome = ?, cognome = ?, codicefiscale = ?, tesseraelettorale = ? WHERE username = ?");
			ps.setString(1, e.getNome());
			ps.setString(2, e.getCognome());
			ps.setString(3, e.getCodiceFiscale());
			ps.setString(4, e.getTesseraElettorale());
			ps.setString(5, e.getUsername());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Aggiornato elettore " + e.toString() + " nel database");
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'aggiornamento dell'elettore " + e.toString() + "\n" + ex.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**elimina elettore e dal database*/
	@Override
	public void deleteElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM elettori WHERE username = ?");
			ps.setString(1, e.getUsername());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Rimosso elettore " + e.toString() + " dal database");
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante la rimozione dell'elettore " + e.toString() + "\n" + ex.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**aggiunge elettore e al database */
	@Override
	public void addElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO elettori (username, nome, cognome, codicefiscale, tesseraelettorale) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, e.getUsername());
			ps.setString(2, e.getNome());
			ps.setString(3, e.getCognome());
			ps.setString(4, e.getCodiceFiscale());
			ps.setString(5, e.getTesseraElettorale());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Inserito elettore " + e.toString() + " dal database");
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento dell'elettore " + e.toString() + "\n" + ex.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}

	@Override
	public void addElettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		Objects.requireNonNull(tesseraElettorale);
		Objects.requireNonNull(codiceFiscale);
		Elettore e = new Elettore(username, nome, cognome, tesseraElettorale, codiceFiscale);
		addElettore(e);
	}

	/**restituisce l'elettore con username indicato dal database */
	@Override
	public Elettore getElettoreByUsername(String username) {
		Objects.requireNonNull(username);
		Elettore e = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM elettori WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next())
				e = new Elettore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("tesseraelettorale"));
			Logging.infoMessage(this.getClass(), "Ottenuto elettore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento dell'elettore con username" + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return e;
	}
	
	/**restituisce l'elettore data la tessera elettorale dal database*/
	@Override
	public Elettore getElettoreByTesseraElettorale(String tesseraElettorale) {
		Objects.requireNonNull(tesseraElettorale);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Elettore e = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM elettori WHERE tesseraelettorale = ?");
			ps.setString(1, tesseraElettorale);
			rs = ps.executeQuery();
			if (rs.next()) 
				e = new Elettore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("tesseraelettorale"));
			Logging.infoMessage(this.getClass(), "Ottenuto elettore con tessera elettorale " + tesseraElettorale);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento dell'elettore con tessera elettorale" + tesseraElettorale + "\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return e;
	}

	/**resistuisce la SaltedPassword dato l'username dell'elettore dal database*/
	@Override
	public SaltedPassword getPasswordElettoreByUsername(String username) {
		Objects.requireNonNull(username);
		PreparedStatement ps = null;
		ResultSet rs = null;
		SaltedPassword sp = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM passwordelettori WHERE elettori = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) 
				sp = new SaltedPassword(rs.getString("hash"), rs.getString("salt"));
			Logging.infoMessage(this.getClass(), "Ottenuta password dell'elettore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento della password dell'elettore con username" + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return sp;
	}
	
	/**imposta SaltedPassword per l'elettore con username indicato */
	@Override
	public void setPasswordElettoreByUsername(String username, SaltedPassword sp) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(sp);
		PreparedStatement ps = null;
		try {
			String query;
			if (getPasswordElettoreByUsername(username) == null) {
				query = "INSERT INTO passwordelettori (salt, hash, elettori) VALUES (?, ?, ?)";
			} else {
				query = "UPDATE passwordelettori SET salt = ?, hash = ? WHERE elettori = ?";
			}
			ps = connection.prepareStatement(query);
			ps.setString(1, sp.getSalt());
			ps.setString(2, sp.getHash());
			ps.setString(3, username);
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Modificata password dell'elettore con username " + username);
		} catch (SQLException ex) {
			Logging.warnMessage(this.getClass(), "Errore durante la modifica della password dell'elettore con username " + username + "\n" + ex.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	@Override
	public int getNumberElettori() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int res = 0;
		try {
			ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM elettori");
			rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
			Logging.infoMessage(this.getClass(), "Ottenuto numero totale di elettori");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(),"Errore durante del numero totale degli elettori \n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
}
