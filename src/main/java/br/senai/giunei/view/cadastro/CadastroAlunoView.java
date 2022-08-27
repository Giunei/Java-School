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

import br.senai.giunei.dao.ContatoDAO;
import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.enumered.Genero;
import br.senai.giunei.enumered.Perfil;
import br.senai.giunei.enumered.TipoContato;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Criptografia;
import br.senai.giunei.view.DetalheView;

public class CadastroAlunoView extends JPanel {
	private JTextField idTF;
	private JTable table;
	private JTextField nomeTF;
	private JTextField emailTF;
	private JTextField senhaTf;

	private JComboBox generoCB;
	private JComboBox responsavelCB;
	private JComboBox contatoAlunoCB;
	private JComboBox contatoTipoContatoCB;

	JTabbedPane tabbedPane;

	private Usuario aluno = new Usuario();
	private List<Usuario> alunos = new ArrayList<Usuario>();
	
	private Contato contato;

	private List<Usuario> responsaveis = new ArrayList<Usuario>();
	private JTextField contatoIDTF;
	private JTextField contatoDetalheTF;

	public CadastroAlunoView(String titulo, final Usuario logado) {

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
				String email = emailTF.getText();
				String senha = senhaTf.getText();
				Genero genero = Genero.values()[generoCB.getSelectedIndex()];
				Usuario responsavel = responsaveis.get(responsavelCB.getSelectedIndex());

				aluno.setNome(nome);
				aluno.setEmail(email);
				aluno.setGenero(genero);
				aluno.setPerfil(Perfil.ALUNO);
				aluno.setResponsavel(responsavel);

				if (aluno.getId() != null) {
					// atualizar
					if (!senha.equals("") || !senha.isEmpty()) {
						aluno.setSenha(Criptografia.md5(senha));
					}
					new UsuarioDAO().atualizar(aluno);
					JOptionPane.showMessageDialog(null, "Aluno atualizado");
				} else {
					// salvar
					aluno.setSenha(Criptografia.md5(senha));
					new UsuarioDAO().salvar(aluno);
					JOptionPane.showMessageDialog(null, "Aluno salvo");
				}
				limparCampos();
				carregarTabela();
				carregarComboResponsavel();
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

		JLabel lblNewLabel_1_1_1 = new JLabel("EMAIL:");
		lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1.gridy = 3;
		panel_2.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);

		emailTF = new JTextField();
		emailTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		emailTF.setColumns(5);
		emailTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.insets = new Insets(0, 0, 5, 0);
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.gridx = 3;
		gbc_emailTF.gridy = 3;
		panel_2.add(emailTF, gbc_emailTF);

		JLabel lblNewLabel_1_1_1_3 = new JLabel("SENHA:");
		lblNewLabel_1_1_1_3.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_3.gridx = 2;
		gbc_lblNewLabel_1_1_1_3.gridy = 4;
		panel_2.add(lblNewLabel_1_1_1_3, gbc_lblNewLabel_1_1_1_3);

		senhaTf = new JTextField();
		senhaTf.setFont(new Font("Dialog", Font.PLAIN, 16));
		senhaTf.setColumns(5);
		senhaTf.setBorder(new EmptyBorder(2, 5, 2, 0));
		GridBagConstraints gbc_senhaTf = new GridBagConstraints();
		gbc_senhaTf.insets = new Insets(0, 0, 5, 0);
		gbc_senhaTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_senhaTf.gridx = 3;
		gbc_senhaTf.gridy = 4;
		panel_2.add(senhaTf, gbc_senhaTf);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("G\u00CANERO:");
		lblNewLabel_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1.gridy = 5;
		panel_2.add(lblNewLabel_1_1_1_1, gbc_lblNewLabel_1_1_1_1);

		generoCB = new JComboBox();
		generoCB.setModel(new DefaultComboBoxModel(new String[] { "Masculino", "Femenino", "N\u00E3o_definido" }));
		generoCB.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_generoCB = new GridBagConstraints();
		gbc_generoCB.insets = new Insets(0, 0, 5, 0);
		gbc_generoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_generoCB.gridx = 3;
		gbc_generoCB.gridy = 5;
		panel_2.add(generoCB, gbc_generoCB);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Respons\u00E1vel:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_1_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1.gridy = 6;
		panel_2.add(lblNewLabel_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1);

		responsavelCB = new JComboBox();
		responsavelCB.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_responsavelCB = new GridBagConstraints();
		gbc_responsavelCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_responsavelCB.gridx = 3;
		gbc_responsavelCB.gridy = 6;
		panel_2.add(responsavelCB, gbc_responsavelCB);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Lista", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);

