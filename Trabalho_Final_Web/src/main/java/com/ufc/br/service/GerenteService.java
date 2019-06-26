package com.ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Gerente;
import com.ufc.br.repository.GerenteRepository;

@Service
public class GerenteService {

	@Autowired
	private GerenteRepository gerenteRepository;
	public void cadastrar(Gerente gerente) {
		// TODO Auto-generated method stub
		gerenteRepository.save(gerente);
	}
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		gerenteRepository.deleteById(id);
	}
	public Gerente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return gerenteRepository.getOne(id);
	}

}
