package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SaltedPassword {
	private String hash;
	private String salt;
	
	public SaltedPassword(String hash, String salt) {
		Objects.requireNonNull(hash);
		Objects.requireNonNull(salt);
		
		this.hash = hash;
		this.salt = salt;
	}

	// Deprecated
	public String getPassword() {
		return hash;
	}

	// Deprecated
	public void setPassword(String hash) {
		Objects.requireNonNull(hash);
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		Objects.requireNonNull(hash);
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		Objects.requireNonNull(salt);
		this.salt = salt;
	}
	
	public boolean checkPassword(String password) {
		// TODO
		return false;
	}
}