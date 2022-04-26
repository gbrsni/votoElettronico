package com.gbrsni.votoelettronico.models;

public enum ModVoto {
	ORDINALE,
	CATEGORICO,
	CATEGORICO_CON_PREFERENZE,
	REFERENDUM;
	
	 @Override
	  public String toString() {
	    switch(this) {
	      case ORDINALE: return "ORDINALE";
	      case CATEGORICO: return "CATEGORICO";
	      case CATEGORICO_CON_PREFERENZE: return "CATEGORICO_CON_PREFERENZE";
	      case REFERENDUM: return "REFERENDUM";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
