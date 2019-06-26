package com.ufc.br.controller;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Prato;
import com.ufc.br.service.ClienteService;
import com.ufc.br.service.PedidoService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping("/pedidos")
	public ModelAndView pedidos() {
		ModelAndView mv = new ModelAndView("ListarPedido");
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Cliente cliente = clienteService.buscarPorLogin(user.getUsername());
		
		mv.addObject("pedidos", pedidoService.listarPorCliente(cliente));
		return mv;
	}
	@RequestMapping("/cadastrar")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("CadastrarCliente");
		mv.addObject("cliente", new Cliente());
		return mv;
	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvar(@Validated Cliente cliente, BindingResult result) {
		
		ModelAndView mv = new ModelAndView("CadastrarCliente");
		if(result.hasErrors()) {
			return mv;
		}
		mv.addObject("mensagem", "Cliente cadastrado com sucesso!");
		clienteService.cadastrar(cliente);
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable Long id) {
		clienteService.excluir(id);
		ModelAndView mv = new ModelAndView("Sucesso");
		return mv;
	}
	
	@RequestMapping("/atualizar/{id}")
	public ModelAndView atualizar(@PathVariable Long id) {
		Cliente cliente = clienteService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("CadastrarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}
	
}
