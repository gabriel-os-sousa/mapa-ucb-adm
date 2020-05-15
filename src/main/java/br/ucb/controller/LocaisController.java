package br.ucb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucb.model.Local;
import br.ucb.model.dao.LocalDAO;

@WebServlet("/locais")
public class LocaisController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "listar";
		}

		Local local = new Local();
		LocalDAO dao = new LocalDAO();

		try {
			RequestDispatcher rd = null;

			// listar todos os locais
			if (cmd.equalsIgnoreCase("listar")) {
				
				List<Local> locais = dao.obterLocais();
				request.setAttribute("attrLocais", locais);
				rd = request.getRequestDispatcher("locais.jsp");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("inserir")) {
				local = new Local("", "", "", "", 0, 0);
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// adicionar local
			} else if (cmd.equalsIgnoreCase("doInserir")) {
				dao.salvar(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");

				// Excluir local
			} else if (cmd.equalsIgnoreCase("excluir")) {
//				dao.excluir(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				String id = request.getParameter("id");
				local = dao.obterLocal(id);
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Realiza a atualização do local
			} else if (cmd.equalsIgnoreCase("doAtualizar")) {
				local = getFromRequest(request);
				dao.atualizar(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");
			}
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
			throw new ServletException(erro);
		}
	}

	private Local getFromRequest(HttpServletRequest request) {
		Local local = new Local();
		local.setId(request.getParameter("id"));
		local.setTipo(request.getParameter("tipo"));
		local.setNome(request.getParameter("tipo"));
		local.setDescricao(request.getParameter("descricao"));
		return local;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		processRequest(request, response);
	}

}
