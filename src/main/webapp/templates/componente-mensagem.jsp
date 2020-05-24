<%@page import="br.ucb.util.MensagemUtil.Mensagem"%>
<%@page import="br.ucb.util.MensagemUtil"%>
<%@page import="br.ucb.model.Usuario"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
	if (request.getSession().getAttribute("attrMensagensSession") != null) {
		MensagemUtil mensagens = (MensagemUtil) request.getSession().getAttribute("attrMensagensSession");
		if (mensagens.isPossuiMensagem()) {
			for (Mensagem m : mensagens.getMensagens()) {
%>
<div
	class="alert alert-<%=m.getTipo().getClasseCss() %> alert-dismissible fade show mb-0"
	style="margin: 2rem !important;" role="alert">
	<%=  m.getMensagem()  %>
	<button class="close" type="button" data-dismiss="alert"
		aria-label="Close">
		<span aria-hidden="true">×</span>
	</button>
</div>
<% }
 }
}

request.getSession().removeAttribute("attrMensagensSession");
%>