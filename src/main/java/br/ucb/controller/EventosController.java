package br.ucb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucb.model.Evento;
import br.ucb.model.dao.EventoDAO;

@WebServlet("/eventos")
public class EventosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "listar";
		}

		Evento evento = new Evento();
		EventoDAO dao = new EventoDAO();

		try {
			RequestDispatcher rd = null;

			// listar todos os eventos
			if ("listar".equalsIgnoreCase(cmd)) {
				request.setAttribute("attrEventos", dao.obterEntidades("eventos"));
				rd = request.getRequestDispatcher("eventos.jsp");

				// Prepara o formulário e o evento
			} else if (cmd.equalsIgnoreCase("inserir")) {
				request.setAttribute("evento", evento);
				rd = request.getRequestDispatcher("eventoForm.jsp");

				// adicionar evento
			} else if (cmd.equalsIgnoreCase("doInserir")) {
				dao.salvar(evento);
				rd = request.getRequestDispatcher("eventos?cmd=listar");

				// Excluir evento
			} else if (cmd.equalsIgnoreCase("excluir")) {
				dao.excluir(evento);
				rd = request.getRequestDispatcher("eventos?cmd=listar");

				// Prepara o formulário e o evento
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				evento = dao.obterEvento(evento.getId());
				request.setAttribute("evento", evento);
				rd = request.getRequestDispatcher("eventoForm.jsp");

				// Realiza a atualização do evento
			} else if (cmd.equalsIgnoreCase("doSalvar")) {
				dao.salvar(evento);
				rd = request.getRequestDispatcher("eventos?cmd=listar");
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
