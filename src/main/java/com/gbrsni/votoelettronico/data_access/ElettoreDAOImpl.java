package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Elettore;

public class ElettoreDAOImpl implements ElettoreDAO {
	private Connection connection;

	public ElettoreDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Elettore> getAllElettore() {
		List<Elettore> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM elettori");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(new Elettore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome"), rs.getString("tesseraElettorale"), rs.getString("codiceFiscale")));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutti gli elettori");
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void updateElettore(Elettore e) {
		Objects.requireNonNull(e);
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE elettori SET nome = ?, cognome = ?, tesseraElettorale = ?, codiceFiscale = ? WHERE username = ?");
			ps.setString(1, e.getNome());
			ps.setString(2, e.getCognome());
			ps.setString(3, e.getTesseraElettorale());
			ps.setString(4, e.getCodiceFiscale());
			ps.setString(5, e.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("Errore durante l'aggiornamento dell'elettore " + e.toString());
			ex.printStackTrace();
			return;
		}
		System.out.println("Aggiornato elettore " + e.toString() + " nel database");
	}

	@Override
	public void deleteElettore(Elettore e) {
		Objects.requireNonNull(e);
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM elettori WHERE username = ?");
			ps.setString(1, e.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("Errore durante la rimozione dell'elettore " + e.toString());
			ex.printStackTrace();
			return;
		}
		System.out.println("Rimosso elettore " + e.toString() + " dal database");
	}

	@Override
	public void addElettore(Elettore e) {
		Objects.requireNonNull(e);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO elettori (username, nome, cognome, tesseraElettorale, codiceFiscale) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, e.getUsername());
			ps.setString(2, e.getNome());
			ps.setString(3, e.getCognome());
			ps.setString(4, e.getTesseraElettorale());
			ps.setString(5, e.getCodiceFiscale());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("Errore durante l'inserimento dell'elettore " + e.toString());
			ex.printStackTrace();
			return;
		}
		System.out.println("Inserito elettore " + e.toString() + " dal database");
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

}
