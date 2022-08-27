package br.senai.giunei.enumered;

import java.util.ArrayList;
import java.util.List;

public enum Perfil {
	
	ADMIN			("Administrador"),
	DIRETOR			("Diretor"),
	SECRETARIA 		("Secretaria"),
	PROFESSOR		("Professor"),
	ALUNO			("Aluno"),
	RESPONSAVEL		("Responsável");
	
	private String str;
	
	private Perfil(String string) {
		this.str = string;
	}

	public String getStr() {
		return str;
	}
	
	public static Perfil get(int i) {
		return Perfil.values()[i];
	}
	
	public static List<String> itens(){
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < Perfil.values().length; i++) {
			lista.add(Perfil.get(i).getStr());
		}
		return lista;
	}
	
}
