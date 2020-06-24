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
import br.ucb.util.MensagemUtil;
import br.ucb.util.Strings;
import br.ucb.util.ValidacaoException;
import br.ucb.util.MensagemUtil.Tipo;

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
				// Inserir um cara vazio e retornar
				usuario = new Usuario();
				request.setAttribute("attrUsuario", usuario);
				rd = request.getRequestDispatcher("usuarioForm.jsp");

				// Excluir usuario
			} else if (cmd.equalsIgnoreCase("excluir")) {
				String id = request.getParameter("id");
				UsuarioDAO.getInstance().excluir(id);
				addMensagem(request, Tipo.AVISO, "Usuário excluído da base de dados.");
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
				try {
					usuario = getFromRequest(request);
					UsuarioDAO.getInstance().salvar(usuario);
					addMensagem(request, Tipo.SUCESSO, "Operação realizada com sucesso.");
					response.sendRedirect("usuarios");
					//rd = request.getRequestDispatcher("usuarios?cmd=listar");
					return;
				} catch (ValidacaoException e) {
					for (String erro : e.getErros()) {
						addMensagem(request, Tipo.ERRO, erro);
					}
					request.setAttribute("attrUsuario", usuario);
					rd = request.getRequestDispatcher("usuarioForm.jsp");
				}
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
		usuario.setSenha(request.getParameter("password"));

		String id = request.getParameter("id");

		if (Strings.isNotNull(id)) {
			usuario.setId(id);
		}

		return usuario;
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
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

}
