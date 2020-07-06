package br.ucb.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucb.model.Local;
import br.ucb.model.dao.LocalDAO;
import br.ucb.util.MensagemUtil;
import br.ucb.util.MensagemUtil.Tipo;
import br.ucb.util.Strings;
import br.ucb.util.ValidacaoException;

@WebServlet("/locais")
public class LocaisController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");

		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "listar";
		}

		Local local = new Local();
		
		try {
			RequestDispatcher rd = null;

			// listar todos os locais
			if (cmd.equalsIgnoreCase("listar")) {
				
				List<Local> locais = LocalDAO.getInstance().obterLocais();
				Collections.sort(locais);
				request.setAttribute("attrLocais", locais);
				rd = request.getRequestDispatcher("locais.jsp");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("inserir")) {
				local = new Local("", "", "", "", null, null, null, null);
				local.setId(LocalDAO.getInstance().getIdFirebase());
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Excluir local
			} else if (cmd.equalsIgnoreCase("excluir")) {
				
				try {
					local.setId(request.getParameter("id"));
					LocalDAO.getInstance().excluir(local);
					addMensagem(request, Tipo.AVISO, "Local excluído da base de dados.");
					response.sendRedirect("locais");
					return;
				} catch (ValidacaoException e) {
					for (String erro : e.getErros()) {
						addMensagem(request, Tipo.ERRO, erro);
					}
					response.sendRedirect("locais");
					return;
				}
				
				
//				local.setId(request.getParameter("id"));
//				LocalDAO.getInstance().excluir(local);
//				addMensagem(request, Tipo.AVISO, "Local excluído da base de dados.");
//				response.sendRedirect("locais");
//				return;

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				String id = request.getParameter("id");
				local = LocalDAO.getInstance().obterLocal(id);
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Realiza a persistencia do local
			} else if (cmd.equalsIgnoreCase("doSalvar")) {
				
				try {
					local = getFromRequest(request);
					LocalDAO.getInstance().salvar(local);
					addMensagem(request, Tipo.SUCESSO, "Operação realizada com sucesso.");
					response.sendRedirect("locais");
					return;
					
				} catch (ValidacaoException e) {
					for (String erro : e.getErros()) {
						addMensagem(request, Tipo.ERRO, erro);
					}
					request.setAttribute("attrLocal", local);
					rd = request.getRequestDispatcher("localForm.jsp");
				}
			}

			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
			try {
				addMensagem(request, Tipo.ERRO, erro.getMessage());
				response.sendRedirect("erro.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Local getFromRequest(HttpServletRequest request) {
		Local local = new Local();

		local.setId(request.getParameter("id"));
		local.setTipo(request.getParameter("tipo"));
		local.setNome(request.getParameter("nome"));
		local.setDescricao(request.getParameter("descricao"));

		if (Strings.isNotNull(request.getParameter("zIndex"))) {
			local.setzIndex(Integer.parseInt(request.getParameter("zIndex")));
		}

		if (Strings.isNotNull(request.getParameter("andar"))) {
			local.setAndar(Integer.parseInt(request.getParameter("andar")));
		}

		if (Strings.isNotNull(request.getParameter("dataCadastro"))) {
			local.setDataCadastro(Long.parseLong(request.getParameter("dataCadastro")));
		}

		if (Strings.isNotNull(request.getParameter("latitude"))) {
			local.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		}

		if (Strings.isNotNull(request.getParameter("longitude"))) {
			local.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		}

		return local;
	}

	private void addMensagem(HttpServletRequest request, Tipo tipo, String mensagem) {
		
		if (request.getSession().getAttribute("attrMensagensSession") == null) {
			MensagemUtil mu = new MensagemUtil();
			request.getSession().setAttribute("attrMensagensSession", mu);
		}
		
		MensagemUtil mensagens = (MensagemUtil) request.getSession().getAttribute("attrMensagensSession");
		mensagens.addMensagem(tipo, mensagem);
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
