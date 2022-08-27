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

import br.senai.giunei.dao.AvaliacaoDAO;
import br.senai.giunei.dao.DisciplinaDAO;
import br.senai.giunei.dao.MatriculaDAO;
import br.senai.giunei.dao.OcorrenciaDAO;
import br.senai.giunei.enumered.ResultadoMatricula;
import br.senai.giunei.enumered.TipoOcorrencia;
import br.senai.giunei.model.Avaliacao;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Ocorrencia;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Formatador;
import br.senai.giunei.view.DetalheView;
import java.awt.FlowLayout;

public class EducacaoDiarioView extends JPanel {
	private JTable table;

	JTabbedPane tabbedPane;

	private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
	
	Double mediaCalculada = 0D;
	
	private Disciplina disciplina = new Disciplina();
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	private Contato contato;
	
	JComboBox disciplinaCB;

	private List<Matricula> matriculas = new ArrayList<Matricula>();
	private Matricula matricula;
	
	List<Avaliacao>	avaliacoes = new ArrayList<>();
	
	private JTable tableMatriculas;
	private JTextField nomeTF;
	private JTextField emailTF;
	private JTextField generoTF;
	private JTextField responsavelTF;
	private JTable tableAvaliacoes;
	private JTable tableOcorrencias;
	private JTextField mediaTF;

	public EducacaoDiarioView(String titulo, final Usuario logado) {

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
		tabbedPane.addTab("Di\u00E1rio", null, painelCadastro, null);
		painelCadastro.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		painelCadastro.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel_2 = new JLabel(titulo);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblNewLabel = new JLabel("Disciplina:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		disciplinaCB = new JComboBox();
		
		JButton btnNewButton_1 = new JButton("BUSCAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disciplina = disciplinas.get(disciplinaCB.getSelectedIndex());
				matriculas = new MatriculaDAO().listar(disciplina, false);
				carregarTabelaMatriculas();
			}
		});
		
		JButton btnFinalizarDiarios = new JButton("FINALIZAR DIARIOS");
		btnFinalizarDiarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO FINALIZAR DIARIOS
				String[] opcao = { "SIM", "NÃO" };
				int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente fechar todos os diários?", "Atenção!",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);

