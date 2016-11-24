package br.com.epsantos.repository;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.epsantos.model.PessoaModel;
import br.com.epsantos.repository.entity.PessoaEntity;
import br.com.epsantos.repository.entity.UsuarioEntity;
import br.com.epsantos.uteis.Uteis;

/*Classe responsável por persistir a entidade PessoaEntity*/
public class PessoaRepository {

	@Inject
	PessoaEntity pessoaEntity;

	EntityManager entityManager;

	/***
	 * Metodo responsável por salvar uma nova pessoa
	 * @param pessoaModel
	 */
	public void SalvarNovoRegistro(PessoaModel pessoaModel){

		entityManager =  Uteis.JpaEntityManager();

		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo());

		pessoaEntity.setUsuarioEntity(usuarioEntity);

		entityManager.persist(pessoaEntity);

	}
}