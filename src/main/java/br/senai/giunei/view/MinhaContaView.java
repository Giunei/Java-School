package br.senai.giunei.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Criptografia;

import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;

public class MinhaContaView extends JPanel {
	private JTextField nomeTF;
	private JTextField generoTF;
	private JTextField emailTF;
	private JTextField perfilTF;
	private JPasswordField senhaAtualPF;
	private JPasswordField novaSenhaPF;
	private JPasswordField confirmarSenhaPF;

	public MinhaContaView(String titulo, final Usuario logado) {
		
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
		
		setBounds(0, 0, largura-5, altura-45);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel(titulo);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1910, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_2)
					.addGap(1844))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 50, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(7)
					.addComponent(lblNewLabel_2)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 70, 14);
		panel.add(lblNewLabel);
		
		JLabel lblGnero = new JLabel("G\u00EAnero:");
		lblGnero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGnero.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGnero.setBounds(10, 35, 70, 14);
		panel.add(lblGnero);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setBounds(10, 65, 70, 14);
		panel.add(lblEmail);
		
		JLabel lblPerfil = new JLabel("Perfil:");
		lblPerfil.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPerfil.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPerfil.setBounds(10, 93, 70, 14);
		panel.add(lblPerfil);
		
		nomeTF = new JTextField();
		nomeTF.setEditable(false);
		nomeTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nomeTF.setBounds(111, 7, 364, 23);
		panel.add(nomeTF);
		nomeTF.setColumns(10);
		
		generoTF = new JTextField();
		generoTF.setEditable(false);
		generoTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generoTF.setColumns(10);
		generoTF.setBounds(111, 34, 364, 23);
		panel.add(generoTF);
		
		emailTF = new JTextField();
		emailTF.setEditable(false);
		emailTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailTF.setColumns(10);
		emailTF.setBounds(111, 61, 364, 23);
		panel.add(emailTF);
		
		perfilTF = new JTextField();
		perfilTF.setEditable(false);
		perfilTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		perfilTF.setColumns(10);
		perfilTF.setBounds(111, 89, 364, 23);
		panel.add(perfilTF);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 132, 472, 14);
		panel.add(separator);
		
		JLabel lblAlterarASenha = new JLabel("Alterar a senha");
		lblAlterarASenha.setForeground(new Color(255, 69, 0));
		lblAlterarASenha.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAlterarASenha.setBounds(197, 157, 148, 14);
		panel.add(lblAlterarASenha);
		
		JLabel lblSenhaAtual = new JLabel("Senha atual:");
		lblSenhaAtual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenhaAtual.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSenhaAtual.setBounds(20, 203, 154, 14);
		panel.add(lblSenhaAtual);
		
		JLabel lblNovaSenha = new JLabel("Nova senha:");
		lblNovaSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNovaSenha.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNovaSenha.setBounds(20, 241, 154, 14);
		panel.add(lblNovaSenha);
		
		JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
		lblConfirmarSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmarSenha.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConfirmarSenha.setBounds(20, 280, 154, 14);
		panel.add(lblConfirmarSenha);
		
		senhaAtualPF = new JPasswordField();
		senhaAtualPF.setBounds(184, 202, 289, 23);
		panel.add(senhaAtualPF);
		
		novaSenhaPF = new JPasswordField();
		novaSenhaPF.setBounds(184, 240, 289, 23);
		panel.add(novaSenhaPF);
		
		confirmarSenhaPF = new JPasswordField();
		confirmarSenhaPF.setBounds(184, 279, 289, 23);
		panel.add(confirmarSenhaPF);
		
		JButton btnNewButton = new JButton("Alterar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO alterar senha
				String senhaAtual = String.valueOf(senhaAtualPF.getPassword());
				String novaSenha = String.valueOf(novaSenhaPF.getPassword());
				String confirmarSenha = String.valueOf(confirmarSenhaPF.getPassword());
				
				if(Criptografia.md5(senhaAtual).equals(logado.getSenha())) {
					if (novaSenha.equals(confirmarSenha)) {
						logado.setSenha(Criptografia.md5(novaSenha));
						new UsuarioDAO().atualizar(logado);
						JOptionPane.showMessageDialog(null, "Senha alterada!");
					} else {
						JOptionPane.showMessageDialog(null, "Confirmação da senha não correspondeu");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Senha atual está incorreta");
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNewButton.setBounds(354, 323, 114, 28);
		panel.add(btnNewButton);
		
		carregarInfos(logado);
	}
	
	public void carregarInfos(Usuario logado) {
		nomeTF.setText(logado.getNome());
		emailTF.setText(logado.getEmail());
		generoTF.setText(logado.getGenero().getStr());
		perfilTF.setText(logado.getPerfil().getStr());
	}
	
}
