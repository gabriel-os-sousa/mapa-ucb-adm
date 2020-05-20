<%@page import="br.ucb.util.Strings"%>
<%@page import="br.ucb.model.Local"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

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

        <!-- Toast container -->
		<div style="position: absolute; right: 1rem;">
		    <!-- Toast -->
		    <div class="toast" id="toastBasic" role="alert" aria-live="assertive" aria-atomic="true" data-delay="5000">
		        <div class="toast-header">
		            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" 
		                 stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bell mr-2">
		            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
		            <path d="M13.73 21a2 2 0 0 1-3.46 0"></path></svg>
		            <strong class="mr-auto">Mensagem! </strong>
		            <button class="ml-2 mb-1 close" type="button" data-dismiss="toast" aria-label="Close">
		            	<span aria-hidden="true">×</span>
		            </button>
		        </div>
		        <div class="toast-body">Operação realizada com sucesso!</div>
		    </div>
		</div>
		<script>
		    window.onload = function() {
		 	  $("#toastBasic").toast("show");
		 	}
		</script>

        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">Lista de Locais</h1>
          
          <p class="mb-4">Listagem de locais cadastrados no Mapa da UCB</p>
          
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Locais</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Nome</th>
                      <th>Tipo</th>
                      <th>Descrição</th>
                      <th>Data Cadastro</th>
                      <th> </th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Nome</th>
                      <th>Tipo</th>
                      <th>Descrição</th>
                      <th>Data Cadastro</th>
                      <th> </th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <%
                      List<Local> locais = (List) request.getAttribute("attrLocais");
		              for (Local local : locais) {
                  %>
                    <tr>
                      <td><%= local.getNome() %></td>
                      <td><%= local.getTipo() %></td>
                      <td><%= local.getDescricao() %></td>
                      <td><%= Strings.dateHourToString(local.getDataCadastro()) %></td>
                      <td>
                          <a href="locais?cmd=inserir" class="btn btn-success btn-circle btn-sm" title="Adicionar">
                            <i class="fas fa-plus"></i>
                          </a>
                          <a href="locais?cmd=atualizar&id=<%=local.getId()%>" class="btn btn-success btn-circle btn-sm" title="Atualizar">
                            <i class="fas fa-edit"></i>
                          </a>
                          <a href="locais?cmd=excluir&id=<%=local.getId()%>" class="btn btn-danger btn-circle btn-sm" title="Excluir">
                            <i class="fas fa-trash"></i>
                          </a>
                      </td>
                    </tr>
                    <%
		              }
                    %>
                  </tbody>
                </table>
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
  
  <%@ include file="templates/footer-components.jsp" %>

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
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-auth.js"></script>
  
  <script type="text/javascript">
  
  //Configuração do App Firebase
  var firebaseConfig = {
    apiKey: "AIzaSyB9HfiV3qwuQS9CJ-i7PS_CwJgtY6t25V8",
    authDomain: "mapa-ucb.firebaseapp.com",
    databaseURL: "https://mapa-ucb.firebaseio.com",
    projectId: "mapa-ucb",
    storageBucket: "mapa-ucb.appspot.com",
    messagingSenderId: "157393312409",
    appId: "1:157393312409:web:56c0100b3f69693bc7b608",
    measurementId: "G-YN4P62W1K5"
  };
  
  // Inicializa o Firebase
  firebase.initializeApp(firebaseConfig);
    
  firebase.auth().onAuthStateChanged(function(user) {
	  if (user) {
	    //online
	    //Mudar nome na barra superior
	    /* var userNameTopbar = document.getElementById("userNameTopbar");
		userNameTopbar.innerHTML = user.displayName; */
	    //Colocar user na session
	    
	    console.log(user);
	    console.log( "Usuario logado: " + user.displayName +" Is annony: "+ user.isAnonymous);
	  } else {
	    //offline
	    console.log(user);
	    console.log("Usuario n logado" );
	  }
  });
  
  function doLogout() {
	  firebase.auth().signOut().then(function() {
		  //Seta os values do form hidden
	   	   document.getElementById("cmd").value = "doLogout";
	   	   document.getElementById("formLogout").submit();
	   	     
		  //sucess
		/* window.location.href = "/mapa-ucb-adm/login?cmd=doLogout"; */
	    console.log('Logout');
	  }, function(error) {
		//error
	    console.error( error );
	  });
  }
  </script>
  
</body>

</html>
