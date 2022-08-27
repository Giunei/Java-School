package br.senai.giunei.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import br.senai.giunei.model.Avaliacao;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Justificativa;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Ocorrencia;
import br.senai.giunei.model.Registro;
import br.senai.giunei.model.Turma;
import br.senai.giunei.model.Usuario;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost/javaschool?useSSL=false&amp");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "giunei");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//				settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				settings.put(Environment.HBM2DDL_AUTO, "create");
//				settings.put(Environment.HBM2DDL_AUTO, "none");

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Disciplina.class);
				configuration.addAnnotatedClass(Matricula.class);
				configuration.addAnnotatedClass(Turma.class);
				configuration.addAnnotatedClass(Usuario.class);
				configuration.addAnnotatedClass(Contato.class);
				configuration.addAnnotatedClass(Registro.class);
				configuration.addAnnotatedClass(Avaliacao.class);
				configuration.addAnnotatedClass(Ocorrencia.class);
				configuration.addAnnotatedClass(Justificativa.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sessionFactory;
	}

}
