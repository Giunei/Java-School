package br.senai.giunei.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AVALIACAO")
public class Avaliacao extends Registro {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "matricula", referencedColumnName = "id", nullable = false)
	private Matricula matricula;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "professor", referencedColumnName = "id", nullable = false)
	private Usuario professor;
	
	@Column(name = "nota", nullable = false)
	private Double nota = 0D;
	
	@Column(name = "peso", nullable = false)
	private Integer peso = 1;
	
	@Column(name = "data", nullable = false)
	private Date data;
	
	
	
	public Avaliacao() {
	}

	public Avaliacao(Usuario aluno, Disciplina disciplina, Turma turma, Boolean efetivado) {
		super();
	}


	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Usuario getProfessor() {
		return professor;
	}

	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAluno: "+getMatricula().getAluno().getNome()+"\n");
		sb.append("Disciplina: "+getMatricula().getDisciplina().getNome()+"\n");
		sb.append("Professor: "+getProfessor().getNome()+"\n");
		sb.append("Nota: "+getNota()+"\n");
		sb.append("Peso: "+getPeso());
		return super.toString()+sb.toString();
	}

}
