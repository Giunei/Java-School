package br.senai.giunei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DISCIPLINA")
public class Disciplina extends Registro{
	
	@Column(name = "nome", length = 45, nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "professor", referencedColumnName = "id", nullable = false)
	private Usuario professor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "turma", referencedColumnName = "id", nullable = false)
	private Turma turma;
	
	public Disciplina() {}

	public Disciplina(String nome, Usuario professor) {
		super();
		this.nome = nome;
		this.professor = professor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getProfessor() {
		return professor;
	}

	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nNome: "+getNome()+"\n");
		sb.append("Professor: "+getProfessor().getNome()+"\n");
		sb.append("Turma: "+getTurma().getNome()+"\n");
		return super.toString()+sb.toString();
	}
}
