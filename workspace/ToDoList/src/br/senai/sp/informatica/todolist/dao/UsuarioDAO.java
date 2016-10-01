package br.senai.sp.informatica.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.senai.sp.informatica.todolist.modelo.Usuario;

@Repository
public class UsuarioDAO {
	
	@PersistenceContext //dispensa a instancial, pois ocorre inje��o de dependencia
	private EntityManager manager;
	
	@Transactional
	public void inserir(Usuario usuario){
		manager.persist(usuario);
	}
	
	public Usuario logar(Usuario usuario){
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where " +
				 "u.login = :login and "  +
				 "u.senha = :senha", Usuario.class);
		
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		
		try{
			usuario = query.getSingleResult();
			return usuario;
			
		}catch (Exception e) {
			return null;
		}
		
		
		 
	}
	
	
}
