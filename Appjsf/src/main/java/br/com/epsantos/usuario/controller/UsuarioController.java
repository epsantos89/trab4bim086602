package br.com.epsantos.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.epsantos.model.UsuarioModel;
import br.com.epsantos.repository.UsuarioRepository;
import br.com.epsantos.repository.entity.UsuarioEntity;
import br.com.epsantos.uteis.Uteis;

//@author Elias Pereira
/*Classe é um bean gerenciado pelo CDI, ela é transformada em um bean gereciado a partir da anottation @Named
 * pertecente ao CDI.*/
@Named(value="usuarioController")
//Gerado a cada nova sessão, uma instancia por sesão, exemplo abrir um novo navegador @SessionScoped
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	//Identifica que será realizado injeção de dependencia dos objetos na classe.
	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private UsuarioEntity usuarioEntity;

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	//Retornar o usuário logado no sistema
	public UsuarioModel GetUsuarioSession(){

		FacesContext facesContext = FacesContext.getCurrentInstance();

		return (UsuarioModel)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	//Finaliza a sessão do usuário redirecionando para a página de login
	public String Logout(){

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/index.xhtml?faces-redirect=true";
	}

	//realiza a autenticação do usuário
	public String EfetuarLogin(){

		if(StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())){

			Uteis.Mensagem("Favor informar o login!");
			return null;
		}
		else if(StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())){

			Uteis.Mensagem("Favor informara senha!");
			return null;
		}
		else{

			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);

			if(usuarioEntity!= null){

				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());


				FacesContext facesContext = FacesContext.getCurrentInstance();

				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);


				return "sistema/home?faces-redirect=true";
			}
			else{

				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}


	}

}
