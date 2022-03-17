package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.Partito;
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
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioni");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, rs.getString("modvoto"), rs.getString("modvittoria"), rs.getString("stato"), rs.getInt("nvoti")));
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
	
	//INUTILE
	@Override
	public List<SessioneDiVoto> getSessioneDiVotoByName(String nome) {
		Objects.requireNonNull(nome);
		List<SessioneDiVoto> res = new ArrayList<>();
		
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioni where nome = ?");
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add(new SessioneDiVoto(rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, rs.getString("modvoto"), rs.getString("modvittoria"), rs.getString("stato"), rs.getInt("nvoti")));
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
		SessioneDiVoto s = new SessioneDiVoto(id, nome, descrizione, data, modVoto, modVittoria, statoSessione, nvoti);
		addSessioneDiVoto(s);
	}
	
	//aggiunta partito s per la sessione p
	public void addVotiPartiti(SessioneDiVoto s, List<Partito> p) {
		Objects.requireNonNull(s);
		Objects.requireNonNull(p);
		int i = 0;
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votipartiti (sessioni, partiti, nvoti) VALUES (?, ?, ?)");
			while(i < p.size()) {
				ps.setInt(1, s.getId());
				ps.setInt(2, p.get(i).getId());
				ps.setInt(3, 0);
				ps.executeUpdate();
				i++;
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del partito  + " + p.get(i).getNome() + " per la sessione " + s.getNome());
			e.printStackTrace();
			return;
		}
		System.out.println( "Relazione partiti - sessione di voto " + s.getNome() + " aggiunta nel database");
	}
	
	
	//aggiunta candidati per la sessione di voto s
	public void addVotiCandidati(SessioneDiVoto s, List<Candidato> c){
		Objects.requireNonNull(s);
		Objects.requireNonNull(c);
		int i = 0;
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO voticandidati (sessioni, candidati, nvoti) VALUES (?, ?, ?)");
			while(i < c.size()) {
				ps.setInt(1, s.getId());
				ps.setInt(2, c.get(i).getId());
				ps.setInt(3, 0);
				ps.executeUpdate();
				i++;
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del candidato " + c.get(i).getNome() + " per la sessione di voto " + s.getNome());
			e.printStackTrace();
			return;
		}
		System.out.println("Relazione candidati - sessione di voto " + s.getNome() + " aggiunta nel database ");
	}
	
	//aggiunta entry astenuti per la sessione di voto s
	public void addVotiAstenuti(SessioneDiVoto s){
		Objects.requireNonNull(s);
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votiastenuti (sessioni, nvoti) VALUES (?, ?)");
			ps.setInt(1, s.getId());
			ps.setInt(2, 0);
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento ");
			e.printStackTrace();
			return;
		}
		System.out.println("Inserimento nel db effettuato");
	}
	
}
