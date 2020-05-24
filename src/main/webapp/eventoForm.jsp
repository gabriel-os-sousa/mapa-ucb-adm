<%@page import="br.ucb.util.Strings"%>
<%@page import="br.ucb.model.Evento"%>
<%@page import="br.ucb.model.Local"%>
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
  
  <link rel="stylesheet" type="text/css" href="css/geral.css">

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
          <h1 class="h3 mb-4 text-gray-800">Cadastro de Evento</h1>
          <% Evento evento = (Evento) request.getAttribute("attrEvento"); %>
          
          <!-- Basic Card Example -->
          <div class="card shadow">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary"><%= evento.isPersistido() ? "Atualizar" : "Inserir" %></h6>
            </div>
            <div class="card-body">
            
              <form id="formEvento" action="eventos" method="post">
                <input type="hidden" id="idLocal" name="id" value="<%= evento.getId() %>">
                <input type="hidden" name="dataCadastro" value="<%= evento.getDataCadastro() %>">
                
                <div class="form-group">
                  <label for="tipo">Tipo</label>
                  <input class="form-control" id="tipo" name="tipo" type="text" value="<%= evento.getTipo() %>">
                </div>
                
                <div class="form-group">
                  <label for="nome">Nome</label>
                  <input class="form-control" id="nome" name="nome" type="text" value="<%= evento.getNome() %>">
                </div>
                
                <div class="form-group">
                  <label for="descricao">Descrição</label>
                  <input class="form-control" id="descricao" name="descricao" type="text" value="<%= evento.getDescricao() %>">
                </div>
                
                <div class="form-group">
                  <label for="zIndex">zIndex</label>
                  <input class="form-control" id="zIndex" name="zIndex" type="number" min="0" max="1000" value="<%= evento.getzIndex() == null ? "0" : evento.getzIndex() %>">
                </div>
                
                <div class="form-group">
                  <label for="data_inicio">Data Inicio</label>
                  <input type="date" id="data_inicio" name="data_inicio" value="<%= evento.getData_inicio() != null ? Strings.millisToString(evento.getData_inicio()) : "" %>">
                </div>
                
                <div class="form-group">
                  <label for="data_fim">Data Fim</label>
                  <input type="date" id="data_fim" name="data_fim" value="<%= evento.getData_fim() != null ? Strings.millisToString(evento.getData_fim()) : "" %>">
                </div>
                
                <div class="form-group">
                   <label for="local">Local</label>
                   <select class="form-control form-control-solid" id="local" name="local">
                   		  <option value="-1">Selecione</option>	
                   		<% List<Local> locais = (List) request.getAttribute("attrLocais");
                		   for (Local local : locais) {
                		 %>
                           <option value="<%= local.getId() %>" <%= local.getId().equals(evento.getLocal()) ? "selected" : "" %> ><%= local.getTipoNome() %></option>
                		 <% } %>
                   </select>
                </div>
                
              </form>
              
        	  <div style="margin-top: 20px;">
    	          <button class="btn btn-outline-primary" type="submit" formaction="eventos?cmd=doSalvar" form="formEvento">Salvar</button>
	        	  <button class="btn btn-outline-danger" type="submit" form="formEvento">Sair</button>
        	  </div>
          
            </div>
          </div>

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
