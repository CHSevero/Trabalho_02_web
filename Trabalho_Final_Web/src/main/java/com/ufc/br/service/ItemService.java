package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private PratoService pratoService;
	
	public Item criarItem(Long codigoPrato) {
		Item item = new Item();
		item.setPrato(pratoService.buscarPorId(codigoPrato));
		item.setQuantidade(1);
		return item;
	}
	
	public void salvar(Iterable<Item> itens) {
		itemRepository.saveAll(itens);
	}
	
	public List<Item> ListarItensPorPedido(Pedido pedido){
		List<Item> itens = itemRepository.findByPedido(pedido);
		return itens;
	}


}
