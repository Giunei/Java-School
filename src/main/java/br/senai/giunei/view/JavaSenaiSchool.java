package br.senai.giunei.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;

import br.senai.giunei.model.Usuario;
import br.senai.giunei.view.cadastro.CadastroAlunoView;
import br.senai.giunei.view.cadastro.CadastroDisciplinaView;
import br.senai.giunei.view.cadastro.CadastroProfessorView;
import br.senai.giunei.view.cadastro.CadastroResponsavelView;
import br.senai.giunei.view.cadastro.CadastroSecretariaView;
import br.senai.giunei.view.cadastro.CadastroTurmaView;
import br.senai.giunei.view.cadastro.CadastroUsuarioView;
import br.senai.giunei.view.educacao.EducacaoAvaliacaoView;
import br.senai.giunei.view.educacao.EducacaoDiarioView;
import br.senai.giunei.view.educacao.EducacaoJustificativaView;
import br.senai.giunei.view.educacao.EducacaoMatriculaView;
import br.senai.giunei.view.educacao.EducacaoOcorrenciaView;

public class JavaSenaiSchool {

	private JFrame frmJavaSenaiSchool;
	private JPanel centro;
	JLabel lblUsuarioLogado;
	
	//Variaveis do Menu
	JMenuBar menuBar;
	
	//Variáveis do menu: Configurações
	JMenu menuConfiguracoes;
	JMenuItem menuConfiguracoesMinhaConta;
	JMenuItem menuConfiguracoesSistema;
	
	//Variaveis do menu: Cadastros
	JMenu menuCadastros;
	JMenuItem menuCadastrosUsuarios;
	JMenuItem menuCadastrosSecretaria;
	JMenuItem menuCadastrosTurmas;
	JMenuItem menuCadastrosDisciplinas;
	JMenuItem menuCadastrosAlunos;
	JMenuItem menuCadastrosProfessores;
	
	//Variáveis do menu: Educação
	JMenu menuEducacao;
	JMenuItem menuEducacaoMatricula;
	JMenuItem menuEducacaoOcorrencia;
	JMenuItem menuEducacaoDiario;
	JMenuItem menuEducacaoAvaliacao;
	JMenuItem menuEducacaoJustificativa;
	
	//Variáveis do menu: Consultas
	JMenu menuConsultas;
	JMenuItem menuConsultasOcorrencias;
	JMenuItem menuConsultasDiarios;
	JMenuItem menuConsultasAvaliacoes;
	JMenuItem menuConsultasJustificativas;
	
	//Variáveis do menu: Sobre
	JMenu menuSobre;
	JMenuItem menuSobreEsseCurso;
	JMenuItem menuSobreProfessor;
	JMenuItem menuSobreAluno;
	private JButton btnNewButton;
	private JMenuItem menuCadastrosResponsaveis;


	/**
	 * Create the application.
	 */
	public JavaSenaiSchool(Usuario logado) {
		initialize(logado);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Usuario logado) {
		
		ImageIcon fundo = new ImageIcon("img/escola.png");
		
		frmJavaSenaiSchool = new JFrame();
		frmJavaSenaiSchool.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		frmJavaSenaiSchool.setTitle("Java Senai School");
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		

		int altura = screensize.height;
		int largura = screensize.width;
		
		frmJavaSenaiSchool.setBounds(0, 5, largura, altura-30);
		frmJavaSenaiSchool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJavaSenaiSchool.getContentPane().setLayout(new BorderLayout(0, 0));
		
		centro = new JPanel();
		frmJavaSenaiSchool.getContentPane().add(centro, BorderLayout.CENTER);
		centro.setLayout(new BorderLayout(0, 0));
		
		final JLabel labelFundo = new JLabel();
		labelFundo.setIcon(fundo);
		labelFundo.setHorizontalAlignment(SwingConstants.CENTER);
		centro.add(labelFundo, BorderLayout.CENTER);
		
		JPanel menuPanel = new JPanel();
		frmJavaSenaiSchool.getContentPane().add(menuPanel, BorderLayout.NORTH);
		menuPanel.setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		menuPanel.add(menuBar);
		
		menuConfiguracoes = new JMenu("Configura\u00E7\u00F5es");
		menuConfiguracoes.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(menuConfiguracoes);
		
		menuConfiguracoesMinhaConta = new JMenuItem("Minha conta");
		menuConfiguracoesMinhaConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Minha Conta
				MinhaContaView view = new MinhaContaView("Configurações da minha conta",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuConfiguracoesMinhaConta.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConfiguracoes.add(menuConfiguracoesMinhaConta);
		
		menuConfiguracoesSistema = new JMenuItem("Sistema");
		menuConfiguracoesSistema.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConfiguracoes.add(menuConfiguracoesSistema);
		
		menuCadastros = new JMenu("Cadastros");
		menuCadastros.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(menuCadastros);
		
		menuCadastrosUsuarios = new JMenuItem("Usuários");
		menuCadastrosUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO CADASTRO USUARIOS
				CadastroUsuarioView view = new CadastroUsuarioView("Cadastro de Usuários",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosUsuarios.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosUsuarios);
		
		menuCadastrosSecretaria = new JMenuItem("Secretaria");
		menuCadastrosSecretaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Secretaria
				CadastroSecretariaView view = new CadastroSecretariaView("Cadastro de Secretaria",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosSecretaria.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosSecretaria);
		
		menuCadastrosTurmas = new JMenuItem("Turmas");
		menuCadastrosTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO cadastro Turmas
				CadastroTurmaView view = new CadastroTurmaView("Cadastro de Turmas",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosTurmas.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosTurmas);
		
		menuCadastrosDisciplinas = new JMenuItem("Disciplina");
		menuCadastrosDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO cadastro Disciplina
				CadastroDisciplinaView view = new CadastroDisciplinaView("Cadastro de Disciplinas",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosDisciplinas.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosDisciplinas);
		
		menuCadastrosAlunos = new JMenuItem("Alunos");
		menuCadastrosAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Cadastrar alunos
				CadastroAlunoView view = new CadastroAlunoView("Cadastro de Alunos",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosAlunos.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosAlunos);
		
		menuCadastrosProfessores = new JMenuItem("Professores");
		menuCadastrosProfessores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Cadastro professores
				CadastroProfessorView view = new CadastroProfessorView("Cadastro de Professores",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
			
		});
		menuCadastrosProfessores.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosProfessores);
		
