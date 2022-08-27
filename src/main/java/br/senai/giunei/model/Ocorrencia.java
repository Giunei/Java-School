package br.senai.giunei.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.senai.giunei.enumered.TipoOcorrencia;

@Entity
@Table(name = "OCORRENCIA")
public class Ocorrencia extends Registro {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "matricula", referencedColumnName = "id", nullable = false)
	private Matricula matricula;
	
	@Column(name = "data", nullable = false)
	private Date data;
	
	@Column(name = "detalhe", length = 300 , nullable = false)
	private String detalhe;
	
	@Column(name = "tipoOcorrencia", nullable = false)
	@Enumerated
	private TipoOcorrencia tipoOcorrencia;
	
	@Column(name = "analisada", nullable = false)
	private Boolean analisada;
	
	public Ocorrencia(Usuario aluno, Disciplina disciplina, Turma turma, Boolean efetivado) {
		super();
	}

	public Ocorrencia() {
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public TipoOcorrencia getTipoOcorrencia() {
		return tipoOcorrencia;
	}

	public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}


	public Boolean getAnalisada() {
		return analisada;
	}


	public void setAnalisada(Boolean analisada) {
		this.analisada = analisada;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nNome: "+getMatricula().getAluno().getNome()+"\n");
		sb.append("Tipo: "+getTipoOcorrencia().toString()+"\n");
		sb.append("Detalhe: "+getDetalhe()+"\n");
		return super.toString()+sb.toString();
	}


}
