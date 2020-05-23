<%@page import="br.ucb.model.Usuario"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%  
String url = request.getRequestURI();
url = !url.endsWith("jsp") ? "index.jsp" : url;

boolean isEventoMenuAtivo = url.toUpperCase().contains("EVENTO");
boolean isLocalMenuAtivo = url.toUpperCase().contains("LOCA");
boolean isHome = url.toUpperCase().contains("INDEX");

// verifica se é admin
Usuario usuarioLogado = (Usuario) session.getAttribute("attrUsuarioLogado");
boolean isAdmin = usuarioLogado.isAdmin();
boolean isUsuarioMenuAtivo = url.toUpperCase().contains("USUARIO");
%>

<!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.jsp">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-map-marked-alt"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Mapa UCB</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item <%= isHome ? "active" : ""%>">
        <a class="nav-link" href="index.jsp">
          <i class="fas fa-fw fa-home"></i>
          <span>Home</span></a>
      </li>
      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Menu
      </div>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
          <i class="fas fa-fw fa-folder"></i>
          <span>Opções</span>
        </a>
        <div id="collapsePages" class="collapse <%= !isHome ? "show" : ""%>" aria-labelledby="headingPages" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Administrativo:</h6>
            <a class="collapse-item <%= isEventoMenuAtivo ? "active" : ""%>" href="eventos">Eventos</a>
            <a class="collapse-item <%= isLocalMenuAtivo ? "active" : ""%>" href="locais">Locais</a>
            <% if(isAdmin){ %>
            	<a class="collapse-item <%= isUsuarioMenuAtivo ? "active" : ""%>" href="usuarios">Usuários</a>
			<% }%>
            <div class="collapse-divider"></div>
            
            <h6 class="collapse-header">-----</h6>
            <a class="collapse-item <%= url.toUpperCase().contains("SOBRE.JSP") ? "active" : ""%>" href="sobre.jsp">Sobre</a>
          </div>
        </div>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->