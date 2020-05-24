package br.ucb.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.ucb.model.Usuario;


/**
 * This Java filter demonstrates how to intercept the request
 * and transform the response to implement authentication feature.
 * for the website's back-end.
 *
 * https://www.codejava.net/java-ee/servlet/how-to-implement-authentication-filter-for-java-web-application
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		request.setCharacterEncoding("UTF-8");
		
		// Ao charmar getSession(false), recupera a sessão sem ter que criar uma nova
        HttpSession session = httpRequest.getSession(false);
       
        boolean isLoggedIn = (session != null && session.getAttribute("attrUsuarioLogado") != null); 

        Usuario user = null;
 
        String loginURI = httpRequest.getContextPath() + "/login";
 
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isCss = httpRequest.getRequestURI().matches(".*(css|jpg|png|gif|js)");
        boolean isUsersPage = httpRequest.getRequestURI().contains("usuario");

        //boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");
//        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
//            // the admin is already logged in and he's trying to login again
//            // then forwards to the admin's homepage
//
//        	// O usuario já está logado e está tentando logar novamente
//            // então manda o usuario para página inicial
//        	
//        	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//            dispatcher.forward(request, response);
// 
//        } else 
        if (isLoggedIn || isLoginRequest || isCss) {
        	
        	// Se estiver logado, salva o atributo da sessão no objeto
        	if (isLoggedIn) {
        		user = (Usuario) session.getAttribute("attrUsuarioLogado");				
			}
        	if (isLoggedIn && user.getUserRecord().getCustomClaims().get("admin").equals(false) && isUsersPage) {
        		// O usuário está tentando acessar página de usuários mas não tem permissao
        		// Redireciona o usuário que estiver logado e não for admin para a tela principal
        		System.out.println("Filter Auth"+user.getUserRecord().getDisplayName());
        		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            } else {
            	// continua a cadeia do filtro, permite que a solicitação chegue ao destino
            	chain.doFilter(request, response); 
            }
			
        } else {
        	// o usuario não está logado, então a autenticacao é requerida
        	// é encaminhado para a página de login
        	System.out.println("login required");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}


}
