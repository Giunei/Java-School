package br.senai.giunei.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class SobreAlunoView extends JPanel {

	/**
	 * Create the panel.
	 */
	public SobreAlunoView() {
		
		ImageIcon iconeFoto = new ImageIcon("img/euzinho.png");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		
		int altura = screensize.height;
		int largura = screensize.width;
		
		setBounds(0, 0, largura-5, altura-45);
		setLayout(null);
		
		JLabel labelFoto = new JLabel();
		labelFoto.setIcon(iconeFoto);
		labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
		labelFoto.setBounds(10, 11, 234, 234);
		add(labelFoto);
		
		JLabel lblNewLabel = new JLabel("Aluno");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 29));
		lblNewLabel.setBackground(new Color(238, 238, 238));
		lblNewLabel.setBounds(305, 27, 202, 39);
		add(lblNewLabel);
		
		JLabel lblTubaro = new JLabel("Giunei Philippi");
		lblTubaro.setForeground(new Color(0, 128, 0));
		lblTubaro.setFont(new Font("Dialog", Font.BOLD, 29));
		lblTubaro.setBackground(UIManager.getColor("Button.background"));
		lblTubaro.setBounds(305, 78, 264, 39);
		add(lblTubaro);
		
		JLabel lblTubaro_1 = new JLabel("giunei_machado-juni1@estudante.sc.senai.br");
		lblTubaro_1.setForeground(new Color(255, 140, 0));
		lblTubaro_1.setFont(new Font("Dialog", Font.BOLD, 26));
		lblTubaro_1.setBackground(UIManager.getColor("Button.background"));
		lblTubaro_1.setBounds(305, 140, 676, 39);
		add(lblTubaro_1);
		
		JLabel lblTubaro_1_1 = new JLabel("giunei@gmail.com");
		lblTubaro_1_1.setForeground(new Color(255, 140, 0));
		lblTubaro_1_1.setFont(new Font("Dialog", Font.BOLD, 26));
		lblTubaro_1_1.setBackground(UIManager.getColor("Button.background"));
		lblTubaro_1_1.setBounds(305, 191, 676, 39);
		add(lblTubaro_1_1);
		
	}
}
