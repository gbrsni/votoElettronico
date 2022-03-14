package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class SessioneDiVotoDAOImpl implements SessioneDiVotoDAO {
	private Connection connection = DBConnection.getConnection();
	
	public SessioneDiVotoDAOImpl() {
	
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVoto() {
		List<SessioneDiVoto> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioniDiVoto");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					//res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("modVoto"), rs.getString("modVittoria"), rs.getString("stato")));
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
					//res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("modVoto"), rs.getString("modVittoria"), rs.getString("stato")));
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void addSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votoelettronico.sessioni (nome, descrizione, data, modvoto, modvittoria, stato, nvoti) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, s.getNome());
			ps.setString(2, s.getDescrizione());
			Date data = new Date(2000,4,20);
			ps.setDate(3,data);
			ps.setString(4, String.valueOf(s.getModVoto()));
			ps.setString(5, String.valueOf(s.getModVittoria()));
			ps.setString(6,String.valueOf(s.getStatoSessione()));
			ps.setInt(7,s.getNvoti());
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
	public void addSessioneDiVoto(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(statoSessione);
		Objects.requireNonNull(nvoti);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		SessioneDiVoto s = new SessioneDiVoto(id, nome, descrizione, data, modVoto, modVittoria, statoSessione, nvoti);
		addSessioneDiVoto(s);
	}

	
}
