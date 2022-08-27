package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Ocorrencia;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class OcorrenciaDAO {
	
	//Salvar Ocorrencia
	public void salvar(Ocorrencia ocorrencia) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(ocorrencia);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Ocorrencia
	public void atualizar(Ocorrencia ocorrencia) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(ocorrencia);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Ocorrencia por ID
	public Ocorrencia buscar(Integer id) {
		Transaction transaction = null;
		Ocorrencia ocorrencia = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			ocorrencia = session.get(Ocorrencia.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ocorrencia;
	}
	
	//Buscar todas as Ocorrencias
	@SuppressWarnings("unchecked")
	public List<Ocorrencia> listar(){
		Transaction transaction = null;
		List<Ocorrencia> ocorrencia = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			ocorrencia = session.createQuery("from Ocorrencia").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ocorrencia;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ocorrencia> listar(Usuario responsavel, Boolean analisada){
		Transaction transaction = null;
		List<Ocorrencia> ocorrencias = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			ocorrencias = session.createQuery("from Ocorrencia t where t.matricula.aluno.responsavel = :responsavel AND t.analisada = :analisada")
					.setParameter("responsavel", responsavel)
					.setParameter("analisada", analisada)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ocorrencias;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ocorrencia> listar(Matricula matricula){
		Transaction transaction = null;
		List<Ocorrencia> ocorrencias = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			ocorrencias = session.createQuery("from Ocorrencia t where t.matricula = :matricula")
					.setParameter("matricula", matricula)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ocorrencias;
	}
	
	//Deletar uma ocorrencia
	public void deletar(Ocorrencia ocorrencia) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(ocorrencia);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
