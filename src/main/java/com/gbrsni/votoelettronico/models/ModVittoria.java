package com.gbrsni.votoelettronico.models;

public enum ModVittoria {
	MAGGIORANZA,
	MAGGIORANZA_ASSOLUTA,
	REFERENDUM_SENZA_QUORUM,
	REFERENDUM_CON_QUORUM;
	
	@Override
	  public String toString() {
	    switch(this) {
	      case MAGGIORANZA: return "MAGGIORANZA";
	      case MAGGIORANZA_ASSOLUTA: return "MAGGIORANZA_ASSOLUTA";
	      case REFERENDUM_SENZA_QUORUM: return "REFERENDUM_SENZA_QUORUM";
	      case REFERENDUM_CON_QUORUM: return "REFERENDUM_CON_QUORUM";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
