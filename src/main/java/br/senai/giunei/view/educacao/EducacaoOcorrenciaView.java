package br.senai.giunei.view.educacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import br.senai.giunei.dao.MatriculaDAO;
import br.senai.giunei.dao.OcorrenciaDAO;
import br.senai.giunei.enumered.TipoOcorrencia;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Ocorrencia;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Formatador;
import br.senai.giunei.view.DetalheView;

public class EducacaoOcorrenciaView extends JPanel {
	private JTextField idTF;
	private JTable table;
	private JTextField detalheTF;

	JTabbedPane tabbedPane;
	
	private JComboBox matriculaCB;
	private JComboBox tipoCB;

	private Ocorrencia ocorrencia = new Ocorrencia();
	private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
	
	private Contato contato;

	private List<Matricula> matriculas = new ArrayList<Matricula>();
	private JTextField dataTF;

	public EducacaoOcorrenciaView(String titulo, final Usuario logado) {

		ImageIcon iconeSalvar = new ImageIcon("img/salvar.jpg");
		ImageIcon iconeExcluir = new ImageIcon("img/deletar.png");
		ImageIcon iconeEditar = new ImageIcon("img/editar.png");
		ImageIcon iconeMostrar = new ImageIcon("img/detalhes.png");
		ImageIcon iconeBuscar = new ImageIcon("img/buscar.png");
		ImageIcon iconeLimpar = new ImageIcon("img/limpar.png");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();

		int altura = screensize.height;
		int largura = screensize.width;

		setBounds(0, 0, largura - 5, altura - 45);
		setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Dialog", Font.BOLD, 16));
		add(tabbedPane, BorderLayout.CENTER);

		JPanel painelCadastro = new JPanel();
		tabbedPane.addTab("Cadastro", null, painelCadastro, null);
		painelCadastro.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		painelCadastro.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel_2 = new JLabel(titulo);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO SALVAR Ocorrencia
				Matricula matricula = matriculas.get(matriculaCB.getSelectedIndex());
				String detalhe = detalheTF.getText();
				TipoOcorrencia tipo = TipoOcorrencia.values()[tipoCB.getSelectedIndex()];
				Date data = Formatador.strData(dataTF.getText());

				ocorrencia.setMatricula(matricula);
				ocorrencia.setData(data);
				ocorrencia.setDetalhe(detalhe);
				ocorrencia.setTipoOcorrencia(tipo);
				ocorrencia.setAnalisada(false);

				if (ocorrencia.getId() != null) {
					// atualizar
					ocorrencia.setEditor(logado);
					ocorrencia.setEdicao(new Date());
					new OcorrenciaDAO().atualizar(ocorrencia);
					JOptionPane.showMessageDialog(null, "Ocorrência atualizada");
				} else {
					// salvar
					ocorrencia.setAutor(logado);
					ocorrencia.setCriacao(new Date());
					new OcorrenciaDAO().salvar(ocorrencia);
					JOptionPane.showMessageDialog(null, "Ocorrência salva");
				}
				limparCampos();
				carregarTabela();
				carregarComboMatriculas();
			}
		});
		btnSalvar.setIcon(iconeSalvar);
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnLimpar = new JButton("LIMPAR");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(iconeLimpar);
		btnLimpar.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(5).addComponent(lblNewLabel_2).addGap(32)
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE).addGap(27)
						.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addGap(1439)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(7)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
								.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(12, Short.MAX_VALUE)));
		gl_panel_1.linkSize(SwingConstants.VERTICAL, new Component[] { btnSalvar, btnLimpar });
		gl_panel_1.linkSize(SwingConstants.HORIZONTAL, new Component[] { btnSalvar, btnLimpar });
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		painelCadastro.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel lblNewLabel = new JLabel("  ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_3 = new JLabel("   ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 1;
		panel_2.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("  ");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 1;
		panel_2.add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

		idTF = new JTextField();
		idTF.setBackground(Color.WHITE);
		idTF.setEditable(false);
		idTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		idTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_idTF = new GridBagConstraints();
		gbc_idTF.insets = new Insets(0, 0, 5, 0);
		gbc_idTF.anchor = GridBagConstraints.WEST;
		gbc_idTF.gridx = 3;
		gbc_idTF.gridy = 1;
		panel_2.add(idTF, gbc_idTF);
		idTF.setColumns(5);

		JLabel lblNewLabel_1_1 = new JLabel("Matr\u00EDcula:");
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 2;
		gbc_lblNewLabel_1_1.gridy = 2;
		panel_2.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		matriculaCB = new JComboBox();
		matriculaCB.setSelectedIndex(-1);
		matriculaCB.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_matriculaCB = new GridBagConstraints();
		gbc_matriculaCB.insets = new Insets(0, 0, 5, 0);
		gbc_matriculaCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_matriculaCB.gridx = 3;
		gbc_matriculaCB.gridy = 2;
		panel_2.add(matriculaCB, gbc_matriculaCB);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Data:");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1_1.gridy = 3;
		panel_2.add(lblNewLabel_1_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1_1);
		
		dataTF = new JTextField();
		dataTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		dataTF.setColumns(5);
		dataTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_dataTF = new GridBagConstraints();
		gbc_dataTF.insets = new Insets(0, 0, 5, 0);
		gbc_dataTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataTF.gridx = 3;
		gbc_dataTF.gridy = 3;
		panel_2.add(dataTF, gbc_dataTF);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Tipo:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1.gridy = 4;
		panel_2.add(lblNewLabel_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1);
		
		tipoCB = new JComboBox();
		tipoCB.setModel(new DefaultComboBoxModel(new String[] {"Falta", "Atraso", "Comportamento", "Assinatura do Respons\u00E1vel"}));
		tipoCB.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_tipoCB = new GridBagConstraints();
		gbc_tipoCB.insets = new Insets(0, 0, 5, 0);
		gbc_tipoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoCB.gridx = 3;
		gbc_tipoCB.gridy = 4;
		panel_2.add(tipoCB, gbc_tipoCB);
		
				JLabel lblNewLabel_1_1_1 = new JLabel("Detalhe:");
				lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
				gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_1_1_1.gridx = 2;
				gbc_lblNewLabel_1_1_1.gridy = 5;
				panel_2.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);
				
						detalheTF = new JTextField();
						detalheTF.setFont(new Font("Dialog", Font.PLAIN, 16));
						detalheTF.setColumns(5);
						detalheTF.setBorder(new EmptyBorder(2, 5, 2, 0));
						GridBagConstraints gbc_detalheTF = new GridBagConstraints();
						gbc_detalheTF.insets = new Insets(0, 0, 5, 0);
						gbc_detalheTF.fill = GridBagConstraints.HORIZONTAL;
						gbc_detalheTF.gridx = 3;
						gbc_detalheTF.gridy = 5;
						panel_2.add(detalheTF, gbc_detalheTF);

		JPanel painelLista = new JPanel();
		tabbedPane.addTab("Lista", null, painelLista, null);
		painelLista.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		painelLista.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Lista de Ocorr\u00EAncias");
		lblNewLabel_2_1.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnNewButton = new JButton("EXCLUIR");
		btnNewButton.setIcon(iconeExcluir);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					String[] opcao = { "SIM", "NÃO" };
					int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente excluir o registro?", "Atenção!",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);

					if (escolha == 0) {
						ocorrencia = ocorrencias.get(table.getSelectedRow());
						new OcorrenciaDAO().deletar(ocorrencia);
						carregarTabela();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Selecione um registro para deletar.");
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Editar
				if (table.getSelectedRow() >= 0) {
					ocorrencia = ocorrencias.get(table.getSelectedRow());
					idTF.setText(ocorrencia.getId().toString());
					detalheTF.setText(ocorrencia.getDetalhe().toString());
					tipoCB.setSelectedIndex(ocorrencia.getTipoOcorrencia().ordinal());
					matriculaCB.setSelectedIndex(acharIndiceMatricula(ocorrencia.getMatricula()));
					JOptionPane.showMessageDialog(null, "Registro carregado para edição.");
					tabbedPane.setSelectedIndex(0);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um registro para editar.");
				}
			}
		});
		btnEditar.setIcon(iconeEditar);
		btnEditar.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnMostrar = new JButton("MOSTRAR");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Mostrar
				if (table.getSelectedRow() >= 0) {
					ocorrencia = ocorrencias.get(table.getSelectedRow());

					DetalheView dialog = new DetalheView(ocorrencia.toString(), "Detalhe da Ocorrência");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma ocorrência para editar.");
				}
			}
		});
		btnMostrar.setIcon(iconeMostrar);
		btnMostrar.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_4
				.createSequentialGroup().addGap(5).addComponent(lblNewLabel_2_1)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnMostrar, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE).addGap(1282)));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup().addGap(7)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2_1)
								.addComponent(btnNewButton).addComponent(btnEditar).addComponent(btnMostrar))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel_4.linkSize(SwingConstants.HORIZONTAL, new Component[] { btnNewButton, btnEditar, btnMostrar });
		panel_4.setLayout(gl_panel_4);

		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		painelLista.add(table, BorderLayout.CENTER);

		JScrollPane scrollTable = new JScrollPane(table);
		painelLista.add(scrollTable, BorderLayout.CENTER);

		carregarTabela();
		carregarComboMatriculas();
		limparCampos();
	}

	private void carregarTabela() {
		ocorrencias = new OcorrenciaDAO().listar();
		carregarComboMatriculas();

		String[] colunas = new String[6];
		String[][] dados = new String[][] {};

		colunas[0] = "DATA";
		colunas[1] = "ALUNO";
		colunas[2] = "DISCIPLINA";
		colunas[3] = "TURMA";
		colunas[4] = "TIPO";
		colunas[5] = "DETALHE";

		if (!ocorrencias.isEmpty()) {
			dados = new String[ocorrencias.size()][6];
			for (int i = 0; i < ocorrencias.size(); i++) {
				Ocorrencia oc = ocorrencias.get(i);
				dados[i][0] = Formatador.dataStr(oc.getData());
				dados[i][1] = oc.getMatricula().getAluno().getNome();
				dados[i][2] = oc.getMatricula().getDisciplina().getNome();
				dados[i][3] = oc.getMatricula().getTurma().getNome() + " - " + oc.getMatricula().getTurma().getNivel();
				dados[i][4] = oc.getTipoOcorrencia().toString();
				dados[i][5] = oc.getDetalhe();
			}
		}

		table.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		table.setIntercellSpacing(new Dimension(width, height));
		table.setFont(new Font("Dialog", Font.PLAIN, 16));
		table.setRowHeight(28);
	}

	private void limparCampos() {
		idTF.setText("");
		detalheTF.setText("");
		Date hoje = new Date();
		dataTF.setText(Formatador.dataStr(hoje));
		tipoCB.setSelectedIndex(-1);
		matriculaCB.setSelectedIndex(-1);
	}
	
	public void carregarComboMatriculas() {
		matriculas = new MatriculaDAO().listar();
		matriculaCB.removeAllItems();
		StringBuilder sb = new StringBuilder();
		
		for (Matricula m : matriculas) {
			sb = new StringBuilder();
			sb.append(m.getAluno().getNome() + " / ");
			sb.append(m.getTurma().getNome() + " / ");
			sb.append(m.getDisciplina().getNome());
			matriculaCB.addItem(sb.toString());
		}
		
	}
	
	public int acharIndiceMatricula(Matricula matricula) {
		int indice = -1;
		for (int i = 0; i < matriculas.size(); i++) {
			if (matricula.equals(matriculas.get(i)))
				indice = i;
		}
		return indice;
	}
}
