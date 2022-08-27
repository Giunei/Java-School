package br.senai.giunei.view;

import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.enumered.Genero;
import br.senai.giunei.enumered.Perfil;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Criptografia;

public class Teste {
	
	public static void main(String[] args) {
		Usuario u = new Usuario();
		u.setNome("Admin 1");
		u.setEmail("admin");
		u.setSenha(Criptografia.md5("1"));
		u.setPerfil(Perfil.ADMIN);
		u.setGenero(Genero.MASCULINO);
		new UsuarioDAO().salvar(u);
		
		u = new Usuario();
		u.setNome("Diretora 1");
		u.setEmail("diretor");
		u.setSenha(Criptografia.md5("1"));
		u.setPerfil(Perfil.DIRETOR);
		u.setGenero(Genero.FEMENINO);
		new UsuarioDAO().salvar(u);
//		
//		u = new Usuario();
//		u.setNome("Secretaria 1");
//		u.setEmail("secretaria");
//		u.setSenha(Criptografia.md5("1"));
//		u.setPerfil(Perfil.SECRETARIA);
//		u.setGenero(Genero.FEMENINO);
//		new UsuarioDAO().salvar(u);
//		
//		u = new Usuario();
//		u.setNome("Professor 1");
//		u.setEmail("professor");
//		u.setSenha(Criptografia.md5("1"));
//		u.setPerfil(Perfil.PROFESSOR);
//		u.setGenero(Genero.MASCULINO);
//		new UsuarioDAO().salvar(u);
//		
//		u = new Usuario();
//		u.setNome("Aluno 1");
//		u.setEmail("aluno");
//		u.setSenha(Criptografia.md5("1"));
//		u.setPerfil(Perfil.ALUNO);
//		u.setGenero(Genero.FEMENINO);
//		new UsuarioDAO().salvar(u);
//		
//		u = new Usuario();
//		u.setNome("Respons�vel 1");
//		u.setEmail("responsavel");
//		u.setSenha(Criptografia.md5("1"));
//		u.setPerfil(Perfil.RESPONSAVEL);
//		u.setGenero(Genero.MASCULINO);
//		
//		new UsuarioDAO().salvar(u);
		
	}

}
