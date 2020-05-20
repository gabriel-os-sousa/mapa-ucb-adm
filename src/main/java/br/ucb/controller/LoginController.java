package br.ucb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.internal.FirebaseCustomAuthToken;

import br.ucb.model.Usuario;
import br.ucb.util.ConfiguracaoFirebase;

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
			if (cmd.equalsIgnoreCase("doAutenticar")) {
				System.out.println(cmd);
				String idToken = request.getParameter("idToken");
				System.out.println("idToken: "+idToken);
				
				// Configura o SDK de authentication firebase
				FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
				
				// Decodifica o token id
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
				
				//Recupera os dados do usuário
				UserRecord usuarioRecuperado = auth.getUser(decodedToken.getUid());
				System.out.println("Successfully fetched user data: " + usuarioRecuperado.getUid() + " "+ usuarioRecuperado.getDisplayName());
				
				// Cria um objeto Usuário com os dados recuperados do firebase
				Usuario usuarioLogado = new Usuario();
				usuarioLogado.setId(usuarioRecuperado.getUid());
				usuarioLogado.setNome(usuarioRecuperado.getDisplayName());
				usuarioLogado.setEmail(usuarioRecuperado.getEmail());

				// Ao charmar getSession(true), ele cria uma nova sessão
				HttpSession session = request.getSession(true);
				
				//Salva o usuarioLogado na sessão
				session.setAttribute("attrUsuarioLogado", usuarioLogado);
				
				rd = request.getRequestDispatcher("index.jsp");

				// Resetar Senha - Abre o form de reset de senha
			} else if (cmd.equalsIgnoreCase("doLogout")) {
				//remove o usuario logado da sesão para cair no webfilter e redirecionar para a página de login
				System.out.println(cmd);
				String idToken = request.getParameter("idToken");
				System.out.println("idToken: "+idToken);
				HttpSession session = request.getSession(false);
								
				session.removeAttribute("attrUsuarioLogado");
				//coloquei uma pagina diferente para ver se o filter está funcionando
				
				rd = request.getRequestDispatcher("login.jsp");
			} else if (cmd.equalsIgnoreCase("login")) {
				System.out.println(cmd);
				rd = request.getRequestDispatcher("login.jsp");
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
