package br.ucb.model;

import java.io.Serializable;

import com.google.firebase.database.Exclude;

public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long data_fim;
	private Long data_inicio;
	private Long dataCadastro;
	private String descricao;
	private String local;
	private Local localEntidade;
	private String nome;
	private String tipo;

	public Evento() {
	}

	public Evento(String id, Long data_fim, Long data_inicio, Long dataCadastro, String descricao, String local,
			String nome, String tipo) {
		this.id = id;
		this.data_fim = data_fim;
		this.data_inicio = data_inicio;
		this.dataCadastro = dataCadastro;
		this.descricao = descricao;
		this.local = local;
		this.nome = nome;
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getData_fim() {
		return data_fim;
	}

	public void setData_fim(Long data_fim) {
		this.data_fim = data_fim;
	}

	public Long getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Long data_inicio) {
		this.data_inicio = data_inicio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	@Exclude
	public boolean isPersistido() {
		return dataCadastro != null;
	}
	
	@Exclude
	public Local getLocalEntidade() {
		return localEntidade;
	}
	
	@Exclude
	public String getTipoNomeLocal() {
		return localEntidade == null ? "Não encontrado" : localEntidade.getTipoNome();
	}
	
	public void setLocalEntidade(Local localEntidade) {
		this.localEntidade = localEntidade;
	}

}
