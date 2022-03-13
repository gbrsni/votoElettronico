package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;

public class CandidatoDAOImpl implements CandidatoDAO {
	private Connection connection;

	public CandidatoDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Candidato> getAllCandidato() {
		List<Candidato> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM candidati");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				// TODO: Qui gestire il partito è un po' spinoso con le foreign key
				res.add(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), null));
			}
			
			ps.close();
		} catch (SQLException c) {
			System.out.println("Errore durante l'ottenimento di tutti gli candidati");
			c.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void updateCandidato(Candidato c) {
		Objects.requireNonNull(c);
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE candidati SET nome = ?, cognome = ?, partito = ? WHERE id = ?");
			ps.setString(1, c.getNome());
			ps.setString(2, c.getCognome());
			ps.setInt(3, c.getPartito().id);
			ps.setInt(4, c.id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del candidato " + c.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornato candidato " + c.toString() + " nel database");
	}

	@Override
	public void deleteCandidato(Candidato c) {
		Objects.requireNonNull(c);
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM candidati WHERE id = ?");
			ps.setInt(1, c.id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione del candidato " + c.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Rimosso candidato " + c.toString() + " dal database");
	}

	@Override
	public void addCandidato(Candidato c) {
		Objects.requireNonNull(c);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO candidati (id, nome, cognome, partito) VALUES (?, ?, ?, ?)");
			ps.setInt(1, c.id);
			ps.setString(2, c.getNome());
			ps.setString(3, c.getCognome());
			ps.setInt(4, c.getPartito().id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del candidato " + c.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Inserito candidato " + c.toString() + " dal database");
	}

	@Override
	public void addCandidato(int id, String nome, String cognome, Partito partito) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);		
		
		Candidato c = new Candidato(id, nome, cognome, partito);
		addCandidato(c);
	}

}
