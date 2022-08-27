package br.senai.giunei.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatador {
	
	public static Date strData(String strData) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date();
		try {
			data = sdf.parse(strData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static String dataStr(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
	public static String booleanStr(Boolean b) {
		return b ? "Sim" : "Não";
	}

}
