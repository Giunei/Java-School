package br.senai.giunei.enumered;

import java.util.ArrayList;
import java.util.List;

public enum Genero {
	
	MASCULINO		("Masculino"),
	FEMENINO		("Feminino"),
	NAO_DEFINIDO	("Não_definido");
	
	private String str;
	
	private Genero(String string) {
		this.str = string;
	}

	public String getStr() {
		return str;
	}
	
	public static Genero get(int i) {
		return Genero.values()[i];
	}
	
	public static List<String> itens(){
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < Genero.values().length; i++) {
			lista.add(Genero.get(i).getStr());
		}
		return lista;
	}
	
}
