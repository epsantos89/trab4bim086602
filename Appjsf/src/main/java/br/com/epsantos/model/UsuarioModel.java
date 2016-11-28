package br.com.epsantos.model;

import java.io.Serializable;
//@author Elias Pereira
/*Essa classe será utilizada pelos Managed Beans para receber os dados que são informados nas páginas*/
public class UsuarioModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codigo;
	private String usuario;
	private String senha;

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
