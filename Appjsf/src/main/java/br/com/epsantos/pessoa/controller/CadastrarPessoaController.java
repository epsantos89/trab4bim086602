package br.com.epsantos.pessoa.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.epsantos.model.PessoaModel;
import br.com.epsantos.repository.PessoaRepository;
import br.com.epsantos.usuario.controller.UsuarioController;
import br.com.epsantos.uteis.Uteis;

/*Classe é um bean gerenciado pelo CDI, ela é transformada em um bean gereciado a partir da anottation @Named
 * pertecente ao CDI. Utilizada para cadastrar uma pessoa*/
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


	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/**
	 *SALVA UM NOVO REGISTRO VIA INPUT
	 */
	public void SalvarNovaPessoa(){

		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());

		//INFORMANDO QUE O CADASTRO FOI VIA INPUT
		pessoaModel.setOrigemCadastro("I");

		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);

		this.pessoaModel = null;

		Uteis.MensagemInfo("Registro cadastrado com sucesso");

	}

	/**
	 * REALIZANDO UPLOAD DE ARQUIVO
	 */
	 public void UploadRegistros() {

		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		 try {


			 if(this.file.getFileName().equals("")){
				 Uteis.MensagemAtencao("Nenhum arquivo selecionado!");
				 return;
			 }

			 DocumentBuilder builder = factory.newDocumentBuilder();

	                 Document doc = builder.parse(this.file.getInputstream());

	                 Element element = doc.getDocumentElement();

	                 NodeList nodes = element.getChildNodes();

	                for (int i = 0; i < nodes.getLength(); i++) {

	        	     Node node  = nodes.item(i);

	        	    if(node.getNodeType() == Node.ELEMENT_NODE){

	        		 Element elementPessoa =(Element) node;

	        		 //PEGANDO OS VALORES DO ARQUIVO XML
	        		 String nome     = elementPessoa.getElementsByTagName("nome").item(0).getChildNodes().item(0).getNodeValue();
	        		 String sexo     = elementPessoa.getElementsByTagName("sexo").item(0).getChildNodes().item(0).getNodeValue();
	        		 String email    = elementPessoa.getElementsByTagName("email").item(0).getChildNodes().item(0).getNodeValue();
	        		 String endereco = elementPessoa.getElementsByTagName("endereco").item(0).getChildNodes().item(0).getNodeValue();

	        		 PessoaModel newPessoaModel = new PessoaModel();

	        		 newPessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
	        		 newPessoaModel.setEmail(email);
	        		 newPessoaModel.setEndereco(endereco);
	        		 newPessoaModel.setNome(nome);
	        		 newPessoaModel.setOrigemCadastro("X");
	        		 newPessoaModel.setSexo(sexo);

	        		 //SALVANDO UM REGISTRO QUE VEIO DO ARQUIVO XML
	        		 pessoaRepository.SalvarNovoRegistro(newPessoaModel);
	        	   }
	              }



		     Uteis.MensagemInfo("Registros cadastrados com sucesso!");

		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} catch (SAXException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}


	 }

}
