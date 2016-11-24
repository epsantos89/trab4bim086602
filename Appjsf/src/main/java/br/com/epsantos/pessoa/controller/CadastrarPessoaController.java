package br.com.epsantos.pessoa.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.epsantos.model.PessoaModel;
import br.com.epsantos.repository.PessoaRepository;
import br.com.epsantos.usuario.controller.UsuarioController;
import br.com.epsantos.uteis.Uteis;

/*Classe é um bean gerenciado pelo CDI, ela é transformada em um bean gereciado a partir da anottation @Named
 * pertecente ao CDI. Irá realizar o processo de salvar uma nova pessoa.*/

@Named(value="cadastrarPessoaController")
//Gerado a cada nova sessão, uma instancia por sesão, exemplo abrir um novo navegador @SessionScoped
@RequestScoped
public class CadastrarPessoaController {

	//Identifica que será realizado injeção de dependencia dos objetos na classe.
	@Inject
	PessoaModel pessoaModel;

	@Inject
	UsuarioController usuarioController;

	@Inject
	PessoaRepository pessoaRepository;


	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/**
	 *Salva um novo registro
	 */
	public void SalvarNovaPessoa(){

		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());

		//Informa que o cadastro foi realizado via input
		pessoaModel.setOrigemCadastro("I");

		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);

		this.pessoaModel = null;

		Uteis.MensagemInfo("Registro cadastrado com sucesso");

	}

}
