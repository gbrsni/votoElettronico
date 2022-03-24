package com.gbrsni.votoelettronico.models;

import java.util.Objects;


public class Partito{
	public final int id;
	private String nome;
	
	public Partito(int id, String nome) {
		Objects.requireNonNull(nome);
		
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	 @Override 
	 public String toString() {
		 return this.nome;
	 }
	 
	 @Override
	    public boolean equals(Object obj){
	    
	    if(this == obj)
	            return true;
	      
	        if(obj == null || obj.getClass()!= this.getClass())
	            return false;
	          
	        Partito p = (Partito) obj;
	          
	        return (p.nome.equals(this.nome)  && p.id == this.id);
	    }
	      
	    @Override
	    public int hashCode(){
	        return this.id;
	    }
	      
	}


