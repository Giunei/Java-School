package br.senai.giunei.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
	
	public static String md5(String senha) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(senha.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		String senhaCriptografada = hash.toString(16);
		return senhaCriptografada;
	}

}
