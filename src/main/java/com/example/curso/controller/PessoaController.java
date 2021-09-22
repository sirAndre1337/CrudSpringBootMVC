package com.example.curso.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.example.curso.service.ReportUtil;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoaobj" , new Pessoa());
		andView.addObject("pessoas" , pessoaRepository.findAll());
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasList = pessoaRepository.findAll();
			modelAndView.addObject("pessoaobj" , pessoa);
			modelAndView.addObject("pessoas", pessoasList);
			
			// pegando as msg de erros
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError: bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // msg das Anotacoes definidas no modelo
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
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
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa , @RequestParam("sexopesquisa") String sexopesquisa) {
		
		
		if (!sexopesquisa.isEmpty() && nomepesquisa.isEmpty()) {
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
			List<Pessoa> lista = pessoaRepository.pesquisaPorSexo(sexopesquisa);
			andView.addObject("pessoaobj" , new Pessoa());
			andView.addObject("pessoas", lista);
			return andView;
			
		} if (!sexopesquisa.isEmpty() && !nomepesquisa.isEmpty()) {
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
			List<Pessoa> lista = pessoaRepository.pesquisaPorSexoENome(nomepesquisa.toUpperCase(), sexopesquisa);
			andView.addObject("pessoaobj" , new Pessoa());
			andView.addObject("pessoas", lista);
			return andView;
		}
		
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
		
		if (telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty() || !telefone.getTipo().isEmpty() && telefone.getTipo().trim().isEmpty()){
			
			List<String> msg = new ArrayList<String>();
			
			if (telefone.getNumero().isEmpty()) {
				msg.add("Informe o numero");
			}
			
			if(telefone.getTipo().isEmpty() || !telefone.getTipo().isEmpty() && telefone.getTipo().trim().isEmpty()) {
				msg.add("Informe o tipo");
			}
			
			andView.addObject("pessoaobj" , pessoa);
			andView.addObject("telefones", telefoneRepository.buscarTelefonesDoUser(id));
			andView.addObject("msg", msg);
			return andView;
			
		}
		
		
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
	
	@GetMapping(value = "**/pesquisarpessoa")
	public void imprimirPDF(@RequestParam("nomepesquisa") String nomepesquisa , @RequestParam("sexopesquisa") String sexopesquisa , HttpServletRequest request , HttpServletResponse response) throws Exception {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		if (!sexopesquisa.isEmpty() && nomepesquisa.isEmpty()) {
			pessoas = pessoaRepository.pesquisaPorSexo(sexopesquisa);
		} else if (!sexopesquisa.isEmpty() && !nomepesquisa.isEmpty()) {
			pessoas = pessoaRepository.pesquisaPorSexoENome(nomepesquisa.toUpperCase(), sexopesquisa);
		} else {
			pessoas = pessoaRepository.pesquisaPorNome(nomepesquisa.toUpperCase());
		}
		
		/*Chama o servico que faz a geracao do relatorio*/
		
		 byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", request.getServletContext());
		 
		 /*Tamanho da respota*/
		 response.setContentLengthLong(pdf.length);
		 
		 /*Definir na respota o tipo de arquivo*/
		 response.setContentType("application/octet-stream");
		 
		 /*Definir o cabecalho da resposta*/
		 String headerKey = "Content-Disposition";
		 String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		 response.setHeader(headerKey, headerValue);
		 
		 /*Finalizar a respota pro navegador*/
		 response.getOutputStream().write(pdf);
		 
	}
}