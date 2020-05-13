package br.ucb.util;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {
	
	private static final Logger log = Logger.getLogger(Initializer.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Inicializando APP ... " + sce.getServletContext().getContextPath());
//		log.info("Inicializando banco ... " + ConfiguracaoFirebase.getFirebaseDatabase());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Encerrando... " + sce.getServletContext().getContextPath());
	}

}
