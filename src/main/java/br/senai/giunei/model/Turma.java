package br.senai.giunei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TURMA")
public class Turma extends Registro{
	
	@Column(name = "nome", length = 15, nullable = false)
	private String nome;
	
	@Column(name = "nivel", length = 20, nullable = false)
	private String nivel;
	
	@Column(name = "sala", length = 20, nullable = false)
	private String sala;
	
	public Turma() {
	}

	public Turma(String nome, String sala) {
		super();
		this.nome = nome;
		this.sala = sala;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	
	@Override
	public String toString() {
		return getNome() + ", " + getNivel() + ", "+ getSala();
	}

}
