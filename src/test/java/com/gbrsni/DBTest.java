package com.gbrsni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.time.LocalDate;

import org.junit.Test;

import com.gbrsni.votoelettronico.data_access.ElettoreDAO;
import com.gbrsni.votoelettronico.data_access.ElettoreDAOImpl;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAO;
import com.gbrsni.votoelettronico.data_access.SessioneDiVotoDAOImpl;
import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class DBTest {
	@Test
	public void testNonExistantElettore() {
		Elettore elettore = new Elettore("testelettore", "testelettore", "testelettore",  "TSTLTR00A12Q123Q", "tessera");
		ElettoreDAO elettoreDAO = new ElettoreDAOImpl();
		elettoreDAO.deleteElettore(elettore);
		assertNull(elettoreDAO.getElettoreByUsername("testelettore"));
	}
	
	@Test
	public void testElettoreAggiuntoNumElettori() {
		Elettore elettore = new Elettore("testelettore", "testelettore", "testelettore",  "TSTLTR00A12Q123Q", "tessera");
		ElettoreDAO elettoreDAO = new ElettoreDAOImpl();
		
		int pre = elettoreDAO.getNumberElettori();
		elettoreDAO.addElettore(elettore);
		int post = elettoreDAO.getNumberElettori();
		
		assertEquals(pre + 1, post);
		elettoreDAO.deleteElettore(elettore);
	}
	
	@Test
	public void testElettoreAggiuntoGetElettore() {
		Elettore elettore = new Elettore("testelettore", "testelettore", "testelettore",  "TSTLTR00A12Q123Q", "tessera");
		ElettoreDAO elettoreDAO = new ElettoreDAOImpl();
		
		elettoreDAO.addElettore(elettore);
		
		assertEquals(elettore, elettoreDAO.getElettoreByUsername("testelettore"));
		elettoreDAO.deleteElettore(elettore);
	}
	
	@Test
	public void testCambioStatoSessione() {
		GetSessioneFactory getSessioneFactory = new GetSessioneFactory();
		SessioneDiVoto sessione = null;
		try {
			sessione = getSessioneFactory.getSessione(ModVoto.CATEGORICO, 0, "sessione test", "", LocalDate.now(), ModVittoria.MAGGIORANZA, StatoSessione.IN_CORSO, 0);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		SessioneDiVotoDAO sessioneDiVotoDAO = new SessioneDiVotoDAOImpl();
		sessioneDiVotoDAO.deleteSessioneDiVoto(sessione);
		sessioneDiVotoDAO.addSessioneDiVoto(sessione);
		sessione = sessioneDiVotoDAO.getSessioneDiVotoByName("sessione test").get(0);
		StatoSessione pre = sessioneDiVotoDAO.getSessioneDiVotoByName(sessione.getNome()).get(0).getStatoSessione();
		
		sessione.setStatoSessione(StatoSessione.CONCLUSA);
		sessioneDiVotoDAO.updateSessioneDiVoto(sessione);
		StatoSessione post = sessioneDiVotoDAO.getSessioneDiVotoByName(sessione.getNome()).get(0).getStatoSessione();
		
		assertEquals(StatoSessione.IN_CORSO, pre);
		assertEquals(StatoSessione.CONCLUSA, post);
		sessioneDiVotoDAO.deleteSessioneDiVoto(sessione);
	}
}
