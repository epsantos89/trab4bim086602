package br.com.epsantos.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.epsantos.model.PessoaModel;
import br.com.epsantos.repository.PessoaRepository;

//@author Elias Pereira
/*
 * Classe realiza a estruturação do dados consultados em um arquivo no formato XML utilizando o Jdom
 */
@Named(value="exportarRegistrosXmlController")
@RequestScoped
public class ExportarRegistrosXmlController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject transient
	PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	/***
	 * Faz o download do arquivo
	 * @return
	 */
	public StreamedContent getArquivoDownload() {

		this.DownlaodArquivoXmlPessoa();

		return arquivoDownload;
	}

	/***
	 * Gera o arquivo XML para disponibiliza-lo para exportação
	 * @return
	 */
	private File GerarXmlPessoas(){

		//Formata a data
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();

		//Elemento raiz do arquivo XML
		Element elementPessoas = new Element("Pessoas");

		Document documentoPessoas = new Document(elementPessoas);

		pessoasModel.forEach(pessoa -> {


			//Monta as tags com os valores pertinentes a cada campo
			Element elementPessoa = new Element("Pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo()));

			//Formata a data
			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);

			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));

			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("endereco").setText(pessoa.getEndereco()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));

			elementPessoas.addContent(elementPessoa);
		});


		XMLOutputter xmlGerado = new XMLOutputter();

		try {


			/*Realiza a nomenclatura do arquivo*/
			String nomeArquivo =  "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");

			//Especifica o caminho, diretório em que o arquivo será salvo.
			File arquivo = new File("C:/Users/epsantos/Documents/".concat(nomeArquivo));

			FileWriter fileWriter =  new FileWriter(arquivo);


			xmlGerado.output(documentoPessoas, fileWriter);

			return arquivo;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	/***
	 * Prepara o arquivo para download
	 */
	public void DownlaodArquivoXmlPessoa(){

		File arquivoXml = this.GerarXmlPessoas();

		InputStream inputStream;

		try {

			inputStream = new FileInputStream(arquivoXml.getPath());

			arquivoDownload = new DefaultStreamedContent(inputStream,"application/xml",arquivoXml.getName());

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}



	}
}
