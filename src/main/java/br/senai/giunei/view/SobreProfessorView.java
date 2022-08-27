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

public class SobreProfessorView extends JPanel {

	/**
	 * Create the panel.
	 */
	public SobreProfessorView() {
		
		ImageIcon iconeLogo = new ImageIcon("img/foto_professor.jpg");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		
		int altura = screensize.height;
		int largura = screensize.width;
		
		setBounds(0, 0, largura-5, altura-45);
		setLayout(null);
		
		JLabel labelFoto = new JLabel();
		labelFoto.setIcon(iconeLogo);
		labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
		labelFoto.setBounds(10, 11, 200, 200);
		add(labelFoto);
		
		JLabel lblNewLabel = new JLabel("Professor");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		lblNewLabel.setBackground(new Color(238, 238, 238));
		lblNewLabel.setBounds(365, 26, 202, 39);
		add(lblNewLabel);
		
		JLabel lblTubaro = new JLabel("Rafael Meyer");
		lblTubaro.setForeground(new Color(0, 128, 0));
		lblTubaro.setFont(new Font("Dialog", Font.BOLD, 36));
		lblTubaro.setBackground(UIManager.getColor("Button.background"));
		lblTubaro.setBounds(365, 105, 242, 39);
		add(lblTubaro);
		
		JLabel lblTubaro_1 = new JLabel("rafael.meyer@edu.sc.senai.br");
		lblTubaro_1.setForeground(new Color(255, 140, 0));
		lblTubaro_1.setFont(new Font("Dialog", Font.BOLD, 30));
		lblTubaro_1.setBackground(UIManager.getColor("Button.background"));
		lblTubaro_1.setBounds(365, 188, 623, 39);
		add(lblTubaro_1);
		
	}
}
