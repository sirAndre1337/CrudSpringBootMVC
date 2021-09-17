package com.example.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.curso.model.Pessoa;
import com.example.curso.model.Telefone;
import com.example.curso.repository.PessoaRepository;
import com.example.curso.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	TelefoneRepository telefoneRepository;
	
	@RequestMapping(method = RequestMethod.GET , value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoaobj" , new Pessoa());
		andView.addObject("pessoas" , pessoaRepository.findAll());
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa){
		
		pessoaRepository.save(pessoa);
		return pessoas();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/listapessoas")
	public ModelAndView pessoas( ) {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasList = pessoaRepository.findAll();
		andView.addObject("pessoaobj" , new Pessoa());
		andView.addObject("pessoas", pessoasList);
		return andView;
	}
	
	@GetMapping(value = "/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long id) {
		
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Pessoa pessoaBuscada = pessoaRepository.findById(id).get();
		
		andView.addObject("pessoaobj" , pessoaBuscada);
		return andView;
	}
	
	@GetMapping(value = "/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long id) {
		
		pessoaRepository.deleteById(id);
		
		return pessoas();
	}
	
	@PostMapping(value = "**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		List<Pessoa> lista = pessoaRepository.pesquisaPorNome(nomepesquisa.toUpperCase());
		andView.addObject("pessoaobj" , new Pessoa());
		andView.addObject("pessoas", lista);
		return andView;
	}
	
	@GetMapping(value = "/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long id) {
		
		Pessoa pessoa = pessoaRepository.findById(id).get();
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("telefones", pessoa.getTelefones());
		andView.addObject("pessoaobj" , pessoa);
		
		return andView;
	}
	
	@PostMapping(value = "**/salvartelefone/{idpessoa}")
	public ModelAndView salvaTelefone(Telefone telefone , @PathVariable("idpessoa") Long id) {
		
		
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		Pessoa pessoa = pessoaRepository.findById(id).get();
		
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		andView.addObject("pessoaobj" , pessoa);
		andView.addObject("telefones", telefoneRepository.buscarTelefonesDoUser(id));
		return andView;
	}
	
	@GetMapping(value = "/excluirtelefone/{telefoneid}")
	public ModelAndView excluirTelefone(@PathVariable("telefoneid") Long id) {
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		
		Pessoa pessoa = telefoneRepository.findById(id).get().getPessoa();
		telefoneRepository.deleteById(id);
		
		andView.addObject("pessoaobj" , pessoa);
		andView.addObject("telefones", telefoneRepository.buscarTelefonesDoUser(pessoa.getId()));
		
		return andView;
	}
}
