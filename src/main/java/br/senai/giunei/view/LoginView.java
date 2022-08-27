package br.senai.giunei.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.senai.giunei.dao.UsuarioDAO;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.Criptografia;

public class LoginView {

	private JFrame frmJavasenaischool;
	private JTextField emailTF;
	private JPasswordField senhaPF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
					window.frmJavasenaischool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ImageIcon icone = new ImageIcon("img/escolaPack.png");
		
		frmJavasenaischool = new JFrame();
		frmJavasenaischool.getContentPane().setBackground(new Color(255, 204, 153));
		frmJavasenaischool.setTitle("JavaSenaiSchool");
		frmJavasenaischool.setBounds(100, 100, 700, 337);
		frmJavasenaischool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJavasenaischool.getContentPane().setLayout(null);
		
		JLabel lblLogo = new JLabel("New label");
		lblLogo.setBounds(10, 11, 246, 274);
		lblLogo.setIcon(icone);
		frmJavasenaischool.getContentPane().add(lblLogo);
		
		JLabel lblNewLabel = new JLabel("Acesso ao Sistema");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(370, 11, 228, 32);
		frmJavasenaischool.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(300, 85, 46, 14);
		frmJavasenaischool.getContentPane().add(lblNewLabel_1);
		
		emailTF = new JTextField();
		emailTF.setBackground(new Color(255, 255, 204));
		emailTF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailTF.setBounds(300, 110, 374, 26);
		frmJavasenaischool.getContentPane().add(emailTF);
		emailTF.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Senha:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(300, 151, 46, 14);
		frmJavasenaischool.getContentPane().add(lblNewLabel_1_1);
		
		senhaPF = new JPasswordField();
		senhaPF.setBackground(new Color(255, 255, 204));
		senhaPF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senhaPF.setBounds(300, 174, 374, 26);
		frmJavasenaischool.getContentPane().add(senhaPF);
		
		JButton btnNewButton = new JButton("REGISTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				TODO Registrar
			}
		});
		btnNewButton.setBackground(new Color(255, 204, 204));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(340, 236, 116, 32);
		btnNewButton.setFocusable(false);
		frmJavasenaischool.getContentPane().add(btnNewButton);
		
		JButton btnAcessar = new JButton("ACESSAR");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ACESSAR
				String email = emailTF.getText();
				String senhaNaoCriptografada = String.valueOf(senhaPF.getPassword());
				String senhaCriptografada = Criptografia.md5(senhaNaoCriptografada);
				
				Usuario usuarioBanco = new UsuarioDAO().buscar(email);
				
				if (usuarioBanco.getId() != null) {
					if (usuarioBanco.getSenha().equals(senhaCriptografada)) {
						JavaSenaiSchool window = new JavaSenaiSchool(usuarioBanco);
						window.getFrmJavaSenaiSchool().setVisible(true);
						frmJavasenaischool.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Senha inválida");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente!\n"+email);
				}
			}
		});
		btnAcessar.setBackground(new Color(255, 204, 204));
		btnAcessar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAcessar.setBounds(497, 236, 116, 32);
		frmJavasenaischool.getContentPane().add(btnAcessar);
	}
	public JFrame getFrmJavasenaischool() {
		return frmJavasenaischool;
	}

}
