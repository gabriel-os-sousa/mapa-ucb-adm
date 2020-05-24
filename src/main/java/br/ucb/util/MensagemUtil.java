package br.ucb.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MensagemUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Mensagem> mensagens = new ArrayList<>();

	public void addSucessoMensagem(String mensagem) {
		mensagens.add(new Mensagem(Tipo.SUCESSO, mensagem));
	}

	public void addErroMensagem(String mensagem) {
		mensagens.add(new Mensagem(Tipo.ERRO, mensagem));
	}

	public void addAvisoMensagem(String mensagem) {
		mensagens.add(new Mensagem(Tipo.AVISO, mensagem));
	}
	
	public void addMensagem(Tipo tipo , String mensagem) {
		mensagens.add(new Mensagem(tipo, mensagem));
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public boolean isPossuiMensagem() {
		return !mensagens.isEmpty();
	}

	public enum Tipo {
		SUCESSO("success"), ERRO("danger"), AVISO("warning");

		private String classeCss;

		private Tipo(String classeCss) {
			this.classeCss = classeCss;
		}

		public String getClasseCss() {
			return classeCss;
		}
	}

	public class Mensagem {

		private Tipo tipo;
		private String mensagem;

		public Mensagem(Tipo tipo, String mensagem) {
			super();
			this.tipo = tipo;
			this.mensagem = mensagem;
		}

		public String getMensagem() {
			return mensagem;
		}

		public Tipo getTipo() {
			return tipo;
		}

	}

}
