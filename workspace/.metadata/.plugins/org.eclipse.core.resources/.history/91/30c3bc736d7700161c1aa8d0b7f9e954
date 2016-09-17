package br.senai.sp.informatica.todolist.modelo;

import java.util.List;

public class Lista {
	
	private Long id;
	private String titulo;
	private List<ItemLista> itens;
	
	
	
	//metodos de acesso
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<ItemLista> getItens() {
		return itens;
	}
	public void setItens(List<ItemLista> itens) {
		this.itens = itens;
	}
	
	
	public boolean isRealizada(){
		for(ItemLista item: itens){
			if(!(item.isFeito())){
			return false;
			}
		}
		return true;
	}
	

}
