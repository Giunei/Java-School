package br.senai.giunei.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.senai.giunei.enumered.Genero;
import br.senai.giunei.enumered.Perfil;

@Entity
@Table(name = "USUARIO")
public class Usuario{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome", length = 45, nullable = false)
	private String nome;
	
	@Column(name = "email", length = 60, nullable = false, unique = true)
	private String email;
	
	@Column(name = "senha", length = 300, nullable = true)
	private String senha;
	
	@Column(name = "genero", nullable = true)
	@Enumerated
	private Genero genero;
	
	@Column(name = "perfil", nullable = true)
	@Enumerated
	private Perfil perfil;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aluno", referencedColumnName = "id", nullable = true)
	private Usuario responsavel;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<Contato> contatos;
	
	public Usuario() {
		this.genero = Genero.NAO_DEFINIDO;
		this.perfil = Perfil.ALUNO;
		this.contatos = new ArrayList<Contato>();
	}

	public Usuario(Integer id, String nome, String email){
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.contatos = new ArrayList<Contato>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID: "+this.id+"\n");
		sb.append("Nome: "+this.nome+"\n");
		sb.append("Email: "+this.email+"\n");
		sb.append("Gênero: "+this.genero.getStr()+"\n");
		if(this.responsavel != null && this.perfil.equals(Perfil.ALUNO)) {
			sb.append("Responsável: "+this.responsavel.getNome()+"\n");
		}
		sb.append("Perfil: "+this.perfil.getStr());
		return sb.toString();
	}

}
