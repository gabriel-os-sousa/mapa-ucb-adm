package br.ucb.controller;

import java.io.IOException;
import java.util.ArrayList;
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
				List<Local> locais = new ArrayList<>();
				request.setAttribute("attrLocais", locais);
				rd = request.getRequestDispatcher("locais.jsp");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("inserir")) {
				request.setAttribute("local", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// adicionar local
			} else if (cmd.equalsIgnoreCase("doInserir")) {
				dao.salvar(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");

				// Excluir local
			} else if (cmd.equalsIgnoreCase("excluir")) {
				dao.excluir(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				local = dao.obterLocal(local.getId());
				request.setAttribute("local", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Realiza a atualização do local
			} else if (cmd.equalsIgnoreCase("doAtualizar")) {
				dao.atualizar(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");
			}
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
			throw new ServletException(erro);
		}
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