		JLabel lblNewLabel_2_1 = new JLabel("Lista de Alunos");
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
						aluno = alunos.get(table.getSelectedRow());
						new UsuarioDAO().deletar(aluno);
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
					aluno = alunos.get(table.getSelectedRow());
					idTF.setText(aluno.getId().toString());
					nomeTF.setText(aluno.getNome().toString());
					emailTF.setText(aluno.getEmail().toString());
					generoCB.setSelectedIndex(aluno.getGenero().ordinal());
					responsavelCB.setSelectedIndex(acharIndiceResponsavel(aluno.getResponsavel()));
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
					aluno = alunos.get(table.getSelectedRow());

					DetalheView dialog = new DetalheView(aluno.toString(), "Detalhe do Usuário");
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
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Contatos", null, panel_5, null);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1_1 = new JPanel();
		panel_5.add(panel_1_1, BorderLayout.NORTH);
		
		JButton btnSalvar_1 = new JButton("SALVAR");
		btnSalvar_1.setIcon(iconeSalvar);
		btnSalvar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contato = new Contato();
				
				Usuario aluno = alunos.get(contatoAlunoCB.getSelectedIndex());
				TipoContato tipo = TipoContato.values()[contatoTipoContatoCB.getSelectedIndex()];
				String detalhe = contatoDetalheTF.getText();
				
				contato.setAutor(logado);
				contato.setCriacao(new Date());
				contato.setDetalhe(detalhe);
				contato.setTipo(tipo);
				contato.setUsuario(aluno);
				
