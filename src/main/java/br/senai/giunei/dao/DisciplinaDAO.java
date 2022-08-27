package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class DisciplinaDAO {
	
	//Salvar Tarefa
	public void salvar(Disciplina disciplina) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(disciplina);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Tarefa
	public void atualizar(Disciplina disciplina) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(disciplina);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Tarefa por ID
	public Disciplina buscar(Integer id) {
		Transaction transaction = null;
		Disciplina disciplina = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			disciplina = session.get(Disciplina.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return disciplina;
	}
	
	//Buscar todas as tarefas
	@SuppressWarnings("unchecked")
	public List<Disciplina> listar(){
		Transaction transaction = null;
		List<Disciplina> disciplina = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			disciplina = session.createQuery("from Disciplina").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return disciplina;
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> listar(String nome){
		Transaction transaction = null;
		List<Disciplina> disciplinas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			disciplinas = session.createQuery("from Disciplina t where t.nome like :nome")
					.setParameter("nome", "%"+nome+"%")
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return disciplinas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> listar(Usuario professor){
		Transaction transaction = null;
		List<Disciplina> disciplinas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			disciplinas = session.createQuery("from Disciplina t where t.professor = :professor ORDER BY t.nome")
					.setParameter("professor", professor)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return disciplinas;
	}
	
	//Deletar uma tarefa pelo ID
	public void deletar(Disciplina disciplina) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(disciplina);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
