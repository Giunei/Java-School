package br.senai.giunei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.senai.giunei.enumered.TipoContato;

@Entity
@Table(name = "CONTATO")
public class Contato extends Registro{
	
	@Column(name = "tipo", length = 15, nullable = false)
	@Enumerated
	private TipoContato tipo;
	
	@Column(name = "detalhe", length = 120, nullable = false)
	private String detalhe;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario", referencedColumnName = "id", nullable = false)
	private Usuario usuario;
	
	
	public Contato() {
	}


	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoContato getTipo() {
		return tipo;
	}

	public void setTipo(TipoContato tipo) {
		this.tipo = tipo;
	}

}
