package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class ContatoDAO {
	
	//Salvar Contato
	public void salvar(Contato contato) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(contato);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Contato
	public void atualizar(Contato contato) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(contato);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Contato por ID
	public Contato buscar(Integer id) {
		Transaction transaction = null;
		Contato contato = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			contato = session.get(Contato.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return contato;
	}
	
	//Buscar todos os contatos
	@SuppressWarnings("unchecked")
	public List<Contato> listar(){
		Transaction transaction = null;
		List<Contato> contato = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			contato = session.createQuery("from Contato").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return contato;
	}
	
	@SuppressWarnings("unchecked")
	public List<Contato> listar(Usuario usuario){
		Transaction transaction = null;
		List<Contato> contatos = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			contatos = session.createQuery("from Contato t where t.usuario like = usuario order by t.tipo")
					.setParameter("usuario", "%"+usuario+"%")
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return contatos;
	}
	
	//Deletar uma contato
	public void deletar(Contato contato) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(contato);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