				new ContatoDAO().salvar(contato);
				JOptionPane.showMessageDialog(null, "Contato adicionado para "+aluno.getNome());
			}
		});
		btnSalvar_1.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JButton btnLimpar_1 = new JButton("LIMPAR");
		btnLimpar_1.setIcon(iconeLimpar);
		btnLimpar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLimpar_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GroupLayout gl_panel_1_1 = new GroupLayout(panel_1_1);
		gl_panel_1_1.setHorizontalGroup(
			gl_panel_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1_1.createSequentialGroup()
					.addGap(140)
					.addComponent(btnSalvar_1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnLimpar_1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(1439))
		);
		gl_panel_1_1.setVerticalGroup(
			gl_panel_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1_1.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_1_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLimpar_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSalvar_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_panel_1_1.linkSize(SwingConstants.VERTICAL, new Component[] {btnSalvar_1, btnLimpar_1});
		gl_panel_1_1.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnSalvar_1, btnLimpar_1});
		panel_1_1.setLayout(gl_panel_1_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_5.add(panel_2_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2_1 = new GridBagLayout();
		gbl_panel_2_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2_1.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2_1.setLayout(gbl_panel_2_1);
		
		JLabel lblNewLabel_5 = new JLabel("  ");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 0;
		panel_2_1.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_3_1 = new JLabel("   ");
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 1;
		panel_2_1.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("  ");
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1.gridx = 1;
		gbc_lblNewLabel_4_1.gridy = 1;
		panel_2_1.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("ID:");
		lblNewLabel_1_2.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2.gridx = 2;
		gbc_lblNewLabel_1_2.gridy = 1;
		panel_2_1.add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);
		
		contatoIDTF = new JTextField();
		contatoIDTF.setEditable(false);
		contatoIDTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		contatoIDTF.setColumns(5);
		contatoIDTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		contatoIDTF.setBackground(Color.WHITE);
		GridBagConstraints gbc_contatoIDTF = new GridBagConstraints();
		gbc_contatoIDTF.anchor = GridBagConstraints.WEST;
		gbc_contatoIDTF.insets = new Insets(0, 0, 5, 0);
		gbc_contatoIDTF.gridx = 3;
		gbc_contatoIDTF.gridy = 1;
		panel_2_1.add(contatoIDTF, gbc_contatoIDTF);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("ALUNO:");
		lblNewLabel_1_2_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_2_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2_1.gridx = 2;
		gbc_lblNewLabel_1_2_1.gridy = 2;
		panel_2_1.add(lblNewLabel_1_2_1, gbc_lblNewLabel_1_2_1);
		
		contatoAlunoCB = new JComboBox();
		contatoAlunoCB.setFont(new Font("Dialog", Font.BOLD, 13));
		GridBagConstraints gbc_contatoAlunoCB = new GridBagConstraints();
		gbc_contatoAlunoCB.insets = new Insets(0, 0, 5, 0);
		gbc_contatoAlunoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_contatoAlunoCB.gridx = 3;
		gbc_contatoAlunoCB.gridy = 2;
		panel_2_1.add(contatoAlunoCB, gbc_contatoAlunoCB);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("TIPO CONTATO:");
		lblNewLabel_1_2_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_2_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2_1_1.gridx = 2;
		gbc_lblNewLabel_1_2_1_1.gridy = 3;
		panel_2_1.add(lblNewLabel_1_2_1_1, gbc_lblNewLabel_1_2_1_1);
		
		contatoTipoContatoCB = new JComboBox();
		contatoTipoContatoCB.setModel(new DefaultComboBoxModel(new String[] {"E-mail", "Telefone Fixo", "Telefone Comercial", "Celular", "M\u00EDdias Sociais"}));
		contatoTipoContatoCB.setFont(new Font("Dialog", Font.BOLD, 13));
		GridBagConstraints gbc_contatoTipoContatoCB = new GridBagConstraints();
		gbc_contatoTipoContatoCB.insets = new Insets(0, 0, 5, 0);
		gbc_contatoTipoContatoCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_contatoTipoContatoCB.gridx = 3;
		gbc_contatoTipoContatoCB.gridy = 3;
		panel_2_1.add(contatoTipoContatoCB, gbc_contatoTipoContatoCB);
		
		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("DETALHE");
		lblNewLabel_1_2_1_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_1_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_2_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_2_1_1_1.gridy = 4;
		panel_2_1.add(lblNewLabel_1_2_1_1_1, gbc_lblNewLabel_1_2_1_1_1);
		
		contatoDetalheTF = new JTextField();
		contatoDetalheTF.setFont(new Font("Dialog", Font.PLAIN, 16));
		contatoDetalheTF.setColumns(5);
		contatoDetalheTF.setBorder(new EmptyBorder(2, 5, 2, 0));
		contatoDetalheTF.setBackground(Color.WHITE);
		GridBagConstraints gbc_contatoDetalheTF = new GridBagConstraints();
		gbc_contatoDetalheTF.insets = new Insets(0, 0, 5, 0);
		gbc_contatoDetalheTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_contatoDetalheTF.gridx = 3;
		gbc_contatoDetalheTF.gridy = 4;
		panel_2_1.add(contatoDetalheTF, gbc_contatoDetalheTF);

		carregarTabela();
		carregarComboResponsavel();
	}

	private void carregarTabela() {
		alunos = new UsuarioDAO().buscar(Perfil.ALUNO);
		carregarComboAlunos();

		String[] colunas = new String[5];
		String[][] dados = new String[][] {};

		colunas[0] = "NOME";
		colunas[1] = "E-MAIL";
		colunas[2] = "GENERO";
		colunas[3] = "PERFIL";
		colunas[4] = "RESPONSÁVEL";

		if (!alunos.isEmpty()) {
			dados = new String[alunos.size()][5];
			for (int i = 0; i < alunos.size(); i++) {
				dados[i][0] = alunos.get(i).getNome();
				dados[i][1] = alunos.get(i).getEmail();
				dados[i][2] = alunos.get(i).getGenero().getStr();
				dados[i][3] = alunos.get(i).getPerfil().getStr();
				if (alunos.get(i).getResponsavel() != null)
					dados[i][4] = alunos.get(i).getResponsavel().getNome();
				else
					dados[i][4] = "Sem responsável";
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
		emailTF.setText("");
		senhaTf.setText("");
		generoCB.setSelectedIndex(-1);
		responsavelCB.setSelectedIndex(-1);
	}
	
	public void carregarComboAlunos() {
		contatoAlunoCB.removeAllItems();
		
		for (Usuario a : alunos) {
			contatoAlunoCB.addItem(a.getNome());
		}
		contatoAlunoCB.setSelectedIndex(-1);
	}

	public void carregarComboResponsavel() {
		responsavelCB.removeAllItems();
		responsaveis = new UsuarioDAO().buscar(Perfil.RESPONSAVEL);
		for (Usuario r : responsaveis) {
			responsavelCB.addItem(r.getNome() + " (" + r.getEmail() + ")");
		}
		responsavelCB.setSelectedIndex(-1);
	}

	public int acharIndiceResponsavel(Usuario responsavel) {
		int indice = -1;
		for (int i = 0; i < responsaveis.size(); i++) {
			if (responsavel.equals(responsaveis.get(i)))
				indice = i;
		}
		return indice;
	}
}
