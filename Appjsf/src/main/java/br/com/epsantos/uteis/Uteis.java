package br.com.epsantos.uteis;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

//@author Elias Pereira
/*Criado classe com metodo para recuperar o EntityManager criado no filter JPAFilter*/
public class Uteis {

	public static EntityManager JpaEntityManager(){

		FacesContext facesContext = FacesContext.getCurrentInstance();

		ExternalContext externalContext = facesContext.getExternalContext();

		HttpServletRequest request  = (HttpServletRequest)externalContext.getRequest();

		return (EntityManager)request.getAttribute("entityManager");
	}

	//Recebe mensagem de alerta e apresenta na tela
		public static void Mensagem(String mensagem){

			FacesContext facesContext = FacesContext.getCurrentInstance();

			facesContext.addMessage(null, new FacesMessage("Alerta",mensagem));
		}

		//Recebe mensagem de atenção e apresenta na tela
		public static void MensagemAtencao(String mensagem){

			FacesContext facesContext = FacesContext.getCurrentInstance();

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
		}

		//Recebe mensagem de informação e apresenta na tela
		public static void MensagemInfo(String mensagem){

			FacesContext facesContext = FacesContext.getCurrentInstance();

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
		}


}
