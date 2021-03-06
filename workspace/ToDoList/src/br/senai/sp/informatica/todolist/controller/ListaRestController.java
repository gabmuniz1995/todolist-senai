package br.senai.sp.informatica.todolist.controller;

import  org.springframework.http.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.informatica.todolist.dao.ListaDAO;
import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;
 

@RestController
public class ListaRestController {

	@Autowired //informa pro spring que esta obj listaDao precisa ser injetado
	private ListaDAO listaDao;
	
	//sempre coloque a transaction annotation na camada mais baixa - DAO, pois aqui podera abrir trans desnecessárias
	@RequestMapping(value="/lista", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Lista> inserir(@RequestBody String strLista){
	
		try {
			
			JSONObject jsonObj = new JSONObject(strLista);
			Lista lista = new Lista();
			lista.setTitulo(jsonObj.getString("titulo"));
			
			lista.setItens(new ArrayList<>());
			
			JSONArray arrayItens = jsonObj.getJSONArray("itens");  
			
			 

			for(int i = 0; i < arrayItens.length(); i++){
				ItemLista item = new ItemLista();
				item.setDescricao(arrayItens.getString(i));
				item.setLista(lista);
				lista.getItens().add(item);
			}
			
			listaDao.inserir(lista);
		 
		 
			URI location = new URI("/lista/" + lista.getId());
			return ResponseEntity.created(location).body(lista);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
	
	@RequestMapping(value="/lista", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> listar(){
		return listaDao.listar();
	}
	
	@RequestMapping(value="/lista/{idLista}", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Lista mostrarLista(@PathVariable("idLista") long idLista){
		return listaDao.mostrarLista(idLista);
	}
	
	
	@RequestMapping(value="/lista/{id}", method=RequestMethod.DELETE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> excluir(@PathVariable("id") long idLista){
		 listaDao.excluir(idLista);
		 
		 return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/lista/{idItem}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> excluirItem(@PathVariable("idItem") long idItem){
		listaDao.excluirItem(idItem);
		return ResponseEntity.noContent().build();
	}
}
