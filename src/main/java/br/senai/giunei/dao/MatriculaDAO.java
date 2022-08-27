package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.enumered.ResultadoMatricula;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Turma;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class MatriculaDAO {
	
	//Salvar Matricula
	public void salvar(Matricula matricula) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(matricula);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Turma
	public void atualizar(Matricula matricula) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(matricula);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Tarefa por ID
	public Matricula buscar(Integer id) {
		Transaction transaction = null;
		Matricula matricula = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			matricula = session.get(Matricula.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matricula;
	}
	
	//Buscar todas as tarefas
	@SuppressWarnings("unchecked")
	public List<Matricula> listar(){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			matriculas = session.createQuery("from Matricula").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Matricula> listar(Usuario aluno){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			matriculas = session.createQuery("from Matricula t where t.aluno like :aluno")
					.setParameter("aluno", aluno)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Matricula> listar(Disciplina disciplina, Boolean finalizada){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			matriculas = session.createQuery("from Matricula t where t.disciplina = :disciplina AND t.finalizada = :finalizada order by t.aluno.nome")
					.setParameter("disciplina", disciplina)
					.setParameter("finalizada", finalizada)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Matricula> listarPorProfessor(Usuario professor){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			matriculas = session.createQuery("from Matricula t where t.disciplina.professor = :professor")
					.setParameter("professor", professor)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Matricula> listar(Usuario aluno, ResultadoMatricula resultado){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			matriculas = session.createQuery("from Matricula t where t.aluno = :aluno AND t.resultado = resultado")
					.setParameter("aluno", aluno)
					.setParameter("resultado", resultado)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	public List<Matricula> listar(Usuario aluno, Boolean finalizada){
		Transaction transaction = null;
		List<Matricula> matriculas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			matriculas = session.createQuery("from Matricula t where t.aluno = :aluno AND t.finalizado = finalizada")
					.setParameter("aluno", aluno)
					.setParameter("finalizada", finalizada)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return matriculas;
	}
	
	
	//Deletar uma tarefa pelo ID
	public void deletar(Matricula matricula) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(matricula);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
