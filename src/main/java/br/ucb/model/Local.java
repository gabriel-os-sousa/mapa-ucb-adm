package br.ucb.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Local implements Serializable {
	
	private String id;
	private String nome;
	private String tipo;
	private String descricao;
	private double latitude;
	private double longitude;
	private int zIndex;
	private Long dataCadastro;
	private List<Local> localVizinho;

	public Local() {
	}

	
	@Override
	public String toString() {
		return " Nome: " + this.getNome() + " - Desc: " + this.getDescricao();
	}

	public List<Local> getLocalVizinho() {
		return localVizinho;
	}

	public void setLocalVizinho(List<Local> localVizinho) {
		this.localVizinho = localVizinho;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Long getDataCadastro() {
		return dataCadastro;
	}

	public Calendar getDataCadastroCalendar() {
		if (this.dataCadastro == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(this.dataCadastro);
		return c;
	}

	public void setDataCadastro(Long dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}