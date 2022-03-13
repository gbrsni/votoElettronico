package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class SessioneDiVotoDAOImpl implements SessioneDiVotoDAO {
	private Connection connection;
	
	public SessioneDiVotoDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVoto() {
		List<SessioneDiVoto> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioniDiVoto");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("modVoto"), rs.getString("modVittoria"), rs.getString("stato")));
				} catch (IllegalArgumentException e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto");
					e.printStackTrace();
				}
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto");
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVotoByStato(StatoSessione statoSessione) {
		List<SessioneDiVoto> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioniDiVoto WHERE stato = ?");
			ps.setString(1, statoSessione.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("modVoto"), rs.getString("modVittoria"), rs.getString("stato")));
				} catch (IllegalArgumentException e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto con stato " + statoSessione);
					e.printStackTrace();
				}
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto con stato " + statoSessione);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void updateSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE sessioniDiVoto SET nome = ?, stato = ? WHERE id = ?");
			ps.setString(1, s.getNome());
			ps.setString(2, s.getStatoSessione().toString());
			ps.setInt(3, s.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornata sessione di voto " + s.toString() + " nel database");
	}

	@Override
	public void deleteSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM sessioniDiVoto  WHERE id = ?");
			ps.setInt(1, s.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Sessione di voto " + s.toString() + " rimossa dal database");
	}
	
	@Override
	public void addSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO sessioniDiVoto (id, nome, stato) VALUES (?, ?, ?)");
			ps.setInt(1, s.getId());
			ps.setString(2, s.getNome());
			ps.setString(3, s.getStatoSessione().toString());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Sessione di voto " + s.toString() + " inserita nel database");
	}

	@Override
	public void addSessioneDiVoto(int id, String nome, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		SessioneDiVoto s = new SessioneDiVoto(id, nome, modVoto, modVittoria, statoSessione);
		addSessioneDiVoto(s);
	}

}
