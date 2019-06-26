package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Prato;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.PratoUtil;
@Service
public class PratoService {
	
	@Autowired
	private PratoRepository pratoRepository;
	
	public void cadastrar(Prato prato, MultipartFile imagem) {
		// TODO Auto-generated method stub
		String caminho= "images/" + prato.getNome() + ".png";
		PratoUtil.salvarImagens(caminho,imagem);
		pratoRepository.save(prato);
	}

	public List<Prato> listarPratos() {
		// TODO Auto-generated method stub
		return pratoRepository.findAll();
	}

	public void excluir(Long id) {
		// TODO Auto-generated method stub
		pratoRepository.deleteById(id);
	}

	public Prato buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return pratoRepository.getOne(id);
	}




}
