package com.senai.entity;

public class Frase {
	
	private Long id;
	private String frase;
	
	
	public Frase(String f) {
		this.frase = f;
	}
	
	public Frase(Long id, String f) {
		this.id = id;
		this.frase = f;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrase() {
		return frase;
	}
	
	public void setFrase(String frase) {
		this.frase = frase;
	}
	
	@Override
	public String toString() {
		return frase.length() < 28 ? frase : frase.substring(0, 28) + "...";
	}
}
