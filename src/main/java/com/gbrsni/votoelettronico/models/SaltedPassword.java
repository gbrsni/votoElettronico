package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SaltedPassword {
	private String hash;
	private String salt;
	
	public static String createSalt() {
		// TODO
		return null;
	}
	
	public static String hashPassword(String password) {]
		// TODO
		return null;
	}
	
	public SaltedPassword(String hash, String salt) {
		Objects.requireNonNull(hash);
		Objects.requireNonNull(salt);
		
		this.hash = hash;
		this.salt = salt;
	}
	
	public SaltedPassword(String password) {
		Objects.requireNonNull(password);

		this.setPassword(password);
	}

	public void setPassword(String password) {
		Objects.requireNonNull(password);
		
		this.salt = createSalt();
		String passwordWithSalt = password + this.salt;
		this.hash = hashPassword(passwordWithSalt);
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
		return hashPassword(password + this.salt).equals(this.hash);
	}
}