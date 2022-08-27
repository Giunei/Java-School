package br.senai.giunei.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.senai.giunei.enumered.Perfil;
import br.senai.giunei.model.Usuario;
import br.senai.giunei.util.HibernateUtil;

public class UsuarioDAO {
	
	//Salvar Usuario
	public void salvar(Usuario usuario) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(usuario);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Atualizar Usuario
	public void atualizar(Usuario usuario) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(usuario);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Busca e lista com nome passado
	@SuppressWarnings("unchecked")
	public Usuario buscar(String email){
		Transaction transaction = null;
		List<Usuario> usuarios = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			usuarios = session.createQuery("from Usuario t where t.email like :email")
					.setParameter("email", email)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		if (usuarios.isEmpty()) {
			return new Usuario();
		} else {
			return usuarios.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscar(Perfil perfil){
		Transaction transaction = null;
		List<Usuario> usuarios = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			usuarios = session.createQuery("from Usuario t where t.perfil like : perfil")
					.setParameter("perfil", perfil)
					.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return usuarios;
	}
	
	//Buscar todos os usuarios
	@SuppressWarnings("unchecked")
	public List<Usuario> listar(){
		Transaction transaction = null;
		List<Usuario> usuarios = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			//iniciando a transação
			transaction = session.beginTransaction();
			
			//buscar todos os obetos-tarefas
			usuarios = session.createQuery("from Usuario").list();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return usuarios;
	}
	
	//Deletar um professor pelo ID
	public void deletar(Usuario usuario) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.delete(usuario);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
}
