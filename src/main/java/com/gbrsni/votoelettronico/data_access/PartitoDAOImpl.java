package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Partito;

public class PartitoDAOImpl implements PartitoDAO {
	private Connection connection = DBConnection.getConnection();

	public PartitoDAOImpl() {
		
	}

	@Override
	public List<Partito> getAllPartito() {
		List<Partito> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM partiti");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(new Partito(rs.getInt("id"), rs.getString("nome")));
			}
			
			ps.close();
		} catch (SQLException p) {
			System.out.println("Errore durante l'ottenimento di tutti gli partiti");
			p.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public Partito getPartitoById(int id) {
		Partito p = null; 

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM partiti WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				p = new Partito(rs.getInt("id"), rs.getString("nome"));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutti gli partiti");
			e.printStackTrace();
		}
		
		return p;
	}
	
	@Override
	public void updatePartito(Partito p) {
		Objects.requireNonNull(p);
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE partiti SET nome = ? WHERE id = ?");
			ps.setString(1, p.getNome());
			ps.setInt(2, p.id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del partito " + p.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornato partito " + p.toString() + " nel database");
	}

	@Override
	public void deletePartito(Partito p) {
		Objects.requireNonNull(p);
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM partiti WHERE id = ?");
			ps.setInt(1, p.id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione del partito " + p.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Rimosso partito " + p.toString() + " dal database");
	}

	@Override
	public void addPartito(Partito p) {
		Objects.requireNonNull(p);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO partiti (id, nome) VALUES (?, ?)");
			ps.setInt(1, p.id);
			ps.setString(2, p.getNome());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del partito " + p.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Inserito partito " + p.toString() + " dal database");
	}

	@Override
	public void addPartito(int id, String nome) {
		Objects.requireNonNull(nome);		
		
		Partito p = new Partito(id, nome);
		addPartito(p);
	}

}
