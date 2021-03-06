package br.senai.sp.informatica.todolist.controller;


import java.net.URI;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.senai.sp.informatica.todolist.dao.ItemDAO;
import br.senai.sp.informatica.todolist.modelo.ItemLista;


@RestController 
public class ItemRestController {

	@Autowired
	private ItemDAO itemDao;
	
	@RequestMapping(value="/item/{id}", method=RequestMethod.PUT, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeito(@PathVariable("id") long idItem, @RequestBody String strFeito){
		 		
			try {
				JSONObject job = new JSONObject(strFeito);
				 itemDao.marcarFeito(idItem, job.getBoolean("feito"));
				 HttpHeaders responseHeaader = new HttpHeaders();
				 URI location = new URI("/item/" + idItem);
				 responseHeaader.setLocation(location);
				 				 
				 return new  ResponseEntity<Void>(responseHeaader, HttpStatus.OK);
			} catch (Exception e) {
				return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
 	
	}
	
	@RequestMapping(value="/lista/{id}/item", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemLista> addItem(@PathVariable("id") long idLista, @RequestBody ItemLista item){
		itemDao.inserir(idLista, item);
	
		try {
			 
			 URI location = new URI("/item/" + item.getId());
			 return    ResponseEntity.created(location).body(item);
		} catch (Exception e) {
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@RequestMapping(value="/item/{idItem}", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ItemLista mostrarLista(@PathVariable("idItem") Long idItem){
		return itemDao.mostrarItem(idItem);
	}
	
}
