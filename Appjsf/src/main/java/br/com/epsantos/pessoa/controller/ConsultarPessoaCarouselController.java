package br.com.epsantos.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.epsantos.model.PessoaModel;
import br.com.epsantos.repository.PessoaRepository;
//@author Elias Pereira
/*Classe é um bean gerenciado pelo CDI, ela é transformada em um bean gereciado a partir da anottation @Named
 * pertecente ao CDI.Utilizada para consultar uma pessoa*/
@Named(value="consultarPessoaCarouselController")
@ViewScoped
public class ConsultarPessoaCarouselController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject transient
	private PessoaRepository pessoaRepository;

 //Cuida da produção do objeto no EntityManager
	@Produces
	private List<PessoaModel> pessoas;

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	/***
	 * Carrega na tela as pessoas cadastradas na iniciliazação
	 */
	@PostConstruct
	private void init(){
		//Retorna as pessoas cadastradas
		this.pessoas = pessoaRepository.GetPessoas();
	}




}
