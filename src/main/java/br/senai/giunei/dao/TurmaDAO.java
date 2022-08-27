package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Turma;
import br.senai.giunei.util.HibernateUtil;

public class TurmaDAO {
	
	//Salvar Turma
	public void salvar(Turma turma) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(turma);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Turma
	public void atualizar(Turma turma) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(turma);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Tarefa por ID
	public Turma buscar(Integer id) {
		Transaction transaction = null;
		Turma turma = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			turma = session.get(Turma.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return turma;
	}
	
	//Buscar todas as tarefas
	@SuppressWarnings("unchecked")
	public List<Turma> listar(){
		Transaction transaction = null;
		List<Turma> turmas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			turmas = session.createQuery("from Turma").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return turmas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Turma> listar(String nome){
		Transaction transaction = null;
		List<Turma> turmas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			turmas = session.createQuery("from Turma t where t.nome like :nome")
					.setParameter("nome", "%"+nome+"%")
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return turmas;
	}
	
	//Deletar uma tarefa pelo objeto
	public void deletar(Turma turma) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(turma);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
