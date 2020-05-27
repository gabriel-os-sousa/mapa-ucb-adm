<%@page import="br.ucb.util.Strings"%>
<%@page import="br.ucb.model.Evento"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="Gabriel Sousa et al..">

  <title>Mapa UCB - ADM</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
	
	<%@ include file="templates/sidebar.jsp" %>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <%@ include file="templates/topbar.jsp" %>

        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">Eventos</a>
          	<a href="eventos?cmd=inserir" class="btn btn-success btn-circle btn-sm" title="Adicionar">
	            <i class="fas fa-plus"></i>
	          </a>
          </h1>

          <% 
          	List<Evento> eventos = (List) request.getAttribute("attrEventos");
          	for (Evento evento : eventos) {
          %>
          
           <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary"><%= evento.getNome() %></h6>
                  <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink" x-placement="bottom-end" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(-156px, 19px, 0px);">
                      <div class="dropdown-header">Opções:</div>
                      <a class="dropdown-item" href="eventos?cmd=atualizar&id=<%= evento.getId() %>">Editar</a>
                      <div class="dropdown-divider"></div>
                      <a class="dropdown-item" href="eventos?cmd=excluir&id=<%= evento.getId() %>">Excluir</a>
                    </div>
                  </div>
                </div>
                <!-- Card Body -->
                <div class="card-body" style="padding: 0.75rem;">
                  <p><span><b>Tipo:</b> <%= evento.getTipo() %></span><span> - <b>Local:</b> <%= evento.getTipoNomeLocal() %></span><span> - <b>Data de cadastro: </b><%= Strings.dateHourToString(evento.getDataCadastro()) %></span></p>
                  <p><span><b>Início:</b> <%= Strings.dateToString(evento.getData_inicio()) %></span><span> - <b>Fim:</b> <%= Strings.dateToString(evento.getData_fim()) %></span></p>
                  <p><b>Descrição:</b> <%= evento.getDescricao() %></p>
                </div>
              </div>
          
          <% } %>
        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <%@ include file="templates/footer.jsp" %>

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->
  
  <!-- Form logout -->
	<form style="display: none" action="login" method="POST" id="formLogout">
	  <input type="hidden" id="cmd" name="cmd" value=""/>
	</form>
  
  <%@ include file="templates/components.jsp" %>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>
  
  <!-- The core Firebase JS SDK is always required and must be listed first -->
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-app.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-auth.js"></script>
  
  <!-- Métodos firebase -->
  <script src="js/firebase-metodos.js"></script>
</body>

</html>
