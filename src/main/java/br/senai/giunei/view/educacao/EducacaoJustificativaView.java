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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import br.senai.giunei.dao.JustificativaDAO;
import br.senai.giunei.dao.OcorrenciaDAO;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Justificativa;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Ocorrencia;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Formatador;
import br.senai.giunei.view.DetalheView;

public class EducacaoJustificativaView extends JPanel {
	private JTextField idTF;
	private JTable table;
	private JTextField detalheTF;

	JTabbedPane tabbedPane;

	private Justificativa justificativa = new Justificativa();
	private Ocorrencia ocorrencia = new Ocorrencia();

	private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
	private List<Justificativa> justificativas = new ArrayList<Justificativa>();

	private Contato contato;

	private List<Matricula> matriculas = new ArrayList<Matricula>();
	private JTextField dataTF;
	private JTextField matriculaTF;
	private JTextField responsavelTF;
	private JTextField ocorrenciaTF;

	private JButton btnSalvar;
	private JTable table_concluidas;

	public EducacaoJustificativaView(String titulo, final Usuario logado) {

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

		JLabel lblNewLabel_2 = new JLabel("Justificativas Abertas");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));

		btnSalvar = new JButton("SALVAR");
		btnSalvar.setEnabled(false);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO SALVAR Ocorrencia
				String detalhe = detalheTF.getText();

				justificativa.setDetalhe(detalhe);

				if (justificativa.getId() != null) {
					// atualizar
					justificativa.setEditor(logado);
					justificativa.setEdicao(new Date());
					new JustificativaDAO().atualizar(justificativa);
					JOptionPane.showMessageDialog(null, "Justificativa atualizada");
				} else {
					// salvar
					justificativa.setAutor(logado);
					justificativa.setCriacao(new Date());
					new JustificativaDAO().salvar(justificativa);

					ocorrencia.setAnalisada(true);
					ocorrencia.setEdicao(new Date());
					ocorrencia.setEditor(logado);

					new OcorrenciaDAO().atualizar(ocorrencia);
					JOptionPane.showMessageDialog(null, "Justificativa salva");
				}
				carregarTabelaAbertas(logado);
				carregarTabelaConcluidas(logado);
				limparCampos();
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
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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

		matriculaTF = new JTextField();
		matriculaTF.setText("");
		matriculaTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		matriculaTF.setEditable(false);
		matriculaTF.setColumns(5);
		matriculaTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		matriculaTF.setBackground(Color.WHITE);
		GridBagConstraints gbc_matriculaTF = new GridBagConstraints();
		gbc_matriculaTF.insets = new Insets(0, 0, 5, 0);
		gbc_matriculaTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_matriculaTF.gridx = 3;
		gbc_matriculaTF.gridy = 2;
		panel_2.add(matriculaTF, gbc_matriculaTF);

		JLabel lblNewLabel_1_1_2 = new JLabel("RESPONS\u00C1VEL");
		lblNewLabel_1_1_2.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_2.gridx = 2;
		gbc_lblNewLabel_1_1_2.gridy = 3;
		panel_2.add(lblNewLabel_1_1_2, gbc_lblNewLabel_1_1_2);

		responsavelTF = new JTextField();
		responsavelTF.setText("");
		responsavelTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		responsavelTF.setEditable(false);
		responsavelTF.setColumns(5);
		responsavelTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		responsavelTF.setBackground(Color.WHITE);
		GridBagConstraints gbc_responsavelTF = new GridBagConstraints();
		gbc_responsavelTF.insets = new Insets(0, 0, 5, 0);
		gbc_responsavelTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_responsavelTF.gridx = 3;
		gbc_responsavelTF.gridy = 3;
		panel_2.add(responsavelTF, gbc_responsavelTF);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("OCORR\u00CANCIA:");
		lblNewLabel_1_1_2_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_2_1.gridx = 2;
		gbc_lblNewLabel_1_1_2_1.gridy = 4;
		panel_2.add(lblNewLabel_1_1_2_1, gbc_lblNewLabel_1_1_2_1);

		ocorrenciaTF = new JTextField();
		ocorrenciaTF.setText("");
		ocorrenciaTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		ocorrenciaTF.setEditable(false);
		ocorrenciaTF.setColumns(5);
		ocorrenciaTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		ocorrenciaTF.setBackground(Color.WHITE);
		GridBagConstraints gbc_ocorrenciaTF = new GridBagConstraints();
		gbc_ocorrenciaTF.insets = new Insets(0, 0, 5, 0);
		gbc_ocorrenciaTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_ocorrenciaTF.gridx = 3;
		gbc_ocorrenciaTF.gridy = 4;
		panel_2.add(ocorrenciaTF, gbc_ocorrenciaTF);

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Data:");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1_1.gridy = 5;
		panel_2.add(lblNewLabel_1_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1_1);

		dataTF = new JTextField();
		dataTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		dataTF.setColumns(5);
		dataTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_dataTF = new GridBagConstraints();
		gbc_dataTF.insets = new Insets(0, 0, 5, 0);
		gbc_dataTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataTF.gridx = 3;
		gbc_dataTF.gridy = 5;
		panel_2.add(dataTF, gbc_dataTF);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Visualizada?");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1.gridy = 6;
		panel_2.add(lblNewLabel_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1);

		JCheckBox analisadaCheck = new JCheckBox("Maque se foi visualizada");
		GridBagConstraints gbc_analisadaCheck = new GridBagConstraints();
		gbc_analisadaCheck.anchor = GridBagConstraints.WEST;
		gbc_analisadaCheck.insets = new Insets(0, 0, 5, 0);
		gbc_analisadaCheck.gridx = 3;
		gbc_analisadaCheck.gridy = 6;
		panel_2.add(analisadaCheck, gbc_analisadaCheck);

		JLabel lblNewLabel_1_1_1 = new JLabel("Detalhe:");
		lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1.gridy = 7;
		panel_2.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);

		detalheTF = new JTextField();
		detalheTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		detalheTF.setColumns(5);
		detalheTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_detalheTF = new GridBagConstraints();
		gbc_detalheTF.insets = new Insets(0, 0, 5, 0);
		gbc_detalheTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_detalheTF.gridx = 3;
		gbc_detalheTF.gridy = 7;
		panel_2.add(detalheTF, gbc_detalheTF);

		JPanel painelLista = new JPanel();
		tabbedPane.addTab("Abertas", null, painelLista, null);
		painelLista.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		painelLista.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Justificativa");
		lblNewLabel_2_1.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnEditar = new JButton("JUSTIFICAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Editar
				if (table.getSelectedRow() >= 0) {
					ocorrencia = ocorrencias.get(table.getSelectedRow());

					justificativa = new Justificativa();
					justificativa.setOcorrencia(ocorrencia);
					justificativa.setMatricula(ocorrencia.getMatricula());
					justificativa.setResponsavel(ocorrencia.getMatricula().getAluno().getResponsavel());
					justificativa.setData(new Date());

					dataTF.setText(Formatador.dataStr(justificativa.getData()));
					ocorrenciaTF.setText(ocorrencia.getDetalhe());
					matriculaTF.setText(ocorrencia.getMatricula().getAluno().getNome() + ", "
							+ ocorrencia.getMatricula().getDisciplina().getNome());
					responsavelTF.setText(ocorrencia.getMatricula().getAluno().getResponsavel().getNome());

					btnSalvar.setEnabled(true);
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

					DetalheView dialog = new DetalheView(justificativa.toString(), "Detalhe da Ocorrência");
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
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_2_1)
					.addGap(18)
					.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnMostrar, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addGap(1363))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2_1)
						.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMostrar, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_4.linkSize(SwingConstants.VERTICAL, new Component[] {btnEditar, btnMostrar});
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

		JPanel painelConcluidas = new JPanel();
		tabbedPane.addTab("Concluidas", null, painelConcluidas, null);
		painelConcluidas.setLayout(new BorderLayout(0, 0));

		JPanel panel_4_1 = new JPanel();
		painelConcluidas.add(panel_4_1, BorderLayout.NORTH);

		JButton btnMostrar_1 = new JButton("MOSTRAR");
		btnMostrar_1.setIcon(iconeMostrar);
		btnMostrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMostrar_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_4_1 = new GroupLayout(panel_4_1);
		gl_panel_4_1.setHorizontalGroup(
			gl_panel_4_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4_1.createSequentialGroup()
					.addGap(37)
					.addComponent(btnMostrar_1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addGap(1559))
		);
		gl_panel_4_1.setVerticalGroup(
			gl_panel_4_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4_1.createSequentialGroup()
					.addGap(7)
					.addComponent(btnMostrar_1)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4_1.setLayout(gl_panel_4_1);

		table_concluidas = new JTable() {
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

		painelConcluidas.add(table_concluidas, BorderLayout.CENTER);

		JScrollPane scrollConcluidas = new JScrollPane(table_concluidas);
		painelConcluidas.add(scrollConcluidas, BorderLayout.CENTER);

		limparCampos();
		carregarTabelaConcluidas(logado);
		carregarTabelaAbertas(logado);
	}

	private void carregarTabelaAbertas(Usuario responsavel) {
		ocorrencias = new OcorrenciaDAO().listar(responsavel, false);
		justificativas = new JustificativaDAO().listar(responsavel);

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
	
	@SuppressWarnings("unused")
	private void carregarTabelaConcluidas(Usuario responsavel) {
		justificativas = new JustificativaDAO().listar(responsavel);

		String[] colunas = new String[5];
		String[][] dados = new String[][] {};

		colunas[0] = "MATRICULA";
		colunas[1] = "RESPONSAVEL";
		colunas[2] = "OCORRENCIA";
		colunas[3] = "DATA";
		colunas[4] = "DETALHE";

		if (!justificativas.isEmpty()) {
			dados = new String[justificativas.size()][5];
			for (int i = 0; i < justificativas.size(); i++) {
				Justificativa js = justificativas.get(i);
				dados[i][0] = js.getMatricula().getAluno().getNome();
				dados[i][1] = js.getMatricula().getAluno().getResponsavel().getNome();
				dados[i][2] = js.getOcorrencia().getTipoOcorrencia().toString();
				dados[i][3] = Formatador.dataStr(js.getData());
				dados[i][4] = js.getDetalhe();
			}
		}

		table_concluidas.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		table_concluidas.setIntercellSpacing(new Dimension(width, height));
		table_concluidas.setFont(new Font("Dialog", Font.PLAIN, 16));
		table_concluidas.setRowHeight(28);
	}

	private void limparCampos() {
		idTF.setText("");
		detalheTF.setText("");
		dataTF.setText("");
		matriculaTF.setText("");
		ocorrenciaTF.setText("");
		responsavelTF.setText("");
		justificativa = new Justificativa();
		ocorrencia = new Ocorrencia();
		btnSalvar.setEnabled(false);
	}

}
