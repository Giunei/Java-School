package br.senai.giunei.enumered;

import java.util.ArrayList;
import java.util.List;

public enum TipoContato {
	
	EMAIL				("E-mail"),
	TELEFONE_FIXO		("Telefone Fixo"),
	TELEFONE_COMERCIAL	("Telefone Comercial"),
	CELULAR				("Celular"),
	MIDIAS_SOCIAIS		("Mídias Sociais");
	
	private String str;
	
	private TipoContato(String string) {
		this.str = string;
	}

	public String getStr() {
		return str;
	}
	
	public static TipoContato get(int i) {
		return TipoContato.values()[i];
	}
	
	public static List<String> itens(){
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < TipoContato.values().length; i++) {
			lista.add(TipoContato.get(i).getStr());
		}
		return lista;
	}
	
}
