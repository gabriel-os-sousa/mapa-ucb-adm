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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// Ao charmar getSession(false), recupera a sessão sem ter que criar uma nova
        HttpSession session = httpRequest.getSession(false);
 
        boolean isLoggedIn = (session != null && session.getAttribute("attrUsuarioLogado") != null); 
 
        String loginURI = httpRequest.getContextPath() + "/login";
 
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
 
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");
        boolean isCss = httpRequest.getRequestURI().matches(".*(css|jpg|png|gif|js)");
 
//        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
//            // the admin is already logged in and he's trying to login again
//            // then forwards to the admin's homepage
//
//        	// O usuario já está logado e está tentando logar novamente
//            // então manda o usuario para página inicial
//        	
//        	RequestDispatcher dispatcher = request.getRequestDispatcher("/");
//            dispatcher.forward(request, response);
// 
//        } else 
        if (isLoggedIn || isLoginRequest || isCss) {
            // continues the filter chain
            // allows the request to reach the destination
        	
        	// continua a cadeia do filtro
        	// permite que a solicitação chegue ao destino
            chain.doFilter(request, response);
 
        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
        	
        	// o usuario não está logado, então a autenticacao é requerida
        	// é encaminhado para a página de login
        	System.out.println("required");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}
