package br.senai.giunei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.senai.giunei.enumered.ResultadoMatricula;

@Entity
@Table(name = "MATRICULA")
public class Matricula extends Registro {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aluno", referencedColumnName = "id", nullable = false)
	private Usuario aluno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "disciplina", referencedColumnName = "id", nullable = false)
	private Disciplina disciplina;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "turma", referencedColumnName = "id", nullable = false)
	private Turma turma;
	
	@Column(name = "media", nullable = false)
	private Double media;
	
	@Column(name = "finalizada")
	private Boolean finalizada;
	
	@Column(name = "efetivada")
	private Boolean efetivada;
	
	@Column(name = "resultado")
	@Enumerated
	private ResultadoMatricula resultado;
	
	public Matricula() {
		this.media = 0D;
	}

	public Matricula(Usuario aluno, Disciplina disciplina, Turma turma, Boolean efetivado) {
		super();
		this.media = 0D;
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.turma = turma;
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public Usuario getAluno() {
		return aluno;
	}

	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public ResultadoMatricula getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoMatricula resultado) {
		this.resultado = resultado;
	}

	public Boolean getEfetivada() {
		return efetivada;
	}

	public void setEfetivada(Boolean efetivada) {
		this.efetivada = efetivada;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAluno: "+getAluno().getNome()+"\n");
		sb.append("Disciplina: "+getDisciplina().getNome()+"\n");
		sb.append("Turma: "+getTurma().getNome()+"\n");
		sb.append("Média: "+getMedia()+"\n");
		return super.toString()+sb.toString();
	}


}
