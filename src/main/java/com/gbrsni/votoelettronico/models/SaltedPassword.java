package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SaltedPassword {
	private String password;
	private String salt;
	
	public SaltedPassword(String password, String salt) {
		Objects.requireNonNull(password);
		Objects.requireNonNull(salt);
		
		this.password = password;
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		Objects.requireNonNull(password);
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		Objects.requireNonNull(salt);
		this.salt = salt;
	}
}