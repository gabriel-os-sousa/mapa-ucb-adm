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
import br.ucb.util.Strings;

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
				request.setAttribute("attrLocais", locais);
				
				rd = request.getRequestDispatcher("locais.jsp");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("inserir")) {
				local = new Local("", "", "", "", 0, 0, 0, 0);
				local.setId(LocalDAO.getInstance().getIdFirebase());
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Excluir local
			} else if (cmd.equalsIgnoreCase("excluir")) {
				local.setId(request.getParameter("id"));
				LocalDAO.getInstance().excluir(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");

				// Prepara o formulário e o local
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				String id = request.getParameter("id");
				local = LocalDAO.getInstance().obterLocal(id);
				request.setAttribute("attrLocal", local);
				rd = request.getRequestDispatcher("localForm.jsp");

				// Realiza a persistencia do local
			} else if (cmd.equalsIgnoreCase("doSalvar")) {
				local = getFromRequest(request);
				LocalDAO.getInstance().salvar(local);
				rd = request.getRequestDispatcher("locais?cmd=listar");
			}
			
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
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
