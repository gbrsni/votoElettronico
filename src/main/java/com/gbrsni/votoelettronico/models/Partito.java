package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Partito {
	private int id;
	private String nome;
	
	public Partito(int id, String nome) {
		Objects.requireNonNull(nome);
		
		this.id = id;
		this.nome = nome;
	}
	
	
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getId() {
		return id;
	}

	@Override
	    public boolean equals(Object obj){
	    if(this == obj)
	            return true;
	        if(obj == null || obj.getClass()!= this.getClass())
	            return false;
	        Partito p = (Partito) obj;
	        return (p.id == this.id);
	    }
	      
	    @Override
	    public int hashCode(){
	        return this.id;
	    }
	    
	    @Override 
		 public String toString() {
			 return this.nome;
	    }
	    

	      
	}


