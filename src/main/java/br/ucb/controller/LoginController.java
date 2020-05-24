package br.ucb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import br.ucb.model.Usuario;
import br.ucb.model.dao.UsuarioDAO;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "login";
		}
	
		try {
			RequestDispatcher rd = null;
			// Loggin 
			if (cmd.equalsIgnoreCase("doLogin")) {
				String idToken = request.getParameter("idToken");
				System.out.println("LOGIN"+"idToken: "+idToken);
				
				// Decodifica o token id
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
				
				//Recupera os dados do usuário
				UserRecord usuarioRecuperado = UsuarioDAO.getInstance().recuperarUsuario(decodedToken.getUid());
							
				System.out.println("Successfully fetched user data: " + usuarioRecuperado.getUid() + " "+ usuarioRecuperado.getDisplayName());
				
				// Cria um objeto Usuário com os dados recuperados do firebase
				Usuario usuarioLogado = new Usuario();
				usuarioLogado.setUserRecord(usuarioRecuperado); // Salva os dados de usuário firebase, que contém os custom claims

				// Ao charmar getSession(true), ele cria uma nova sessão
				HttpSession session = request.getSession(true);

				//Salva o usuarioLogado na sessão
				session.setAttribute("attrUsuarioLogado", usuarioLogado);

				rd = request.getRequestDispatcher("index.jsp");

			} else if (cmd.equalsIgnoreCase("doLogout")) {
				//remove o usuario logado da sesão para cair no webfilter e redirecionar para a página de login
				System.out.println("LOGOUT");
				
				HttpSession session = request.getSession(false);
				session.removeAttribute("attrUsuarioLogado");
				
				rd = request.getRequestDispatcher("login.jsp");
			} else if (cmd.equalsIgnoreCase("login")) {
				System.out.println(cmd);
				//Salva o usuarioLogado na sessão
				HttpSession session2 = request.getSession(false);
				
				if(session2.getAttribute("attrUsuarioLogado") != null) {
					rd = request.getRequestDispatcher("index.jsp");					
					System.out.println("login user ativo");
				} else {
					rd = request.getRequestDispatcher("login.jsp");
					System.out.println("login user null");
				}
			} else if (cmd.equalsIgnoreCase("resetPassword")) {
				// abre form reset senha
				rd = request.getRequestDispatcher("resetPassword.jsp");
			}
			
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
		}
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
