package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SaltedPassword;

public class ElettoreDAOImpl implements ElettoreDAO {
	private Connection connection = DBConnection.getConnection();

	public ElettoreDAOImpl() {
	}
	
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
			
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutti gli elettori");
			e.printStackTrace();
		}
		
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}

	/**Aggiorna elettore e*/
	@Override
	public void updateElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement("UPDATE elettori SET nome = ?, cognome = ?, codicefiscale = ?, tesseraelettorale = ? WHERE username = ?");
			ps.setString(1, e.getNome());
			ps.setString(2, e.getCognome());
			ps.setString(3, e.getTesseraElettorale());
			ps.setString(4, e.getCodiceFiscale());
			ps.setString(5, e.getUsername());
			ps.executeUpdate();
			System.out.println("Aggiornato elettore " + e.toString() + " nel database");
		} catch (SQLException ex) {
			System.out.println("Errore durante l'aggiornamento dell'elettore " + e.toString());
			ex.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**elimina elettore e*/
	@Override
	public void deleteElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM elettori WHERE username = ?");
			ps.setString(1, e.getUsername());
			ps.executeUpdate();
			System.out.println("Rimosso elettore " + e.toString() + " dal database");
		} catch (SQLException ex) {
			System.out.println("Errore durante la rimozione dell'elettore " + e.toString());
			ex.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**aggiunti elettore e */
	@Override
	public void addElettore(Elettore e) {
		Objects.requireNonNull(e);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO elettori (username, nome, cognome, codicefiscale, tesseraelettorale) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, e.getUsername());
			ps.setString(2, e.getNome());
			ps.setString(3, e.getCognome());
			ps.setString(4, e.getTesseraElettorale());
			ps.setString(5, e.getCodiceFiscale());
			ps.executeUpdate();
			System.out.println("Inserito elettore " + e.toString() + " dal database");
		} catch (SQLException ex) {
			System.out.println("Errore durante l'inserimento dell'elettore " + e.toString());
			ex.printStackTrace();
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

	/**restituisce l'elettore dato l'username */
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
			System.out.println("Ottenuto elettore con username " + username);
		} catch (SQLException ex) {
			System.out.println("Errore durante l'ottenimento dell'elettore con username" + username);
			ex.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return e;
	}
	
	/**Restituisce l'elettore data la tessera Elettorale*/
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
			
			System.out.println("Ottenuto elettore con tessera elettorale " + tesseraElettorale);
			
		} catch (SQLException ex) {
			System.out.println("Errore durante l'ottenimento dell'elettore con tessera elettorale" + tesseraElettorale);
			ex.printStackTrace();
		}
		
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return e;
	}

	/**resistuisce la SaltedPassword dato l'username dell'elettore*/
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
			
			System.out.println("Ottenuta password dell'elettore con username " + username);
		} catch (SQLException ex) {
			System.out.println("Errore durante l'ottenimento della password dell'elettore con username" + username);
			ex.printStackTrace();
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
			System.out.println("Modificata password dell'elettore con username " + username);
		} catch (SQLException ex) {
			System.out.println("Errore durante la modifica della password dell'elettore con username " + username);
			ex.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
}
