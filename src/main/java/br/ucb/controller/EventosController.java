package br.ucb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucb.model.Evento;
import br.ucb.model.Local;
import br.ucb.model.dao.EventoDAO;
import br.ucb.model.dao.LocalDAO;
import br.ucb.util.MensagemUtil;
import br.ucb.util.Strings;
import br.ucb.util.ValidacaoException;
import br.ucb.util.MensagemUtil.Tipo;

@WebServlet("/eventos")
public class EventosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");

		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "listar";
		}

		Evento evento = new Evento();
		EventoDAO dao = new EventoDAO();

		try {
			RequestDispatcher rd = null;
			List<Local> locais = LocalDAO.getInstance().getLocaisPersisted();
			request.setAttribute("attrLocais", locais);

			// listar todos os eventos
			if ("listar".equalsIgnoreCase(cmd)) {

				List<Evento> eventos = dao.obterEntidades("eventos");
				
				// Tratamento para associar o Evento ao Local
				for (Evento e : eventos) {
					int indexOf = locais.indexOf(new Local(e.getLocal()));
					if (indexOf >= 0) {
						Local localEntidade = locais.get(indexOf);
						e.setLocalEntidade(localEntidade);
					}
				}
				
				request.setAttribute("attrEventos", eventos);
				
				rd = request.getRequestDispatcher("eventos.jsp");

				// Prepara o formulário e o evento
			} else if (cmd.equalsIgnoreCase("inserir")) {
				evento = new Evento(dao.getIdFirebase(), null, null, null, "", "", "", "", null);
				request.setAttribute("attrEvento", evento);
				rd = request.getRequestDispatcher("eventoForm.jsp");

				// Excluir evento
			} else if (cmd.equalsIgnoreCase("excluir")) {
				evento.setId(request.getParameter("id"));
				dao.excluir(evento);
				addMensagem(request, Tipo.AVISO, "Evento excluído da base de dados.");
				/* rd = request.getRequestDispatcher("eventos?cmd=listar"); */
				response.sendRedirect("eventos");
				return;
				// Prepara o formulário e o evento
			} else if (cmd.equalsIgnoreCase("atualizar")) {
				String id = request.getParameter("id");
				evento = dao.obterEvento(id);
				request.setAttribute("attrEvento", evento);
				rd = request.getRequestDispatcher("eventoForm.jsp");

				// Realiza a persistência do evento
			} else if (cmd.equalsIgnoreCase("doSalvar")) {
				
				try {
					evento = getFromRequest(request);
					dao.salvar(evento);
					addMensagem(request, Tipo.SUCESSO, "Operação realizada com sucesso.");
					response.sendRedirect("eventos");
					return;
					
				} catch (ValidacaoException e) {
					for (String erro : e.getErros()) {
						addMensagem(request, Tipo.ERRO, erro);
					}
					//setar o evento na request para preencher os dados
					request.setAttribute("attrEvento", evento);
					rd = request.getRequestDispatcher("eventoForm.jsp");
				}
			}
			
			rd.forward(request, response);

		} catch (Exception erro) {
			erro.printStackTrace();
			try {
				addMensagem(request, Tipo.ERRO, erro.getMessage()+" - "+erro.getCause());
				response.sendRedirect("erro.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Evento getFromRequest(HttpServletRequest request) {
		Evento evento = new Evento();
		evento.setId(request.getParameter("id"));
		evento.setTipo(request.getParameter("tipo"));
		evento.setNome(request.getParameter("nome"));
		evento.setDescricao(request.getParameter("descricao"));
		
		if (Strings.isNotNull(request.getParameter("zIndex"))) {
			evento.setzIndex(Integer.parseInt(request.getParameter("zIndex")));
		}
		
		if (Strings.isNotNull(request.getParameter("data_inicio"))) {
			//fazer o tratamento da data
	        evento.setData_inicio(Strings.stringToMillis(request.getParameter("data_inicio"), "00:00"));
		}
		
		if (Strings.isNotNull(request.getParameter("data_fim"))) {
			//fazer o tratamento da data
	        evento.setData_fim(Strings.stringToMillis(request.getParameter("data_fim"), "23:59"));
		}
		
		if (Strings.isNotNull(request.getParameter("dataCadastro"))) {
			evento.setDataCadastro(Long.parseLong(request.getParameter("dataCadastro")));
		}
		
		if (!"-1".equals(request.getParameter("local"))) {
			evento.setLocal(request.getParameter("local"));
		}
		
		return evento;
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
