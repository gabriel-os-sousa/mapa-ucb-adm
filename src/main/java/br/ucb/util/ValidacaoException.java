package br.ucb.util;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> erros = new ArrayList<>();

	public void addErro(String erro) {
		erros.add(erro);
	}

	public List<String> getErros() {
		return erros;
	}

}
