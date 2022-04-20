package com.gbrsni.votoelettronico.data_access;

import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiReferendumDAO {
	public int getNVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione);
	public void increaseVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione);
}
