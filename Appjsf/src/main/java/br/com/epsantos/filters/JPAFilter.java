package br.com.epsantos.filters;

import java.io.IOException;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;



/***
 * Esse filter irá gerenciar o EntityManager que será utilizado para executar as
 * iterações com o bd. irá ser chamado quando realizado alguma requisição no faces servlet que por sua vez
 * gerencia o clico de vida do processamento da aplicação.
 * */
@WebFilter(servletNames ={ "Faces Servlet" })
public class JPAFilter implements Filter {


	private EntityManagerFactory entityManagerFactory;

	private String persistence_unit_name = "unit_app";

    public JPAFilter() {

    }

	public void destroy() {

		this.entityManagerFactory.close();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		/*Criando o EntityManager para as ações de persistencia*/
		EntityManager entityManager =  this.entityManagerFactory.createEntityManager();

		/*Insere o EntityManager na requisição*/
		request.setAttribute("entityManager", entityManager);

		/*Abre a transação*/
		entityManager.getTransaction().begin();

		/*Incia o faces servlet*/
		chain.doFilter(request, response);

		try {

			/*Realiza o commit gravando a transação no banco de dados, caso não contenha erros*/
			entityManager.getTransaction().commit();

		} catch (Exception e) {

			/*Realiza o rollback desfazendo a transação no banco de dados, caso contenha erros*/
			entityManager.getTransaction().rollback();
		}
		finally{

			/*Finaliza a transação após realizar o rollback ou o commit*/
			entityManager.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

		/*Realiza a criação do EntityManagerFactory com a parametrização definida no arquivo de configuração da persistência*/
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name);
	}

}