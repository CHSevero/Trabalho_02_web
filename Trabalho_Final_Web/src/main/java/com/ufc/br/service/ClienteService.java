package com.ufc.br.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Cliente;
import com.ufc.br.model.Role;
import com.ufc.br.repository.ClienteRepository;


@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;


	public void cadastrar(Cliente cliente) {
		// TODO Auto-generated method stub
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        Role role = new Role();
        role.setPapel("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        cliente.setRoles(roles);
		clienteRepository.save(cliente);
	}

	public void excluir(Long id) {
		// TODO Auto-generated method stub
		clienteRepository.deleteById(id);
	}

	public Cliente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return clienteRepository.getOne(id);
	}

	public Cliente buscarPorLogin(String username) {
		// TODO Auto-generated method stub
		return clienteRepository.findByLogin(username);
	}
}
