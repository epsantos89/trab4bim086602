package br.com.epsantos.pessoa.controller;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.epsantos.repository.PessoaRepository;

//@author Elias Pereira
/*Classe é um bean gerenciado pelo CDI, ela é transformada em um bean gereciado a partir da anottation @Named
 * pertecente ao CDI.*/
@Named(value="graficoPizzaPessoaController")
@RequestScoped
public class GraficoPizzaPessoaController {

	@Inject
	private PessoaRepository pessoaRepository;

 //O gráfico Pie exibe as informações num gráfico em forma de uma torta.
	private PieChartModel pieChartModel;

	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	@PostConstruct
	public  void init(){

		this.pieChartModel = new PieChartModel();

		this.MontaGrafico();
	}

//Monta o gráfico na tela
	private void MontaGrafico(){

		//Realiza consulta dos dados para montar o gráfico
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();


		//Informa os valores para montar o gráfico, chave e valor
		hashtableRegistros.forEach((chave,valor) -> {

			pieChartModel.set(chave, valor);

		});

		pieChartModel.setTitle("Total de Pessoas cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");


	}
}
