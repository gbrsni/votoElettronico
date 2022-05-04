package com.gbrsni.votoelettronico.models;

public enum OpzioneReferendum {
	favorevole,
	contrario;
	
	 @Override
	  public String toString() {
	    switch(this) {
	      case favorevole: return "favorevole";
	      case contrario: return "contrario";
	      default: throw new IllegalArgumentException();
	    }
	 } 	 
}
