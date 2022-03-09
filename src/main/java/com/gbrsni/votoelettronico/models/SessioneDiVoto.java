package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	private ModalitąDiVoto modalitąDiVoto;
	private ModalitąDiVittoria modalitąDiVittoria;
	
	public SessioneDiVoto(int id, String nome, ModalitąDiVoto modalitąDiVoto, ModalitąDiVittoria modalitąDiVittoria) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalitąDiVoto);
		Objects.requireNonNull(modalitąDiVittoria);
		
		this.id = id;
		this.nome = nome;
		this.modalitąDiVoto = modalitąDiVoto;
		this.modalitąDiVittoria = modalitąDiVittoria;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	// TODO: toString()
}