				if (escolha == 0) {
					for (Matricula m : matriculas) {
						m.setFinalizada(true);
						if(m.getMedia() >= 7) {
							m.setResultado(ResultadoMatricula.APROVADO);
						}else {
							m.setResultado(ResultadoMatricula.REPROVADO);
						}
						
						new MatriculaDAO().atualizar(m);
					}
					JOptionPane.showMessageDialog(null, "Foram finalizados "+matriculas.size()+" diários");
					matriculas = new MatriculaDAO().listar(disciplina, false);
					carregarTabelaMatriculas();
				}
			}
		});
		btnFinalizarDiarios.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(disciplinaCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(205)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnFinalizarDiarios)
					.addGap(1191))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel)
						.addComponent(disciplinaCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFinalizarDiarios, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel = new JPanel();
		painelCadastro.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollMatriculas = new JScrollPane(panel);
		painelCadastro.add(scrollMatriculas, BorderLayout.CENTER);
		
		tableMatriculas = new JTable(){
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
		tableMatriculas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel.add(tableMatriculas, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		painelCadastro.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton_2 = new JButton("Carregar Matricula");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO CARREGAR MATRICULA
				matricula = matriculas.get(tableMatriculas.getSelectedRow());
				nomeTF.setText(matricula.getAluno().getNome());
				emailTF.setText(matricula.getAluno().getEmail());
				generoTF.setText(matricula.getAluno().getGenero().toString());
				responsavelTF.setText(matricula.getAluno().getResponsavel().getNome());
				
				tabbedPane.setSelectedIndex(1);
				carregarTabelaAvaliacoes(matricula);
				carregarTabelaOcorrencias(matricula);
				mediaTF.setText("");
			}
		});
		panel_2.add(btnNewButton_2);

		JPanel painelLista = new JPanel();
		tabbedPane.addTab("Lista", null, painelLista, null);
		painelLista.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		painelLista.add(panel_4, BorderLayout.NORTH);

		JButton btnNewButton = new JButton("MÉDIA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaCalculada = 0D;
				Double totalNotas = 0D;
				Integer totalPesos = 0;
				
				for (Avaliacao a : avaliacoes) {
					totalNotas += a.getNota()*a.getPeso();
					totalPesos += a.getPeso();
				}
				mediaCalculada = totalNotas/totalPesos;
				mediaTF.setText(mediaCalculada.toString());
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnEditar = new JButton("FECHAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO FECHAR
				String[] opcao = { "SIM", "NÃO" };
				int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente fechar a média do aluno "+matricula.getAluno().getNome(), "Atenção!",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);

				if (escolha == 0) {
					matricula.setMedia(mediaCalculada);
					verificarSituacaoMedia();
					new MatriculaDAO().atualizar(matricula);
					carregarTabelaMatriculas();
				}
			}
		});
		btnEditar.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(83)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(1384))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnEditar))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_4.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnNewButton, btnEditar});
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_3 = new JPanel();
		painelLista.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPaneMatricula = new JTabbedPane(JTabbedPane.TOP);
		panel_3.add(tabbedPaneMatricula, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		tabbedPaneMatricula.addTab("Informa\u00E7\u00F5es", null, panel_5, null);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel_5.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		nomeTF = new JTextField();
		nomeTF.setEditable(false);
		GridBagConstraints gbc_nomeTF = new GridBagConstraints();
		gbc_nomeTF.insets = new Insets(0, 0, 5, 0);
		gbc_nomeTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nomeTF.gridx = 2;
		gbc_nomeTF.gridy = 1;
		panel_5.add(nomeTF, gbc_nomeTF);
		nomeTF.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 2;
		panel_5.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		emailTF = new JTextField();
		emailTF.setEditable(false);
		emailTF.setColumns(10);
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.insets = new Insets(0, 0, 5, 0);
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.gridx = 2;
		gbc_emailTF.gridy = 2;
		panel_5.add(emailTF, gbc_emailTF);
		
		JLabel lblNewLabel_1_2 = new JLabel("G\u00EAnero:");
		lblNewLabel_1_2.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2.gridx = 1;
		gbc_lblNewLabel_1_2.gridy = 3;
		panel_5.add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);
		
		generoTF = new JTextField();
		generoTF.setEditable(false);
		generoTF.setColumns(10);
		GridBagConstraints gbc_generoTF = new GridBagConstraints();
		gbc_generoTF.insets = new Insets(0, 0, 5, 0);
		gbc_generoTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_generoTF.gridx = 2;
		gbc_generoTF.gridy = 3;
		panel_5.add(generoTF, gbc_generoTF);
		
		JLabel lblNewLabel_1_3 = new JLabel("Respons\u00E1vel:");
		lblNewLabel_1_3.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_1_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_3.gridx = 1;
		gbc_lblNewLabel_1_3.gridy = 4;
		panel_5.add(lblNewLabel_1_3, gbc_lblNewLabel_1_3);
		
		responsavelTF = new JTextField();
		responsavelTF.setEditable(false);
		responsavelTF.setColumns(10);
		GridBagConstraints gbc_responsavelTF = new GridBagConstraints();
		gbc_responsavelTF.insets = new Insets(0, 0, 5, 0);
		gbc_responsavelTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_responsavelTF.gridx = 2;
		gbc_responsavelTF.gridy = 4;
		panel_5.add(responsavelTF, gbc_responsavelTF);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("M\u00E9dia:");
		lblNewLabel_1_3_1.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_3_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_3_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_3_1.gridx = 1;
		gbc_lblNewLabel_1_3_1.gridy = 5;
		panel_5.add(lblNewLabel_1_3_1, gbc_lblNewLabel_1_3_1);
		
		mediaTF = new JTextField();
		mediaTF.setEditable(false);
		mediaTF.setColumns(10);
		GridBagConstraints gbc_mediaTF = new GridBagConstraints();
		gbc_mediaTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_mediaTF.gridx = 2;
		gbc_mediaTF.gridy = 5;
		panel_5.add(mediaTF, gbc_mediaTF);
		
		JPanel panel_6 = new JPanel();
		tabbedPaneMatricula.addTab("Avalia\u00E7\u00F5es", null, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		tableAvaliacoes = new JTable(){
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
		tableAvaliacoes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel_6.add(tableAvaliacoes, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(tableAvaliacoes);
		panel_6.add(scroll, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		tabbedPaneMatricula.addTab("Ocorr\u00EAncias", null, panel_7, null);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		tableOcorrencias = new JTable(){
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
		tableOcorrencias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel_7.add(tableOcorrencias, BorderLayout.CENTER);
		
		JScrollPane scrollOcorrencia = new JScrollPane(tableOcorrencias);
		panel_7.add(scrollOcorrencia, BorderLayout.CENTER);

		carregarComboDisciplinas(logado);
		limparCampos();
	}
	
	private void carregarTabelaOcorrencias(Matricula matriculaSelecionada) {
		ocorrencias = new OcorrenciaDAO().listar(matriculaSelecionada);
		
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

		tableOcorrencias.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		tableOcorrencias.setIntercellSpacing(new Dimension(width, height));
		tableOcorrencias.setFont(new Font("Dialog", Font.PLAIN, 16));
		tableOcorrencias.setRowHeight(28);
	}
	
	private void carregarTabelaAvaliacoes(Matricula matriculaSelecionada) {
		avaliacoes = new AvaliacaoDAO().listar(matriculaSelecionada);
		
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

		tableAvaliacoes.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		tableAvaliacoes.setIntercellSpacing(new Dimension(width, height));
		tableAvaliacoes.setFont(new Font("Dialog", Font.PLAIN, 16));
		tableAvaliacoes.setRowHeight(28);
	}

	private void carregarTabelaMatriculas() {
		String[] colunas = new String[7];
		String[][] dados = new String[][] {};

		colunas[0] = "ALUNO";
		colunas[1] = "DISCIPLINA";
		colunas[2] = "TURMA";
		colunas[3] = "EFETIVADA";
		colunas[4] = "MEDIA";
		colunas[5] = "SITUACAO";
		colunas[6] = "FINALIZADA";

		if (!disciplinas.isEmpty()) {
			dados = new String[matriculas.size()][7];
			for (int i = 0; i < matriculas.size(); i++) {
				Matricula m = matriculas.get(i);
				dados[i][0] = m.getAluno().getNome();
				dados[i][1] = m.getDisciplina().getNome();
				dados[i][2] = m.getTurma().getNome();
				dados[i][3] = Formatador.booleanStr(m.getEfetivada());
				dados[i][4] = m.getMedia().toString();
				dados[i][5] = m.getResultado().toString();
				dados[i][6] = Formatador.booleanStr(m.getEfetivada());
			}
		}

		tableMatriculas.setModel(new DefaultTableModel(dados, colunas));
		int width = 10;
		int height = 2;
		tableMatriculas.setIntercellSpacing(new Dimension(width, height));
		tableMatriculas.setFont(new Font("Dialog", Font.PLAIN, 16));
		tableMatriculas.setRowHeight(28);
	}

	private void limparCampos() {
		Date hoje = new Date();
	}
	
	public void carregarComboDisciplinas(Usuario professor) {
		disciplinas = new DisciplinaDAO().listar(professor);
		StringBuilder sb = new StringBuilder();
		
		for (Disciplina d : disciplinas) {
			sb = new StringBuilder();
			sb.append(d.getNome() + " - ");
			sb.append(d.getTurma().getNome() + " - ");
			sb.append(d.getTurma().getSala());
			disciplinaCB.addItem(sb.toString());
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
	
	public void verificarSituacaoMedia() {
		if(matricula.getMedia() >= 7) {
			matricula.setResultado(ResultadoMatricula.APROVADO);
		}else {
			matricula.setResultado(ResultadoMatricula.RECUPERACAO);
		}
	}
}
