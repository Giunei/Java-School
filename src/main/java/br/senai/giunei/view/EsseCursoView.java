package br.senai.giunei.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorBuilderImpl;

import java.awt.BorderLayout;
import javax.swing.JTable;

public class EsseCursoView extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public EsseCursoView() {

		ImageIcon iconeLogo = new ImageIcon("img/senai.png");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();

		int altura = screensize.height;
		int largura = screensize.width;

		setBounds(0, 0, largura - 5, altura - 45);
		setLayout(null);

		JLabel labelLogo = new JLabel();
		labelLogo.setIcon(iconeLogo);
		labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogo.setBounds(10, 11, 320, 240);
		add(labelLogo);

		JLabel lblNewLabel = new JLabel("SENAI SC");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		lblNewLabel.setBackground(new Color(238, 238, 238));
		lblNewLabel.setBounds(365, 26, 202, 39);
		add(lblNewLabel);

		JLabel lblTubaro = new JLabel("Tubar\u00E3o");
		lblTubaro.setForeground(new Color(0, 128, 0));
		lblTubaro.setFont(new Font("Dialog", Font.BOLD, 36));
		lblTubaro.setBackground(UIManager.getColor("Button.background"));
		lblTubaro.setBounds(365, 105, 165, 39);
		add(lblTubaro);

		JLabel lblTubaro_1 = new JLabel("T\u00E9cnico em Desenvolvimento de Sistemas");
		lblTubaro_1.setForeground(new Color(255, 140, 0));
		lblTubaro_1.setFont(new Font("Dialog", Font.BOLD, 30));
		lblTubaro_1.setBackground(UIManager.getColor("Button.background"));
		lblTubaro_1.setBounds(365, 188, 623, 39);
		add(lblTubaro_1);

		JPanel panel = new JPanel();
		panel.setBounds(10, 285, 1000, 331);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setFont(new Font("Dialog", Font.PLAIN, 18));
		panel.add(table, BorderLayout.CENTER);

		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		carregarTabela();
	}

	public void carregarTabela() {
		String[] colunas = new String[3];
		String[][] dados = new String[11][3];
		DefaultTableCellRenderer alinhamento = new DefaultTableCellRenderer();

		colunas[0] = "MÓDULO";
		colunas[1] = "UNIDADE CURRICULAR";
		colunas[2] = "CARGA HORÁRIA";

		// linha1
		dados[0][0] = "BÁSICO";
		dados[0][1] = "Comunicação Oral e Escrita";
		dados[0][2] = "60 horas";

		// linha2
		dados[1][0] = "BÁSICO";
		dados[1][1] = "Fundamentos da Tecnologia da Informação";
		dados[1][2] = "40 horas";

		// linha3
		dados[2][0] = "BÁSICO";
		dados[2][1] = "Informática Aplicada";
		dados[2][2] = "80 horas";

		// linha4
		dados[3][0] = "BÁSICO";
		dados[3][1] = "Lógica de Programação";
		dados[3][2] = "160 horas";

		// linha5
		dados[4][0] = "ESPECÍFICO I";
		dados[4][1] = "Banco de Dados";
		dados[4][2] = "140 horas";

		// linha6
		dados[5][0] = "ESPECÍFICO I";
		dados[5][1] = "Programação de Aplicativos";
		dados[5][2] = "160 horas";

		// linha7
		dados[6][0] = "ESPECÍFICO II";
		dados[6][1] = "Modelagem de Sistemas";
		dados[6][2] = "80 horas";

		// linha8
		dados[7][0] = "ESPECÍFICO II";
		dados[7][1] = "Desenvolvimento de Sistemas";
		dados[7][2] = "160 horas";

		// linha9
		dados[8][0] = "ESPECÍFICO II";
		dados[8][1] = "Teste de Sistemas";
		dados[8][2] = "60 horas";

		// linha10
		dados[9][0] = "ESPECÍFICO II";
		dados[9][1] = "Implantação de Sistemas";
		dados[9][2] = "30 horas";

		// linha11
		dados[10][0] = "ESPECÍFICO II";
		dados[10][1] = "Manutenção de Sistemas";
		dados[10][2] = "30 horas";

		table.setModel(new DefaultTableModel(dados, colunas));
		table.setRowHeight(28);
		table.getColumnModel().getColumn(0).setPreferredWidth(240);
		table.getColumnModel().getColumn(1).setPreferredWidth(700);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
		
		alinhamento = new DefaultTableCellRenderer();
		alinhamento.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(alinhamento);
		table.getColumnModel().getColumn(2).setCellRenderer(alinhamento);
	}

}
