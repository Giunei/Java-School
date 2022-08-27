package br.senai.giunei.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JUSTIFICATIVA")
public class Justificativa extends Registro {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "matricula", referencedColumnName = "id", nullable = false)
	private Matricula matricula;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "responsavel", referencedColumnName = "id", nullable = false)
	private Usuario responsavel;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ocorrencia", referencedColumnName = "id", nullable = false)
	private Ocorrencia ocorrencia;
	
	@Column(name = "data", nullable = false)
	private Date data;
	
	@Column(name = "detalhe", nullable = false)
	private String detalhe;
	
	
	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}


	public Usuario getResponsavel() {
		return responsavel;
	}


	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}


	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}


	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAluno: "+getMatricula().getAluno().getNome()+"\n");
		sb.append("Responsável: "+getResponsavel().getNome()+"\n");
		sb.append("Detalhe: "+getDetalhe()+"\n");
		return super.toString()+sb.toString();
	}

}
