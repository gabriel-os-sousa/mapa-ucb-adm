package br.ucb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucb.model.Usuario;
import br.ucb.model.dao.UsuarioDAO;
import br.ucb.util.Strings;

@WebServlet("/usuarios")
public class UsuariosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "listar";
		}

		Usuario usuario = new Usuario();
		
		try {
			RequestDispatcher rd = null;

			// listar todos os locais
			if (cmd.equalsIgnoreCase("listar")) {
				List<Usuario> usuarios = UsuarioDAO.getInstance().obterUsuarios();
				request.setAttribute("attrUsuarios", usuarios);
				rd = request.getRequestDispatcher("usuarios.jsp");

				// Prepara o formulário e o usuario
			} else if (cmd.equalsIgnoreCase("inserir")) {
				//Inserir um cara vazio e retornar
				usuario = new Usuario();
				request.setAttribute("attrUsuario", usuario);
				rd = request.getRequestDispatcher("usuarioForm.jsp");

				// Excluir usuario
			} else if (cmd.equalsIgnoreCase("excluir")) {
				String id = request.getParameter("id");
				UsuarioDAO.getInstance().excluir(id);
				rd = request.getRequestDispatcher("usuarios?cmd=listar");

				// Prepara o formulário e o usuario
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				usuario = new Usuario();
				String id = request.getParameter("id");
				
				usuario.setUserRecord(UsuarioDAO.getInstance().recuperarUsuario(id));
				request.setAttribute("attrUsuario", usuario);
				
				rd = request.getRequestDispatcher("usuarioForm.jsp");
				
				// Realiza a persistencia do usuario
			} else if (cmd.equalsIgnoreCase("doSalvar")) {
				usuario = getFromRequest(request);
				
				System.out.println("doSalvar: "+usuario.getId());
				UsuarioDAO.getInstance().salvar(usuario);
				
				rd = request.getRequestDispatcher("usuarios?cmd=listar");
			} 
			
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
		}
		
	}
	
	private Usuario getFromRequest(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		usuario.setEmail(request.getParameter("email"));
		usuario.setNome(request.getParameter("displayName"));
		usuario.setTipo(request.getParameter("tipo"));
		
		String id = request.getParameter("id");
		
		if (Strings.isNotEmpty(id) || Strings.isNotNull(id)) 
			usuario.setId(id);
		
		
		return usuario;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

}
