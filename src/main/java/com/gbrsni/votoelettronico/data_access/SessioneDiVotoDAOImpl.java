package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class SessioneDiVotoDAOImpl implements SessioneDiVotoDAO {
	private Connection connection = DBConnection.getConnection();
	
	public SessioneDiVotoDAOImpl() {}
	
	/**restituisce tutte le sessioni di voto*/
	@Override
	public List<SessioneDiVoto> getAllSessioneDiVoto() {
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM sessioni");
			rs = ps.executeQuery();
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
				} catch (Exception e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto");
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce le sessione di voto con nome indicato*/
	@Override
	public List<SessioneDiVoto> getSessioneDiVotoByName(String nome) {
		Objects.requireNonNull(nome);
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			ps = connection.prepareStatement("SELECT * FROM sessioni where nome = ?");
			ps.setString(1, nome);
			rs = ps.executeQuery();
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
				} catch (Exception e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto con nome " + nome);
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto con nome " + nome);
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce le sessioni di voto con stato indicato*/
	@Override
	public List<SessioneDiVoto> getAllSessioneDiVotoByStato(StatoSessione statoSessione) {
		Objects.requireNonNull(statoSessione);
		List<SessioneDiVoto> res = new ArrayList<>();
		GetSessioneFactory sessioneFactory = new GetSessioneFactory();
		PreparedStatement ps = null;
		ResultSet rs= null;
		try {
			ps = connection.prepareStatement("SELECT * FROM sessioni WHERE stato = ?");
			ps.setString(1, statoSessione.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				try {
					LocalDate data = LocalDate.of(rs.getDate("data").getYear(), rs.getDate("data").getMonth(), rs.getDate("data").getDate());
					res.add( sessioneFactory.getSessione(ModVoto.valueOf(rs.getString("modvoto")), rs.getInt("id"), rs.getString("nome"), rs.getString("descrizione") , data, ModVittoria.valueOf(rs.getString("modvittoria")), StatoSessione.valueOf(rs.getString("stato")), rs.getInt("nvoti")));
				} catch (Exception e) {
					System.out.println("Errore durante l'ottenimento di una sessione di voto con stato " + statoSessione);
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto con stato " + statoSessione);
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**aggiorna la sessione di voto s*/
	@Override
	public void updateSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE sessioni SET nome = ?, descrizione = ?, data = ?, modvoto = ?, modvittoria = ?, stato = ?, nvoti = ? WHERE id = ?");
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
			System.out.println("Aggiornata sessione di voto " + s.toString() + " nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento della sessione di voto " + s.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**elimina la sessione di voto s */
	@Override
	public void deleteSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM sessioni  WHERE id = ?");
			ps.setInt(1, s.getId());
			ps.executeUpdate();
			System.out.println("Sessione di voto " + s.toString() + " rimossa dal database");
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione della sessione di voto " + s.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**aggiorna la sessione di voto s*/
	@SuppressWarnings("deprecation")
	@Override
	public int addSessioneDiVoto(SessioneDiVoto s) {
		Objects.requireNonNull(s);
		PreparedStatement ps = null;
		int id = 0;
		try {			
			ps = connection.prepareStatement("INSERT INTO votoelettronico.sessioni (nome, descrizione, data, modvoto, modvittoria, stato, nvoti) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
			System.out.println("Sessione di voto " + s.getNome() + " inserita nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento della sessione di voto " + s.toString());
			e.printStackTrace();
			return 0;
		}
		finally { DbUtils.closeStatement(ps); }
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
	
	/**restituisce il numero totale di sessioni di voto presenti nel database*/
	@Override
	public int getTotalNumberSessioneDiVoto() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int res = 0;
		try {
			ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM sessioni");
			rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento numero totale sessioni di voto");
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce il numero di sessioni di voto con stato indicato*/
	@Override 
	public int getNumberSessioneDiVotoByStato(StatoSessione stato) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int res = 0;
		try {
			 ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM sessioni where stato = ?");
			ps.setString(1, stato.name());
			rs = ps.executeQuery();
			if(rs.next())
				res = rs.getInt("total");
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento numero totale sessioni di voto");
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
}