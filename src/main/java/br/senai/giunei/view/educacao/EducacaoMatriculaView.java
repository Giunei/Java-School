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
import javax.swing.JRadioButton;
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

import org.hibernate.loader.plan.build.internal.returns.EntityAttributeFetchImpl;

import br.senai.giunei.dao.DisciplinaDAO;
import br.senai.giunei.dao.MatriculaDAO;
import br.senai.giunei.dao.TurmaDAO;
import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.enumered.Perfil;
import br.senai.giunei.enumered.ResultadoMatricula;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Turma;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Formatador;
import br.senai.giunei.view.DetalheView;

public class EducacaoMatriculaView extends JPanel {
	private JTextField idTF;
	private JTable table;

	JTabbedPane tabbedPane;

	private Usuario professor = new Usuario();
	private Matricula matricula = new Matricula();
	private List<Matricula> matriculas = new ArrayList<Matricula>();
	private JTextField educacaoMediaTF;
	
	List<Usuario> alunos = new ArrayList<Usuario>();
	List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	List<Turma> turmas = new ArrayList<Turma>();
	
	JComboBox educacaoAlunoCB;
	JComboBox educacaoDisciplinaCB;
	JComboBox educacaoTurmaCB;
	JComboBox educacaoResultadoCB;
	
	JRadioButton educacaoEfetivadaCheck;
	JRadioButton educacaoFinalizadaCheck;

