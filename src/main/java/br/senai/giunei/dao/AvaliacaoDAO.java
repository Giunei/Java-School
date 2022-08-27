package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Avaliacao;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class AvaliacaoDAO {
	
	//Salvar Avaliacao
	public void salvar(Avaliacao avaliacao) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(avaliacao);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Contato
	public void atualizar(Avaliacao avaliacao) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(avaliacao);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Avaliacao por ID
	public Avaliacao buscar(Integer id) {
		Transaction transaction = null;
		Avaliacao avaliacao = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			avaliacao = session.get(Avaliacao.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return avaliacao;
	}
	
	//Buscar todas as avaliacoes
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listar(){
		Transaction transaction = null;
		List<Avaliacao> avaliacao = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			avaliacao = session.createQuery("from Avaliacao").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return avaliacao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listar(Matricula matricula){
		Transaction transaction = null;
		List<Avaliacao> contatos = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			contatos = session.createQuery("from Avaliacao t where t.matricula = :matricula")
					.setParameter("matricula", matricula)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return contatos;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Avaliacao> listarPorProfessor(Usuario professor){
		Transaction transaction = null;
		List<Avaliacao> avaliacoes = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			avaliacoes = session.createQuery("from Avaliacao t where t.professor = :professor")
					.setParameter("professor", professor)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return avaliacoes;
	}
	
	//Deletar uma contato
	public void deletar(Avaliacao contato) {
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
