package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneCategorico;
import com.gbrsni.votoelettronico.models.SessioneCategoricoPreferenze;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.SessioneOrdinale;
import com.gbrsni.votoelettronico.models.SessioneReferendum;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class SessioneDiVotoDAOImpl implements SessioneDiVotoDAO {
	private Connection connection = DBConnection.getConnection();
	
	public SessioneDiVotoDAOImpl() {
	
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVoto() {
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioni");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
					
				} catch (Exception e) {
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
	public List<SessioneDiVoto> getSessioneDiVotoByName(String nome) {
		Objects.requireNonNull(nome);
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();

		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioni where nome = ?");
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));

				} catch (Exception e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto con nome " + nome);
					e.printStackTrace();
				}
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto con nome " + nome);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVotoByStato(StatoSessione statoSessione) {
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioni WHERE stato = ?");
			ps.setString(1, statoSessione.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));

				} catch (Exception e) {
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
			PreparedStatement ps = connection.prepareStatement("UPDATE sessioni SET nome = ?, descrizione = ?, data = ?, modvoto = ?, modvittoria = ?, stato = ?, nvoti = ? WHERE id = ?");
			ps.setString(1, s.getNome());
			ps.setString(2, s.getDescrizione());
			Date data = new Date(s.getData().getYear(),s.getData().getMonthValue(),s.getData().getDayOfMonth());
			ps.setDate(3,data);
			ps.setString(4, String.valueOf(s.getModVoto()));
			ps.setString(5, String.valueOf(s.getModVittoria()));
			ps.setString(6,String.valueOf(s.getStatoSessione()));
			ps.setInt(7,s.getNvoti());
			ps.setInt(8, s.getId());
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
			PreparedStatement ps = connection.prepareStatement("DELETE FROM sessioni  WHERE id = ?");
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
	public int addSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		int id = 0;
		try {			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votoelettronico.sessioni (nome, descrizione, data, modvoto, modvittoria, stato, nvoti) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, s.getNome());
			ps.setString(2, s.getDescrizione());
			Date data = new Date(s.getData().getYear(),s.getData().getMonthValue(),s.getData().getDayOfMonth());
			ps.setDate(3,data);
			ps.setString(4, String.valueOf(s.getModVoto()));
			ps.setString(5, String.valueOf(s.getModVittoria()));
			ps.setString(6,String.valueOf(s.getStatoSessione()));
			ps.setInt(7,s.getNvoti());
			ps.executeUpdate();
			ResultSet res = ps.getGeneratedKeys();
			if (res.next()) id = res.getInt(1);
			ps.close(); 
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento della sessione di voto " + s.toString());
			e.printStackTrace();
			return 0;
		}
		System.out.println("Sessione di voto " + s.getNome() + " inserita nel database");
		return id;
	}

	@Override
	public void addSessioneDiVoto(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(statoSessione);
		Objects.requireNonNull(nvoti);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		SessioneDiVoto sessione = null;
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();

		try {
			sessione = sessioneFactory.getSessione(modVoto , id, nome, descrizione , data, modVittoria, statoSessione, nvoti);

		} catch (Exception e) {
			System.out.println("Errore durante l'inserimento di una sessione di voto");
			e.printStackTrace();
		}
		addSessioneDiVoto(sessione);
	}
	
	@Override
	public int getTotalNumberSessioneDiVoto() {
		int res = 0;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM sessioni");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento numero totale sessioni di voto");
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override 
	public int getNumberSessioneDiVotoByStato(StatoSessione stato) {
		int res = 0;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM sessioni where stato = ?");
			ps.setString(1, stato.name());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento numero totale sessioni di voto");
			e.printStackTrace();
		}
		
		return res;
	}
	
}