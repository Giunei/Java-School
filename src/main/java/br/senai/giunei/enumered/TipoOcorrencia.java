package br.senai.giunei.enumered;

import java.util.ArrayList;
import java.util.List;

public enum TipoOcorrencia {
	
	FALTA 					("Falta"),
	ATRASO					("Atraso"),
	COMPORTAMENTO 			("Comportamento"),
	ASSINATURA_RESPONSAVEL  ("Assinatura do Responsável");
	
	private String str;
	
	private TipoOcorrencia(String string) {
		this.str = string;
	}

	public String getStr() {
		return str;
	}
	
	public static TipoOcorrencia get(int i) {
		return TipoOcorrencia.values()[i];
	}
	
	public static List<String> itens(){
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < TipoOcorrencia.values().length; i++) {
			lista.add(TipoOcorrencia.get(i).getStr());
		}
		return lista;
	}

}
