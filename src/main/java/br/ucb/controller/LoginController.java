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
			cmd = "doAutenticar";
		}

				
		try {
			RequestDispatcher rd = null;

			// Loggin 
			if (cmd.equalsIgnoreCase("doAutenticar")) {
				//Recuperar os campos da requisição
				String usuario = request.getParameter("usuario");
				String senha = request.getParameter("senha");
				
				FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
				
				// Ao charmar getSession(true), ele cria uma nova sessão
				HttpSession session = request.getSession(true);
				
				//Salva o usuarioLogado na sessão
				Usuario usuarioLogado = new Usuario();
				session.setAttribute("usuarioLogado", usuarioLogado);
				
				rd = request.getRequestDispatcher("index.jsp");

				// Resetar Senha
			} else if (cmd.equalsIgnoreCase("resetPassword")) {
				rd = request.getRequestDispatcher("resetPassword.jsp");
				
			} else if (cmd.equalsIgnoreCase("doResetPassword")) {
				//Recuperar os campos da requisição
				//String email = request.getParameter("email");
				String email = "gabriiel.dfx@gmail.com";
				FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
			
				ActionCodeSettings settings = ActionCodeSettings.builder().;
				auth.generateSignInWithEmailLink(email, settings);

					try {
						auth.generatePasswordResetLink(email);
					} catch (FirebaseAuthException e) {
						System.out.println("Error generating email link: " + e.getMessage());
					}
								
				rd = request.getRequestDispatcher("index.jsp");
			} 
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		System.out.println();
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
