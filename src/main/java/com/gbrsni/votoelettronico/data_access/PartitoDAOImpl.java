package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Partito;

public class PartitoDAOImpl implements PartitoDAO {
	private Connection connection = DBConnection.getConnection();

	public PartitoDAOImpl() {}
	
	/**otteniene tutti i partiti dal database*/
	@Override
	public List<Partito> getAllPartito() {
		List<Partito> res = new ArrayList<>();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement("SELECT * FROM partiti");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(new Partito(rs.getInt("id"), rs.getString("nome")));
			}
			
		} catch (SQLException p) {
			System.out.println("Errore durante l'ottenimento di tutti gli partiti");
			p.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	@Override
	public Partito getPartitoById(int id) {
		Partito p = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement("SELECT * FROM partiti WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) p = new Partito(rs.getInt("id"), rs.getString("nome"));

		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutti gli partiti");
			e.printStackTrace();
		}
		
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return p;
	}
	
	@Override
	public void updatePartito(Partito p) {
		Objects.requireNonNull(p);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE partiti SET nome = ? WHERE id = ?");
			ps.setString(1, p.getNome());
			ps.setInt(2, p.id);
			ps.executeUpdate();
			System.out.println("Aggiornato partito " + p.toString() + " nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del partito " + p.toString());
			e.printStackTrace();
			return;
		}
		finally {DbUtils.closeStatement(ps); }
		
	}

	@Override
	public void deletePartito(Partito p) {
		Objects.requireNonNull(p);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM partiti WHERE id = ?");
			ps.setInt(1, p.id);
			ps.executeUpdate();
			System.out.println("Rimosso partito " + p.toString() + " dal database");
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione del partito " + p.toString());
			e.printStackTrace();
			return;
		}
		finally {DbUtils.closeStatement(ps); }
		
	}

	@Override
	public void addPartito(Partito p) {
		Objects.requireNonNull(p);
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement("INSERT INTO partiti (id, nome) VALUES (?, ?)");
			ps.setInt(1, p.id);
			ps.setString(2, p.getNome());
			ps.executeUpdate();
			System.out.println("Inserito partito " + p.toString() + " dal database");
			
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del partito " + p.toString());
			e.printStackTrace();
		}
		finally {DbUtils.closeStatement(ps); }
		
	}

	@Override
	public void addPartito(int id, String nome) {
		Objects.requireNonNull(nome);		
		
		Partito p = new Partito(id, nome);
		addPartito(p);
	}

}
