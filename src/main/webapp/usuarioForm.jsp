<%@page import="br.ucb.model.Usuario"%>
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
          <% Usuario usuario = (Usuario) request.getAttribute("attrUsuario"); %>
          <h1 class="h3 mb-4 text-gray-800">Usuários</h1>
          
          <!-- Basic Card Example -->
          <div class="card shadow">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary"><%= usuario.isPersisted() ? "Atualizar" : "Inserir" %></h6>
            </div>
            <div class="card-body">
            
              <form id="formUsuario" action="usuarios" method="post">
                <input type="hidden" id="idUsuario" name="id" value="<%= usuario.isPersisted() ? usuario.getUserRecord().getUid() : "" %>">
                <%-- <input type="hidden" id="latitude"  name="latitude" type="text" value="<%= local.getLatitude() %>">
	            <input type="hidden" id="longitude" name="longitude" type="text" value="<%= local.getLongitude() %>"> --%>
                
                <div class="form-group">
                  <label for="tipo">Nome de exibição</label>
                  <input class="form-control" id="tipo" name="displayName" type="text" value="<%= usuario.isPersisted() ? usuario.getUserRecord().getDisplayName() : "" %>">
                </div>
                
                
                <div class="form-group">
                  <label for="tipo">Email</label>
                  <input class="form-control" id="email" name="email" type="text" value="<%= usuario.isPersisted() ? usuario.getUserRecord().getEmail() : ""%>">
                </div>
                
		        <div class="form-group">
		          <label for="descricao">Tipo</label>
                  <input class="form-control" id="tipo" name="tipo" type="text" value="<%= usuario.isPersisted() ? usuario.getUserRecord().getCustomClaims().get("admin") : "" %>">
		        </div>
              </form>
              
        	  
        	  <div style="margin-top: 20px;">
    	          <button class="btn btn-outline-primary" type="submit" formaction="usuarios?cmd=doSalvar" form="formUsuario">Salvar</button>
	        	  <button class="btn btn-outline-danger" type="submit" form="formUsuario">Sair</button>
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
  
  <!-- TODO: Add SDKs for Firebase products that you want to use https://firebase.google.com/docs/web/setup#available-libraries -->
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-analytics.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-auth.js"></script>
  
  <!-- Métodos firebase -->
  <script src="js/firebase-metodos.js"></script>
</body>

</html>
