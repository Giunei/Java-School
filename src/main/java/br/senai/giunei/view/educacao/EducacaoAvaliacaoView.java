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

import br.senai.giunei.dao.AvaliacaoDAO;
import br.senai.giunei.dao.MatriculaDAO;
import br.senai.giunei.model.Avaliacao;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Formatador;
import br.senai.giunei.view.DetalheView;

public class EducacaoAvaliacaoView extends JPanel {
	private JTextField idTF;
	private JTable table;

	JTabbedPane tabbedPane;
	
	private JComboBox matriculaCB;

	private Avaliacao avaliacao = new Avaliacao();
	private List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
	
	private List<Matricula> matriculas = new ArrayList<Matricula>();
	private JTextField dataTF;
	private JTextField pesoTF;
	private JTextField notaTF;
	private JTextField professorTF;

	public EducacaoAvaliacaoView(String titulo, final Usuario logado) {

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
				Date data = Formatador.strData(dataTF.getText());
				Integer peso = Integer.parseInt(pesoTF.getText());
				Double nota = Double.parseDouble(notaTF.getText());
				Usuario professor = logado;

				avaliacao.setMatricula(matricula);
				avaliacao.setData(data);
				avaliacao.setProfessor(professor);
				avaliacao.setPeso(peso);
				avaliacao.setNota(nota);

				if (avaliacao.getId() != null) {
					// atualizar
					avaliacao.setEditor(logado);
					avaliacao.setEdicao(new Date());
					new AvaliacaoDAO().atualizar(avaliacao);
					JOptionPane.showMessageDialog(null, "Avaliação atualizada");
				} else {
					// salvar
					avaliacao.setAutor(logado);
					avaliacao.setCriacao(new Date());
					new AvaliacaoDAO().salvar(avaliacao);
					JOptionPane.showMessageDialog(null, "Avaliação salva");
				}
				limparCampos(logado);
				carregarTabela(logado);
				carregarComboMatriculas(logado);
			}
		});
		btnSalvar.setIcon(iconeSalvar);
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnLimpar = new JButton("LIMPAR");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos(logado);
			}
		});
		btnLimpar.setIcon(iconeLimpar);
		btnLimpar.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JButton btnEditar_1 = new JButton("EDITAR");
		btnEditar_1.setIcon(iconeEditar);
		btnEditar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO EDITAR
				if(table.getSelectedRow()>=0) {
					avaliacao = avaliacoes.get(table.getSelectedRow());
					
					idTF.setText(avaliacao.getId().toString());
					matriculaCB.setSelectedIndex(acharIndiceMatricula(avaliacao.getMatricula()));
					dataTF.setText(Formatador.dataStr(avaliacao.getData()));
					notaTF.setText(avaliacao.getNota().toString());
					pesoTF.setText(avaliacao.getPeso().toString());
					professorTF.setText(avaliacao.getProfessor().getNome());
					
					
					tabbedPane.setSelectedIndex(0);
					JOptionPane.showMessageDialog(null, "Registro carregado para a!"+avaliacao.getNota().toString());
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um item para Editar!");
				}
				
			}
		});
		btnEditar_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_2)
					.addGap(32)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEditar_1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(1302))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEditar_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLimpar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_panel_1.linkSize(SwingConstants.VERTICAL, new Component[] {btnSalvar, btnLimpar});
		gl_panel_1.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnSalvar, btnLimpar});
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
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Nota:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1.gridy = 4;
		panel_2.add(lblNewLabel_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1);
				
				notaTF = new JTextField();
				notaTF.setText("");
				notaTF.setFont(new Font("Dialog", Font.PLAIN, 16));
				notaTF.setColumns(5);
				notaTF.setBorder(new EmptyBorder(2, 5, 2, 0));
				notaTF.setBackground(Color.WHITE);
				GridBagConstraints gbc_notaTF = new GridBagConstraints();
				gbc_notaTF.insets = new Insets(0, 0, 5, 0);
				gbc_notaTF.fill = GridBagConstraints.HORIZONTAL;
				gbc_notaTF.gridx = 3;
				gbc_notaTF.gridy = 4;
				panel_2.add(notaTF, gbc_notaTF);
		
				JLabel lblNewLabel_1_1_1 = new JLabel("Peso:");
				lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
				gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_1_1_1.gridx = 2;
				gbc_lblNewLabel_1_1_1.gridy = 5;
				panel_2.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);
				
				pesoTF = new JTextField();
				pesoTF.setText("1");
				pesoTF.setFont(new Font("Dialog", Font.PLAIN, 16));
				pesoTF.setColumns(5);
				pesoTF.setBorder(new EmptyBorder(2, 5, 2, 0));
				pesoTF.setBackground(Color.WHITE);
				GridBagConstraints gbc_pesoTF = new GridBagConstraints();
				gbc_pesoTF.insets = new Insets(0, 0, 5, 0);
				gbc_pesoTF.fill = GridBagConstraints.HORIZONTAL;
				gbc_pesoTF.gridx = 3;
				gbc_pesoTF.gridy = 5;
				panel_2.add(pesoTF, gbc_pesoTF);
				
				JLabel lblNewLabel_1_1_1_1 = new JLabel("Professor:");
				lblNewLabel_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_lblNewLabel_1_1_1_1 = new GridBagConstraints();
				gbc_lblNewLabel_1_1_1_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1_1_1_1.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel_1_1_1_1.gridx = 2;
				gbc_lblNewLabel_1_1_1_1.gridy = 6;
				panel_2.add(lblNewLabel_1_1_1_1, gbc_lblNewLabel_1_1_1_1);
				
				professorTF = new JTextField();
				professorTF.setText("");
				professorTF.setFont(new Font("Dialog", Font.PLAIN, 16));
				professorTF.setEditable(false);
				professorTF.setColumns(5);
				professorTF.setBorder(new EmptyBorder(2, 5, 2, 0));
				professorTF.setBackground(Color.WHITE);
				GridBagConstraints gbc_professorTF = new GridBagConstraints();
				gbc_professorTF.fill = GridBagConstraints.HORIZONTAL;
				gbc_professorTF.gridx = 3;
				gbc_professorTF.gridy = 6;
				panel_2.add(professorTF, gbc_professorTF);

		JPanel painelLista = new JPanel();
		tabbedPane.addTab("Lista", null, painelLista, null);
		painelLista.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		painelLista.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Lista de Avalia\u00E7\u00F5es");
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
						avaliacao = avaliacoes.get(table.getSelectedRow());
						new AvaliacaoDAO().deletar(avaliacao);
						carregarTabela(logado);
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
				if(table.getSelectedRow()>=0) {
					avaliacao = avaliacoes.get(table.getSelectedRow());
					
					idTF.setText(avaliacao.getId().toString());
					matriculaCB.setSelectedIndex(acharIndiceMatricula(avaliacao.getMatricula()));
					dataTF.setText(Formatador.dataStr(avaliacao.getData()));
					notaTF.setText(avaliacao.getNota().toString());
					pesoTF.setText(avaliacao.getPeso().toString());
					professorTF.setText(avaliacao.getProfessor().getNome());
					
					
					tabbedPane.setSelectedIndex(0);
					JOptionPane.showMessageDialog(null, "Registro carregado para Edição!");
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um item para Editar!");
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
					avaliacao = avaliacoes.get(table.getSelectedRow());

					DetalheView dialog = new DetalheView(avaliacao.toString(), "Detalhe da Ocorrência");
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

		carregarTabela(logado);
		carregarComboMatriculas(logado);
		limparCampos(logado);
	}

	private void carregarTabela(Usuario professor) {
		avaliacoes = new AvaliacaoDAO().listarPorProfessor(professor);

		String[] colunas = new String[6];
		String[][] dados = new String[][] {};

		colunas[0] = "DATA";
		colunas[1] = "ALUNO";
		colunas[2] = "DISCIPLINA";
		colunas[3] = "TURMA";
		colunas[4] = "NOTA";
		colunas[5] = "PESO";

		if (!avaliacoes.isEmpty()) {
			dados = new String[avaliacoes.size()][6];
			for (int i = 0; i < avaliacoes.size(); i++) {
				Avaliacao av = avaliacoes.get(i);
				dados[i][0] = Formatador.dataStr(av.getData());
				dados[i][1] = av.getMatricula().getAluno().getNome();
				dados[i][2] = av.getMatricula().getDisciplina().getNome();
				dados[i][3] = av.getMatricula().getTurma().getNome() + " - " + av.getMatricula().getTurma().getNivel();
				dados[i][4] = av.getNota().toString();
				dados[i][5] = av.getPeso().toString();
			}
		}

		table.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		table.setIntercellSpacing(new Dimension(width, height));
		table.setFont(new Font("Dialog", Font.PLAIN, 16));
		table.setRowHeight(28);
	}

	private void limparCampos(Usuario logado) {
		idTF.setText("");
		Date hoje = new Date();
		dataTF.setText(Formatador.dataStr(hoje));
		matriculaCB.setSelectedIndex(-1);
		notaTF.setText("");
		pesoTF.setText("");
		professorTF.setText(logado.getNome());
	}
	
	@SuppressWarnings("unchecked")
	public void carregarComboMatriculas(Usuario professor) {
		matriculas = new MatriculaDAO().listarPorProfessor(professor);
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
