package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.ufc.br.model.Gerente;
import com.ufc.br.service.GerenteService;


@Controller
@RequestMapping("/gerente")
public class GerenteController {
	@Autowired
	private GerenteService gerenteService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("CadastrarGerente");
		mv.addObject("gerente", new Gerente());
		return mv;
	}
	
	@RequestMapping("/salvar")
	public String salvar(Gerente gerente) {
		gerenteService.cadastrar(gerente);
		return "/Sucesso";
	}
	
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable Long id) {
		gerenteService.excluir(id);
		ModelAndView mv = new ModelAndView("Sucesso");
		return mv;
	}
	
	@RequestMapping("/atualizar/{id}")
	public ModelAndView atualizar(@PathVariable Long id) {
		Gerente gerente = gerenteService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("CadastrarGerente");
		mv.addObject("gerente", gerente);
		return mv;
	}
}
