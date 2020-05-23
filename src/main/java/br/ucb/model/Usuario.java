package br.ucb.model;

import java.io.Serializable;

import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.Exclude;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private String tipo;
	private Long dataCadastro;
	private UserRecord userRecord;
	
	public Usuario() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Long dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRecord getUserRecord() {
		return userRecord;
	}

	public void setUserRecord(UserRecord userRecord) {
		this.userRecord = userRecord;
	}
	
	@Exclude
	public boolean isAdmin() {
		return (boolean) this.userRecord.getCustomClaims().get("admin");
	}
	
	public boolean isPersisted() {
		if(this.userRecord != null) 
			return true;
		return false;
	}
}
