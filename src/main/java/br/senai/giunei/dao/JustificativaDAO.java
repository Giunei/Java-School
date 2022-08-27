package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.model.Avaliacao;
import br.senai.giunei.model.Contato;
import br.senai.giunei.model.Disciplina;
import br.senai.giunei.model.Matricula;
import br.senai.giunei.model.Justificativa;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class JustificativaDAO {
	
	//Salvar Justificativa
	public void salvar(Justificativa justificativa) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(justificativa);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Justificativa
	public void atualizar(Justificativa justificativa) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(justificativa);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Buscar Justificativa por ID
	public Justificativa buscar(Integer id) {
		Transaction transaction = null;
		Justificativa justificativa = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			justificativa = session.get(Justificativa.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return justificativa;
	}
	
	//Buscar todas as Justificativas
	@SuppressWarnings("unchecked")
	public List<Justificativa> listar(){
		Transaction transaction = null;
		List<Justificativa> ocorrencia = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			ocorrencia = session.createQuery("from Justificativa").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ocorrencia;
	}
	
	@SuppressWarnings("unchecked")
	public List<Justificativa> listar(Matricula matricula){
		Transaction transaction = null;
		List<Justificativa> justificativas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			justificativas = session.createQuery("from Justificativa t where t.matricula = :matricula")
					.setParameter("matricula", "%"+matricula+"%")
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return justificativas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Justificativa> listar(Usuario responsavel){
		Transaction transaction = null;
		List<Justificativa> justificativas = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			justificativas = session.createQuery("from Justificativa t where t.responsavel = :responsavel")
					.setParameter("responsavel", responsavel)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return justificativas;
	}
	
	//Deletar uma ocorrencia
	public void deletar(Justificativa justificativa) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(justificativa);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

}
