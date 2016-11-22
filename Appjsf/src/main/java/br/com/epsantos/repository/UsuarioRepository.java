package br.com.epsantos.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.epsantos.model.UsuarioModel;
import br.com.epsantos.repository.entity.UsuarioEntity;
import br.com.epsantos.uteis.Uteis;

/*Classe irá conter os metodos de criação do query que será utilizada para localizar um usuário no banco de dados*/
public class UsuarioRepository implements Serializable {


	private static final long serialVersionUID = 1L;

	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel){

		try {

			//Cria a consulta
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");

			//Passa o parametros para consulta
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());

			//Retorna o usuário caso ele seja localizado
			return (UsuarioEntity)query.getSingleResult();

		} catch (Exception e) {

			return null;
		}



	}
}