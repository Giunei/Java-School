package br.senai.giunei.view.cadastro;

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

import br.senai.giunei.dao.DisciplinaDAO;
import br.senai.giunei.dao.TurmaDAO;
import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.enumered.Perfil;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Turma;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.view.DetalheView;

public class CadastroDisciplinaView extends JPanel {
	private JTextField idTF;
	private JTable table;
	private JTextField nomeTF;
	
	private JComboBox professorCB;
	private JComboBox turmaCB;

	JTabbedPane tabbedPane;

	private Disciplina disciplina = new Disciplina();
	private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	private List<Usuario> professores = new ArrayList<Usuario>();
	private List<Turma> turmas = new ArrayList<Turma>();

	public CadastroDisciplinaView(String titulo, final Usuario logado) {

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
				// TODO SALVAR USUARIO
				String nome = nomeTF.getText();
				Usuario professor = professores.get(professorCB.getSelectedIndex());
				Turma turma = turmas.get(turmaCB.getSelectedIndex());

				disciplina.setNome(nome);
				professor.setPerfil(Perfil.PROFESSOR);
				disciplina.setProfessor(professor);
				disciplina.setTurma(turma);

				if (disciplina.getId() != null) {
					// atualizar
					disciplina.setEditor(logado);
					disciplina.setEdicao(new Date());
					new DisciplinaDAO().atualizar(disciplina);
					JOptionPane.showMessageDialog(null, "Disciplina atualizada");
				} else {
					// salvar
					disciplina.setAutor(logado);
					disciplina.setCriacao(new Date());
					new DisciplinaDAO().salvar(disciplina);
					JOptionPane.showMessageDialog(null, "Disciplina salva");
				}
				limparCampos();
				carregarTabela();
				carregarComboProfessor();
				carregarComboTurma();
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

		JLabel lblNewLabel_1_1 = new JLabel("NOME:");
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 2;
		gbc_lblNewLabel_1_1.gridy = 2;
		panel_2.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		nomeTF = new JTextField();
		nomeTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		nomeTF.setColumns(5);
		nomeTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_nomeTF = new GridBagConstraints();
		gbc_nomeTF.insets = new Insets(0, 0, 5, 0);
		gbc_nomeTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nomeTF.gridx = 3;
		gbc_nomeTF.gridy = 2;
		panel_2.add(nomeTF, gbc_nomeTF);
		
				JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Professor:");
				lblNewLabel_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_lblNewLabel_1_1_1_1_1 = new GridBagConstraints();
				gbc_lblNewLabel_1_1_1_1_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_1_1_1_1_1.gridx = 2;
				gbc_lblNewLabel_1_1_1_1_1.gridy = 3;
				panel_2.add(lblNewLabel_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1);
		
				professorCB = new JComboBox();
				professorCB.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_professorCB = new GridBagConstraints();
				gbc_professorCB.insets = new Insets(0, 0, 5, 0);
				gbc_professorCB.fill = GridBagConstraints.HORIZONTAL;
				gbc_professorCB.gridx = 3;
				gbc_professorCB.gridy = 3;
				panel_2.add(professorCB, gbc_professorCB);
				
				JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Turma:");
				lblNewLabel_1_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_lblNewLabel_1_1_1_1_1_1 = new GridBagConstraints();
				gbc_lblNewLabel_1_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_1_1_1_1_1_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1_1_1_1_1_1.gridx = 2;
				gbc_lblNewLabel_1_1_1_1_1_1.gridy = 4;
				panel_2.add(lblNewLabel_1_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1_1);
				
				turmaCB = new JComboBox();
				turmaCB.setSelectedIndex(-1);
				turmaCB.setFont(new Font("Dialog", Font.BOLD, 16));
				GridBagConstraints gbc_turmaCB = new GridBagConstraints();
				gbc_turmaCB.insets = new Insets(0, 0, 5, 0);
				gbc_turmaCB.fill = GridBagConstraints.HORIZONTAL;
				gbc_turmaCB.gridx = 3;
				gbc_turmaCB.gridy = 4;
				panel_2.add(turmaCB, gbc_turmaCB);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Lista", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Lista de Disciplinas");
		lblNewLabel_2_1.setFont(new Font("Dialog", Font.BOLD, 20));

		JButton btnNewButton = new JButton("EXCLUIR");
		btnNewButton.setIcon(iconeExcluir);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					String[] opcao = { "SIM", "N?O" };
					int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente excluir o registro?", "Aten??o!",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);

					if (escolha == 0) {
						disciplina = disciplinas.get(table.getSelectedRow());
						new DisciplinaDAO().deletar(disciplina);
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
					disciplina = disciplinas.get(table.getSelectedRow());
					idTF.setText(disciplina.getId().toString());
					nomeTF.setText(disciplina.getNome().toString());
					professorCB.setSelectedIndex(acharIndiceProfessor(disciplina.getProfessor()));
					turmaCB.setSelectedIndex(acharIndiceTurma(disciplina.getTurma()));
					JOptionPane.showMessageDialog(null, "Registro carregado para edi??o.");
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
					disciplina = disciplinas.get(table.getSelectedRow());

					DetalheView dialog = new DetalheView(disciplina.toString(), "Detalhe da Disciplina");
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
		carregarComboProfessor();
		carregarComboTurma();
	}

	private void carregarTabela() {
		disciplinas = new DisciplinaDAO().listar();

		String[] colunas = new String[3];
		String[][] dados = new String[][] {};

		colunas[0] = "NOME";
		colunas[1] = "PROFESSOR";
		colunas[2] = "TURMA";

		if (!disciplinas.isEmpty()) {
			dados = new String[disciplinas.size()][3];
			for (int i = 0; i < disciplinas.size(); i++) {
				dados[i][0] = disciplinas.get(i).getNome();
				if (disciplinas.get(i).getProfessor() != null)
					dados[i][1] = disciplinas.get(i).getProfessor().getNome();
				else
					dados[i][1] = "Sem professor";
				if (disciplinas.get(i).getTurma() != null)
					dados[i][2] = disciplinas.get(i).getTurma().getNome();
				else
					dados[i][2] = "Sem turma";
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
		nomeTF.setText("");
		professorCB.setSelectedIndex(-1);
		turmaCB.setSelectedIndex(-1);
	}

	public void carregarComboProfessor() {
		professorCB.removeAllItems();
		professores = new UsuarioDAO().buscar(Perfil.PROFESSOR);
		for (Usuario r : professores) {
			professorCB.addItem(r.getNome() + " (" + r.getEmail() + ")");
		}
		professorCB.setSelectedIndex(-1);
	}
	
	public void carregarComboTurma() {
		turmaCB.removeAllItems();
		turmas = new TurmaDAO().listar();
		for (Turma t : turmas) {
			turmaCB.addItem(t.getNome() + " (" + t.getNivel() + ")");
		}
		turmaCB.setSelectedIndex(-1);
	}

	public int acharIndiceProfessor(Usuario professor) {
		int indice = -1;
		for (int i = 0; i < professores.size(); i++) {
			if (professor.equals(professores.get(i)))
				indice = i;
		}
		return indice;
	}
	
	public int acharIndiceTurma(Turma turma) {
		int indice = -1;
		for (int i = 0; i < turmas.size(); i++) {
			if (turma.equals(turmas.get(i)))
				indice = i;
		}
		return indice;
	}

}
