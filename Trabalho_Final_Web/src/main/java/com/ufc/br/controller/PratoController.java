package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Prato;
import com.ufc.br.service.PratoService;

@Controller
@RequestMapping("/prato")
public class PratoController {
	
	@Autowired
	private PratoService pratoService;
	
	@RequestMapping("/cadastrar")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("CadastrarPratos");
		mv.addObject("prato", new Prato());
		return mv;
	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvar(@Validated  Prato prato,  BindingResult result, @RequestParam(value="imagem") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("CadastrarPratos");
		if(result.hasErrors()) {
			return mv;
		}
		mv.addObject("mensagem", "Prato cadastrado com sucesso!");
		pratoService.cadastrar(prato,imagem);
		return mv;
	}
	
	@RequestMapping("/listar")
	public ModelAndView listar() {
		List<Prato> pratos = pratoService.listarPratos();
		ModelAndView mv = new ModelAndView("/PaginaListagem");
		mv.addObject("listaDePratos", pratos);
		return mv;
	}
	
	@RequestMapping("/excluir/{Id}")
	public ModelAndView excluir(@PathVariable Long Id) {
		pratoService.excluir(Id);
		ModelAndView mv = new ModelAndView("redirect:/prato/listar");
		return mv;
	}
	
	@RequestMapping("/atualizar/{Id}")
	public ModelAndView atualizar(@PathVariable Long Id) {
		Prato prato = pratoService.buscarPorId(Id);
		ModelAndView mv = new ModelAndView("CadastrarPratos");
		mv.addObject("prato", prato);
		return mv;
	}
}
