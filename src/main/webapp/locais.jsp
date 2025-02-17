<%@page import="br.ucb.util.MensagemUtil.Mensagem"%>
<%@page import="br.ucb.util.MensagemUtil"%>
<%@page import="br.ucb.util.Strings"%>
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
          <h1 class="h3 mb-4 text-gray-800">Locais
	          <a href="locais?cmd=inserir" class="btn btn-success btn-circle btn-sm" title="Adicionar">
	            <i class="fas fa-plus"></i>
	          </a>
          </h1>
          
          
          <p class="mb-4">Listagem de locais cadastrados no Mapa da UCB</p>
          
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary" style="display: inline">Locais</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Nome</th>
                      <th>Tipo</th>
                      <th>Descrição</th>
                      <th>zIndex</th>
                      <th>Andar</th>
                      <th>ID</th>
                      <th>Data Cadastro</th>
                      <th>Ações</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Nome</th>
                      <th>Tipo</th>
                      <th>Descrição</th>
                      <th>zIndex</th>
                      <th>Andar</th>
                      <th>ID</th>
                      <th>Data Cadastro</th>
                      <th>Ações</th>
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
                      <td><%= local.getzIndex() == null ? "0" : local.getzIndex()  %></td>
                      <td><%= local.getAndar() == null ? "0" : local.getAndar() %></td>
                      <td><%= local.getId() %></td>
                      <td><%= Strings.dateHourToString(local.getDataCadastro()) %></td>
                      <td>
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