	public EducacaoMatriculaView(String titulo, final Usuario logado) {

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

		JPanel panel = new JPanel();
		tabbedPane.addTab("Cadastro", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel_2 = new JLabel(titulo);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO SALVAR TURMA
				Usuario aluno = alunos.get(educacaoAlunoCB.getSelectedIndex());
				Disciplina disciplina = disciplinas.get(educacaoDisciplinaCB.getSelectedIndex());
				Turma turma = turmas.get(educacaoTurmaCB.getSelectedIndex());
				
				Boolean efetivada = educacaoEfetivadaCheck.isSelected();
				Boolean finalizada = educacaoFinalizadaCheck.isSelected();
				
				ResultadoMatricula resultado = ResultadoMatricula.values()[educacaoResultadoCB.getSelectedIndex()];
				
				matricula.setAluno(aluno);
				matricula.setTurma(turma);
				matricula.setDisciplina(disciplina);
				matricula.setFinalizada(finalizada);
				matricula.setEfetivada(efetivada);
				matricula.setResultado(resultado);

				if (matricula.getId() != null) {
					// atualizar
					matricula.setEditor(logado);
					matricula.setEdicao(new Date());
					new MatriculaDAO().atualizar(matricula);
					JOptionPane.showMessageDialog(null, "Matrícula atualizada");
				} else {
					// salvar
					matricula.setMedia(0D);
					matricula.setAutor(logado);
					matricula.setCriacao(new Date());
					new MatriculaDAO().salvar(matricula);
					JOptionPane.showMessageDialog(null, "Matrícula salva");
				}
				limparCampos();
				carregarTabela();
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
		panel.add(panel_2, BorderLayout.CENTER);
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

		JLabel lblauno = new JLabel("ALUNO:");
		lblauno.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblauno = new GridBagConstraints();
		gbc_lblauno.anchor = GridBagConstraints.EAST;
		gbc_lblauno.insets = new Insets(0, 0, 5, 5);
		gbc_lblauno.gridx = 2;
		gbc_lblauno.gridy = 2;
		panel_2.add(lblauno, gbc_lblauno);
		
		educacaoAlunoCB = new JComboBox();
		educacaoAlunoCB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_educacaoAlunoCB = new GridBagConstraints();
		gbc_educacaoAlunoCB.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoAlunoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_educacaoAlunoCB.gridx = 3;
		gbc_educacaoAlunoCB.gridy = 2;
		panel_2.add(educacaoAlunoCB, gbc_educacaoAlunoCB);

		JLabel lblaa = new JLabel("DISCIPLINA:");
		lblaa.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblaa = new GridBagConstraints();
		gbc_lblaa.anchor = GridBagConstraints.EAST;
		gbc_lblaa.insets = new Insets(0, 0, 5, 5);
		gbc_lblaa.gridx = 2;
		gbc_lblaa.gridy = 3;
		panel_2.add(lblaa, gbc_lblaa);
		
		educacaoDisciplinaCB = new JComboBox();
		educacaoDisciplinaCB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_educacaoDisciplinaCB = new GridBagConstraints();
		gbc_educacaoDisciplinaCB.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoDisciplinaCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_educacaoDisciplinaCB.gridx = 3;
		gbc_educacaoDisciplinaCB.gridy = 3;
		panel_2.add(educacaoDisciplinaCB, gbc_educacaoDisciplinaCB);
		
		JLabel lblturma = new JLabel("TURMA:");
		lblturma.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblturma = new GridBagConstraints();
		gbc_lblturma.anchor = GridBagConstraints.EAST;
		gbc_lblturma.insets = new Insets(0, 0, 5, 5);
		gbc_lblturma.gridx = 2;
		gbc_lblturma.gridy = 4;
		panel_2.add(lblturma, gbc_lblturma);
		
		educacaoTurmaCB = new JComboBox();
		educacaoTurmaCB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_educacaoTurmaCB = new GridBagConstraints();
		gbc_educacaoTurmaCB.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoTurmaCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_educacaoTurmaCB.gridx = 3;
		gbc_educacaoTurmaCB.gridy = 4;
		panel_2.add(educacaoTurmaCB, gbc_educacaoTurmaCB);
		
		JLabel educacaoEfetivadaCB = new JLabel("EFETIVADA?");
		educacaoEfetivadaCB.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_educacaoEfetivadaCB = new GridBagConstraints();
		gbc_educacaoEfetivadaCB.insets = new Insets(0, 0, 5, 5);
		gbc_educacaoEfetivadaCB.gridx = 2;
		gbc_educacaoEfetivadaCB.gridy = 5;
		panel_2.add(educacaoEfetivadaCB, gbc_educacaoEfetivadaCB);
		
		educacaoEfetivadaCheck = new JRadioButton("Marque caso a matr\u00EDcula est\u00E1 efetivada");
		educacaoEfetivadaCheck.setFont(new Font("Tahoma", Font.PLAIN, 12));
		educacaoEfetivadaCheck.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_educacaoEfetivadaCheck = new GridBagConstraints();
		gbc_educacaoEfetivadaCheck.anchor = GridBagConstraints.WEST;
		gbc_educacaoEfetivadaCheck.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoEfetivadaCheck.gridx = 3;
		gbc_educacaoEfetivadaCheck.gridy = 5;
		panel_2.add(educacaoEfetivadaCheck, gbc_educacaoEfetivadaCheck);
		
		JLabel lblMdia = new JLabel("M\u00C9DIA:");
		lblMdia.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblMdia = new GridBagConstraints();
		gbc_lblMdia.anchor = GridBagConstraints.EAST;
		gbc_lblMdia.insets = new Insets(0, 0, 5, 5);
		gbc_lblMdia.gridx = 2;
		gbc_lblMdia.gridy = 6;
		panel_2.add(lblMdia, gbc_lblMdia);
		
		educacaoMediaTF = new JTextField();
		educacaoMediaTF.setBackground(Color.WHITE);
		educacaoMediaTF.setEditable(false);
		educacaoMediaTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_educacaoMediaTF = new GridBagConstraints();
		gbc_educacaoMediaTF.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoMediaTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_educacaoMediaTF.gridx = 3;
		gbc_educacaoMediaTF.gridy = 6;
		panel_2.add(educacaoMediaTF, gbc_educacaoMediaTF);
		educacaoMediaTF.setColumns(10);
		
		JLabel lblFinalizada = new JLabel("FINALIZADA?");
		lblFinalizada.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblFinalizada = new GridBagConstraints();
		gbc_lblFinalizada.insets = new Insets(0, 0, 5, 5);
		gbc_lblFinalizada.gridx = 2;
		gbc_lblFinalizada.gridy = 7;
		panel_2.add(lblFinalizada, gbc_lblFinalizada);
		
		educacaoFinalizadaCheck = new JRadioButton("Marque caso o ciclo da matr\u00EDcula j\u00E1 est\u00E1 finalizado");
		educacaoFinalizadaCheck.setFont(new Font("Tahoma", Font.PLAIN, 12));
		educacaoFinalizadaCheck.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_educacaoFinalizadaCheck = new GridBagConstraints();
		gbc_educacaoFinalizadaCheck.anchor = GridBagConstraints.WEST;
		gbc_educacaoFinalizadaCheck.insets = new Insets(0, 0, 5, 0);
		gbc_educacaoFinalizadaCheck.gridx = 3;
		gbc_educacaoFinalizadaCheck.gridy = 7;
		panel_2.add(educacaoFinalizadaCheck, gbc_educacaoFinalizadaCheck);
		
		JLabel lblResultado = new JLabel("RESULTADO:");
		lblResultado.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblResultado = new GridBagConstraints();
		gbc_lblResultado.anchor = GridBagConstraints.EAST;
		gbc_lblResultado.insets = new Insets(0, 0, 0, 5);
		gbc_lblResultado.gridx = 2;
		gbc_lblResultado.gridy = 8;
		panel_2.add(lblResultado, gbc_lblResultado);
		
		educacaoResultadoCB = new JComboBox();
		educacaoResultadoCB.setModel(new DefaultComboBoxModel(new String[] {"CURSANDO", "APROVADO", "RECUPERACAO", "REPROVADO"}));
		GridBagConstraints gbc_educacaoResultadoCB = new GridBagConstraints();
		gbc_educacaoResultadoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_educacaoResultadoCB.gridx = 3;
		gbc_educacaoResultadoCB.gridy = 8;
		panel_2.add(educacaoResultadoCB, gbc_educacaoResultadoCB);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Lista", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Lista de Matr\u00EDculas");
		lblNewLabel_2_1.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnNewButton = new JButton("EXCLUIR");
		btnNewButton.setIcon(iconeExcluir);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					String[] opcao = { "Sim", "Não" };
					int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente excluir o registro?", "Atenção!",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);

					if (escolha == 0) {
						matricula = matriculas.get(table.getSelectedRow());
						new MatriculaDAO().deletar(matricula);
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
					matricula = matriculas.get(table.getSelectedRow());
					idTF.setText(matricula.getId().toString());
					
					educacaoAlunoCB.setSelectedIndex(acharIndiceAlunoCB(matricula.getAluno()));
					educacaoTurmaCB.setSelectedIndex(acharIndiceTurmaCB(matricula.getTurma()));
					educacaoDisciplinaCB.setSelectedIndex(acharIndiceDisciplinaCB(matricula.getDisciplina()));
					educacaoEfetivadaCheck.setSelected(matricula.getEfetivada());
					educacaoMediaTF.setText(matricula.getMedia().toString());
					educacaoFinalizadaCheck.setSelected(matricula.getFinalizada());
					educacaoResultadoCB.setSelectedIndex(matricula.getResultado().ordinal());
					
					tabbedPane.setSelectedIndex(0);
					JOptionPane.showMessageDialog(null, "Registro carregado para edição.");
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
					matricula = matriculas.get(table.getSelectedRow());

					DetalheView dialog = new DetalheView(matricula.toString(), "Detalhe da Matrícula");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um registro para editar.");
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
		panel_3.add(table, BorderLayout.CENTER);

		JScrollPane scrollTable = new JScrollPane(table);
		panel_3.add(scrollTable, BorderLayout.CENTER);

		carregarTabela();
		carregarCombos();
	}

	private void carregarTabela() {
		matriculas = new MatriculaDAO().listar();

		String[] colunas = new String[7];
		String[][] dados = new String[][] {};

		colunas[0] = "ALUNO";
		colunas[1] = "DISCIPLINA";
		colunas[2] = "TURMA";
		colunas[3] = "EFETIVADA";
		colunas[4] = "MÉDIA";
		colunas[5] = "FINALIZADA";
		colunas[6] = "RESULTADO";

		if (!matriculas.isEmpty()) {
			dados = new String[matriculas.size()][7];
			for (int i = 0; i < matriculas.size(); i++) {
				dados[i][0] = matriculas.get(i).getAluno().getNome();
				dados[i][1] = matriculas.get(i).getDisciplina().getNome();
				dados[i][2] = matriculas.get(i).getTurma().getNome();
				dados[i][3] = Formatador.booleanStr(matriculas.get(i).getEfetivada());
				dados[i][4] = matriculas.get(i).getMedia().toString();
				dados[i][5] = Formatador.booleanStr(matriculas.get(i).getFinalizada());
				dados[i][6] = matriculas.get(i).getResultado().toString();
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
		educacaoAlunoCB.setSelectedIndex(-1);
		educacaoTurmaCB.setSelectedIndex(-1);
		educacaoDisciplinaCB.setSelectedIndex(-1);
		educacaoResultadoCB.setSelectedIndex(-1);
		educacaoEfetivadaCheck.setSelected(false);
		educacaoFinalizadaCheck.setSelected(false);
		educacaoMediaTF.setText("");
	}
	
	@SuppressWarnings("unchecked")
	private void carregarCombos() {
		alunos = new UsuarioDAO().buscar(Perfil.ALUNO);
		turmas = new TurmaDAO().listar();
		disciplinas = new DisciplinaDAO().listar();
		educacaoAlunoCB.removeAllItems();
		educacaoTurmaCB.removeAllItems();
		educacaoDisciplinaCB.removeAllItems();
		
		for (Usuario u : alunos) {
			educacaoAlunoCB.addItem(u.getNome());
		}
		
		for (Disciplina d : disciplinas) {
			educacaoDisciplinaCB.addItem(d.getNome());
		}
		
		for (Turma t : turmas) {
			educacaoTurmaCB.addItem(t);
		} 
		
	}
	
	private int acharIndiceDisciplinaCB(Disciplina disciplina) {
		int indice = -1;
		for (int i = 0; i < disciplinas.size(); i++) {
			if(disciplinas.get(i).equals(disciplina)) {
				indice = i;
			}
		}
		return indice;
	}
	
	private int acharIndiceTurmaCB(Turma turma) {
		int indice = -1;
		for (int i = 0; i < turmas.size(); i++) {
			if(turmas.get(i).equals(turma)) {
				indice = i;
			}
		}
		return indice;
	}
	
	private int acharIndiceAlunoCB(Usuario aluno) {
		int indice = -1;
		for (int i = 0; i < alunos.size(); i++) {
			if(alunos.get(i).equals(aluno)) {
				indice = i;
			}
		}
		return indice;
	}

}
