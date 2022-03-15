package com.gbrsni.votoelettronico.models;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Random;

public class SaltedPassword {
	private String hash;
	private String salt;
	
	// Restituisce una stringa di 8 caratteri casuali
	public static String createSalt() {
		byte[] bytes = new byte[8];
		new Random().nextBytes(bytes);
		return new String(bytes, Charset.forName("UTF-8"));
	}

	public static String hashString(String input) {
		return String.valueOf(input.hashCode());
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
		this.hash = hashString(passwordWithSalt);
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
		return hashString(password + this.salt).equals(this.hash);
	}
}