		menuCadastrosResponsaveis = new JMenuItem("Responsaveis");
		menuCadastrosResponsaveis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Cadastrar responsaveis
				CadastroResponsavelView view = new CadastroResponsavelView("Cadastro de Responsáveis",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuCadastrosResponsaveis.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuCadastros.add(menuCadastrosResponsaveis);
		
		menuEducacao = new JMenu("Educação");
		menuEducacao.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(menuEducacao);
		
		menuEducacaoMatricula = new JMenuItem("Matrícula");
		menuEducacaoMatricula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EducacaoMatriculaView view = new EducacaoMatriculaView("Matrículas",logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuEducacaoMatricula.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuEducacao.add(menuEducacaoMatricula);
		
		menuEducacaoOcorrencia = new JMenuItem("Ocorrência");
		menuEducacaoOcorrencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Ocorrencia
				EducacaoOcorrenciaView view = new EducacaoOcorrenciaView("Cadastro de Ocorrências", logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuEducacaoOcorrencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuEducacao.add(menuEducacaoOcorrencia);
		
		menuEducacaoDiario = new JMenuItem("Diário");
		menuEducacaoDiario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EducacaoDiarioView view = new EducacaoDiarioView("Diários", logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuEducacaoDiario.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuEducacao.add(menuEducacaoDiario);
		
		menuEducacaoAvaliacao = new JMenuItem("Avaliação");
		menuEducacaoAvaliacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Avaliacao
				EducacaoAvaliacaoView view = new EducacaoAvaliacaoView("Avaliações", logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuEducacaoAvaliacao.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuEducacao.add(menuEducacaoAvaliacao);
		
		menuEducacaoJustificativa = new JMenuItem("Justificativa");
		menuEducacaoJustificativa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Justificativa
				EducacaoJustificativaView view = new EducacaoJustificativaView("Justificativas", logado);
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuEducacaoJustificativa.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuEducacao.add(menuEducacaoJustificativa);
		
		menuSobre = new JMenu("Sobre");
		menuSobre.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(menuSobre);
		
		menuSobreEsseCurso = new JMenuItem("Esse curso");
		menuSobreEsseCurso.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuSobreEsseCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EsseCursoView view = new EsseCursoView();
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuSobre.add(menuSobreEsseCurso);
		
		menuSobreProfessor = new JMenuItem("Professor");
		menuSobreProfessor.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuSobreProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SobreProfessorView view = new SobreProfessorView();
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuSobre.add(menuSobreProfessor);
		
		menuSobreAluno = new JMenuItem("Aluno");
		menuSobreAluno.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuSobreAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SobreAlunoView view = new SobreAlunoView();
				view.setVisible(true);
				centro.removeAll();
				centro.add(view, BorderLayout.CENTER);
				labelFundo.setIcon(null);
				view.revalidate();
			}
		});
		menuSobre.add(menuSobreAluno);
		
		menuConsultas = new JMenu("Consultas");
		menuConsultas.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(menuConsultas);
		
		menuConsultasOcorrencias = new JMenuItem("Ocorr\u00EAncias");
		menuConsultasOcorrencias.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConsultas.add(menuConsultasOcorrencias);
		
		menuConsultasDiarios = new JMenuItem("Di\u00E1rios");
		menuConsultasDiarios.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConsultas.add(menuConsultasDiarios);
		
		menuConsultasAvaliacoes = new JMenuItem("Avalia\u00E7\u00F5es");
		menuConsultasAvaliacoes.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConsultas.add(menuConsultasAvaliacoes);
		
		menuConsultasJustificativas = new JMenuItem("Justificativas");
		menuConsultasJustificativas.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuConsultas.add(menuConsultasJustificativas);
		
		lblUsuarioLogado = new JLabel("");
		lblUsuarioLogado.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUsuarioLogado.setForeground(new Color(255, 153, 51));
		menuBar.add(lblUsuarioLogado);
		
		lblUsuarioLogado.setText(logado.getNome()+" - "+logado.getEmail()+" ("+logado.getPerfil().getStr()+")");
		
		btnNewButton = new JButton("Logoff");
		btnNewButton.setBackground(new Color(255, 248, 220));
		btnNewButton.setBorder(new CompoundBorder(null, UIManager.getBorder("EditorPane.border")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] options = {"Sim","Não"};
				int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente fazer logoff?", "Sair", 
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				
				if(escolha == 0) {
					LoginView login = new LoginView();
					login.getFrmJavasenaischool().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					login.getFrmJavasenaischool().setVisible(true);
					frmJavaSenaiSchool.dispose();
				}
				
			}
		});
		btnNewButton.setFocusable(false);
		menuBar.add(btnNewButton);
		carregarMenu(logado);
	}

	public void carregarMenu(Usuario logado) {
		
		switch (logado.getPerfil().ordinal()) {
		case 0:
			//admin
			//Variáveis do menu: Configurações
			//Variaveis do menu: Cadastros
			//Variáveis do menu: Educação
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;
		case 1:
			//diretor
			//Variáveis do menu: Configurações
			menuConfiguracoesSistema.setVisible(false);
			//Variaveis do menu: Cadastros
			menuCadastrosUsuarios.setVisible(false);
			//Variáveis do menu: Educação
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;
		case 2:
			//secretaria
			//Variáveis do menu: Configurações
			menuConfiguracoesSistema.setVisible(false);
			//Variaveis do menu: Cadastros
			menuCadastrosUsuarios.setVisible(false);
			menuCadastrosSecretaria.setVisible(false);
			//Variáveis do menu: Educação
			menuEducacaoMatricula.setVisible(false);
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;
		case 3:
			//professor
			//Variáveis do menu: Configurações
			menuConfiguracoesSistema.setVisible(false);
			//Variaveis do menu: Cadastros
			menuCadastros.setVisible(false);
			menuCadastrosUsuarios.setVisible(false);
			menuCadastrosSecretaria.setVisible(false);
			menuCadastrosTurmas.setVisible(false);
			menuCadastrosDisciplinas.setVisible(false);
			menuCadastrosAlunos.setVisible(false);
			menuCadastrosProfessores.setVisible(false);
			menuCadastrosResponsaveis.setVisible(false);
			//Variáveis do menu: Educação
			menuEducacaoMatricula.setVisible(false);
			menuEducacaoJustificativa.setVisible(false);
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;
		case 4:
			//aluno
			//Variáveis do menu: Configurações
			menuConfiguracoesSistema.setVisible(false);
			//Variaveis do menu: Cadastros
			menuCadastros.setVisible(false);
			menuCadastrosUsuarios.setVisible(false);
			menuCadastrosSecretaria.setVisible(false);
			menuCadastrosTurmas.setVisible(false);
			menuCadastrosDisciplinas.setVisible(false);
			menuCadastrosAlunos.setVisible(false);
			menuCadastrosProfessores.setVisible(false);
			menuCadastrosResponsaveis.setVisible(false);
			//Variáveis do menu: Educação
			menuEducacaoMatricula.setVisible(false);
			menuEducacaoOcorrencia.setVisible(false);
			menuEducacaoDiario.setVisible(false);
			menuEducacaoAvaliacao.setVisible(false);
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;
		case 5:
			//responsavel
			//Variáveis do menu: Configurações
			menuConfiguracoesSistema.setVisible(false);
			//Variaveis do menu: Cadastros
			menuCadastros.setVisible(false);
			menuCadastrosUsuarios.setVisible(false);
			menuCadastrosSecretaria.setVisible(false);
			menuCadastrosTurmas.setVisible(false);
			menuCadastrosDisciplinas.setVisible(false);
			menuCadastrosAlunos.setVisible(false);
			menuCadastrosProfessores.setVisible(false);
			menuCadastrosResponsaveis.setVisible(false);
			//Variáveis do menu: Educação
			menuEducacaoMatricula.setVisible(false);
			menuEducacaoOcorrencia.setVisible(false);
			menuEducacaoDiario.setVisible(false);
			menuEducacaoAvaliacao.setVisible(false);
			//Variáveis do menu: Consultas
			//Variáveis do menu: Sobre
			break;

		default:
			break;
		}
		
	}

	public JFrame getFrmJavaSenaiSchool() {
		return frmJavaSenaiSchool;
	}

	public void setFrmJavaSenaiSchool(JFrame frmJavaSenaiSchool) {
		this.frmJavaSenaiSchool = frmJavaSenaiSchool;
	}

}
