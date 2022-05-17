package com.gbrsni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.GetSessioneFactory;
import com.gbrsni.votoelettronico.models.ModVittoria;
import com.gbrsni.votoelettronico.models.ModVoto;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

public class SessioneDiVotoTest {
	@Test
	public void testVincitore() {
		GetSessioneFactory getSessioneFactory = new GetSessioneFactory();
		SessioneDiVoto sessione = null;
		try {
			sessione = getSessioneFactory.getSessione(ModVoto.CATEGORICO, 0, "sessione test", "", LocalDate.now(), ModVittoria.MAGGIORANZA, StatoSessione.IN_CORSO, 0);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		Map<Candidato, Integer> vincitori = new HashMap<>();
		Partito p = new Partito(0, "Partito test");
		vincitori.put(new Candidato(0, "Nome0", "Cognome0", p), Integer.valueOf(10));
		vincitori.put(new Candidato(1, "Nome1", "Cognome1", p), Integer.valueOf(5));
		Candidato expected = new Candidato(2, "Nome2", "Cognome2", p);
		vincitori.put(expected, Integer.valueOf(13));
		vincitori.put(new Candidato(3, "Nome3", "Cognome3", p), Integer.valueOf(4));
		vincitori.put(new Candidato(4, "Nome4", "Cognome4", p), Integer.valueOf(0));
		
		sessione.setStatoSessione(StatoSessione.CONCLUSA);
		
		Map<Candidato, Integer> res = sessione.calcolaVincitore(vincitori);
		
		assertEquals(res.size(), 1);
		assertEquals(res.get(expected), Integer.valueOf(13));
	}
	
	@Test
	public void testPareggio() {
		GetSessioneFactory getSessioneFactory = new GetSessioneFactory();
		SessioneDiVoto sessione = null;
		try {
			sessione = getSessioneFactory.getSessione(ModVoto.CATEGORICO, 0, "sessione test", "", LocalDate.now(), ModVittoria.MAGGIORANZA, StatoSessione.IN_CORSO, 0);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		int punteggioVincitore = 15;
		
		Map<Candidato, Integer> vincitori = new HashMap<>();
		Partito p = new Partito(0, "Partito test");
		vincitori.put(new Candidato(0, "Nome0", "Cognome0", p), Integer.valueOf(10));
		vincitori.put(new Candidato(1, "Nome1", "Cognome1", p), Integer.valueOf(5));
		Candidato expected1 = new Candidato(2, "Nome2", "Cognome2", p);
		vincitori.put(expected1, Integer.valueOf(punteggioVincitore));
		Candidato expected2 = new Candidato(3, "Nome3", "Cognome3", p);
		vincitori.put(expected2, Integer.valueOf(punteggioVincitore));
		vincitori.put(new Candidato(4, "Nome4", "Cognome4", p), Integer.valueOf(0));
		
		sessione.setStatoSessione(StatoSessione.CONCLUSA);
		
		Map<Candidato, Integer> res = sessione.calcolaVincitore(vincitori);
		
		assertEquals(res.size(), 2);
		assertEquals(res.get(expected1), Integer.valueOf(punteggioVincitore));
		assertEquals(res.get(expected2), Integer.valueOf(punteggioVincitore));
	}
	
	@Test
	public void testCondizioneVoto() {
		int nVotiEspressi = 150;
		
		GetSessioneFactory getSessioneFactory = new GetSessioneFactory();
		SessioneDiVoto sessione = null;
		try {
			sessione = getSessioneFactory.getSessione(ModVoto.CATEGORICO, 0, "sessione test", "", LocalDate.now(), ModVittoria.MAGGIORANZA_ASSOLUTA, StatoSessione.CONCLUSA, nVotiEspressi);
		} catch (Exception e) {
			fail(e.toString());
		}

		assertTrue(sessione.condizioneVoto(nVotiEspressi, 76));
		assertFalse(sessione.condizioneVoto(nVotiEspressi, 7));
	}
}
