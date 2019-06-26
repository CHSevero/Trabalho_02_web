package com.ufc.br.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.service.ClienteService;
import com.ufc.br.service.ItemService;
import com.ufc.br.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoControler {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ItemService itemService;
	
	private double sumTotal(HttpSession session){

        List<Item> sacola = (List<Item>) session.getAttribute("sacola");
        double s = 0;

        for(Item item : sacola){
            s += item.getQuantidade() * item.getPrato().getPreco();
        }

        return s;

    }
	
   
    
    private int exists(long codigo, List<Item> sacola) {

        for(int i = 0; i < sacola.size(); i++) {

            if(sacola.get(i).getPrato().getId() == codigo) {
                return i;
            }
        }

        return -1;

    }
	
    
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") long codigo, HttpSession session) {

        Double total = 0.0;

        List<Item> sacola = (List<Item>) session.getAttribute("sacola");
        int index = this.exists(codigo, sacola);

        if(sacola.get(index).getQuantidade() > 1){
            int qtd = sacola.get(index).getQuantidade();
            sacola.get(index).setQuantidade(qtd-1);

        }else {
            sacola.remove(index);
        }

        for(Item item : sacola) {
            total += item.getQuantidade() * item.getPrato().getPreco();
        }

        session.setAttribute("sacola", sacola);
        session.setAttribute("total", total);
        return "redirect:/pedido/telaPedido";
    }
	
	@RequestMapping("/pedir")
	public ModelAndView pedirProduto() {
		ModelAndView mv = new ModelAndView("sacola/endereco-entrega");
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Cliente cliente = clienteService.buscarPorLogin(user.getUsername());
		
		mv.addObject("enderecoCliente", cliente.getEndereco());
		
		mv.addObject("cliente", cliente);
		
		return mv;
		
	}
	
	@RequestMapping("/finalizar")
	public ModelAndView finalizar(HttpSession session){
		ModelAndView mv = new ModelAndView("redirect:/cliente/pedidos");
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Cliente cliente = clienteService.buscarPorLogin(user.getUsername());
		
	
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setEnderecoDeEntrega(cliente.getEndereco());
		
		Iterable<Item> sacola = (Iterable<Item>) session.getAttribute("sacola");
		
		Double total = 0.0;
		pedido.setTotal(total);
		pedidoService.salvar(pedido);
		for(Item item : sacola) {
			item.setPedido(pedido);
		}
		
		itemService.salvar(sacola);
		total = (Double) session.getAttribute("total");
		pedido.setTotal(total);
		pedidoService.salvar(pedido);
		
		session.removeAttribute("sacola");
		session.removeAttribute("total");
		return mv;
	}
	

	
}
