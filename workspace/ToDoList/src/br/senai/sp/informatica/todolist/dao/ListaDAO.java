package br.senai.sp.informatica.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
public class ListaDAO {
	
	@PersistenceContext //dispensa a instancial, pois ocorre injeção de dependencia
	private EntityManager manager;
	
	@Transactional
	public void inserir(Lista lista){
		manager.persist(lista);
	}
	
	
	public List<Lista> listar(){
		
	TypedQuery<Lista> query = 
			manager.createQuery("select l from Lista l", Lista.class);
	
	return query.getResultList();
	}
	
	public Lista mostrarLista(Long idLista){
		return manager.find(Lista.class, idLista );
		}
	
	
	
	@Transactional
	public void excluir(Long idLista){
		Lista lista = manager.find(Lista.class, idLista );
		manager.remove(lista);
	}
	
	@Transactional
	public void excluirItem(Long idItem){
		ItemLista item = manager.find(ItemLista.class, idItem );
		Lista lista = item.getLista();
		lista.getItens().remove(item);
		manager.merge(lista);
		
		
	}
	
	

}
