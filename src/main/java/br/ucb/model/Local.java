package br.ucb.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.google.firebase.database.Exclude;

public class Local implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	
	public Local(String id) {
		this.id = id;
	}

	public Local(String id, String nome, String tipo, String descricao, double latitude, double longitude) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.descricao = descricao;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return " Nome: " + this.getNome() + " - Desc: " + this.getDescricao();
	}

	@Exclude
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
	
	@Exclude
	public String getTipoNome() {
		return tipo + " - " + nome;
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
	
	@Exclude
	public boolean isPersistido() {
		return dataCadastro != null;
	}
	
	@Exclude
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Local other = (Local) obj;
		return Objects.equals(id, other.id);
	}
}