package com.gbrsni.votoelettronico.data_access;

import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiReferendumDAO {
	public int getNVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione);
	public void addNewVotiSessioneReferendum(SessioneDiVoto sessioneDiVoto);
	public void updateVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione, int valore);
	public void increseVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione, int valore);
}
