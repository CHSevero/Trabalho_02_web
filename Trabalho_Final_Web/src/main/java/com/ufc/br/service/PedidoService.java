package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	
	public void salvar(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public List<Pedido> listarPorCliente(Cliente cliente){
		return pedidoRepository.findByCliente(cliente);
	}
}
