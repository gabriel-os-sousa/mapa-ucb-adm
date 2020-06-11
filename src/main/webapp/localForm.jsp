<%@page import="br.ucb.model.Local"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>

  <meta charset="UTF-8">
  <meta http-equiv="Content-Type"  content="text/html; charset=UTF-8">
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
          <h1 class="h3 mb-4 text-gray-800">Cadastro de Local</h1>
          <% Local local = (Local) request.getAttribute("attrLocal"); %>
          
          <!-- Basic Card Example -->
          <div class="card shadow">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary"><%= local.isPersistido() ? "Atualizar" : "Inserir" %></h6>
            </div>
            <div class="card-body">
            
              <form id="formLocal" action="locais" method="POST">
                <input type="hidden" id="idLocal" name="id" value="<%= local.getId() %>">
                <input type="hidden" name="dataCadastro" value="<%= local.getDataCadastro() %>">
                <input type="hidden" id="latitude"  name="latitude" type="text" value="<%= local.getLatitude() %>">
	            <input type="hidden" id="longitude" name="longitude" type="text" value="<%= local.getLongitude() %>">
                
                <div class="form-group">
                  <label for="tipo">Nome</label>
                  <input class="form-control" id="nome" name="nome" type="text" value="<%= local.getNome() %>">
                </div>
                
                <div class="form-group">
                   <label for="tipo">Tipo</label>
                   <select class="form-control form-control-solid" id="tipo" name="tipo">
                   		 <% if(local.getTipo().equalsIgnoreCase("")) {
                   		 		out.print("<option value='' selected>Selecione o tipo</option>");
                   			}
                   		 %>
                   		 <option value="atendimento" <%= local.getTipo().equalsIgnoreCase("atendimento") ? "selected" : "" %>>atendimento</option>
                   		 <option value="auditorio" <%= local.getTipo().equalsIgnoreCase("auditorio") ? "selected" : "" %>>auditorio</option>
                   		 <option value="banheiro" <%= local.getTipo().equalsIgnoreCase("banheiro") ? "selected" : "" %>>banheiro</option>
                   		 <option value="biblioteca" <%= local.getTipo().equalsIgnoreCase("biblioteca") ? "selected" : "" %>>biblioteca</option>
                   		 <option value="bloco" <%= local.getTipo().equalsIgnoreCase("bloco") ? "selected" : "" %>>bloco</option>
                   		 <option value="esporte" <%= local.getTipo().equalsIgnoreCase("esporte") ? "selected" : "" %>>esporte</option>
                   		 <option value="estacionamento" <%= local.getTipo().equalsIgnoreCase("estacionamento") ? "selected" : "" %>>estacionamento</option>
                   		 <option value="laboratorio" <%= local.getTipo().equalsIgnoreCase("laboratorio") ? "selected" : "" %>>laboratorio</option>
                   		 <option value="refeicao" <%= local.getTipo().equalsIgnoreCase("refeicao") ? "selected" : "" %>>refeicao</option>
                   		 <option value="sala" <%= local.getTipo().equalsIgnoreCase("sala") ? "selected" : "" %>>sala</option>
                   		 <option value="token_estacionamento" <%= local.getTipo().equalsIgnoreCase("token_estacionamento") ? "selected" : "" %>>token_estacionamento</option>
                   </select>
                </div>

                <%-- <div class="form-group">
                  <label for="tipo">Tipo</label>
                  <input class="form-control" id="tipo" name="tipo" type="text" value="<%= local.getTipo() %>">
                </div> --%>
                
		        <div class="form-group">
		          <label for="descricao">Descrição</label>
		          <textarea class="form-control" id="descricao" name="descricao" rows="3" ><%= local.getDescricao() %></textarea>
		        </div>
		        
		        <div class="form-group">
		          <label for="zIndex">zIndex</label>
                  <input class="form-control" id="zIndex" name="zIndex" type="number" min="0" max="1000" value="<%= local.getzIndex() %>">
		        </div>

		        <div class="form-group">
		          <label for="andar">Andar</label>
                  <input class="form-control" id="andar" name="andar" type="number" min="0" max="6" value="<%= local.getAndar() %>">
		        </div>
              </form>
              
              <div class="card" style="margin-bottom: 20px;">
              	<div class="card-header">Arquivo</div>
			    <div class="card-body">
	              <h6>Selecione o arquivo.</h6>
	              <input type="file" id="file" name="file" accept="image/*" style="margin-bottom: 5px;"/>
	              <h6>URL do Arquivo:</h6>
	              <span id="linkbox"></span>
			      <div id="divImagem" style="width: 100px; height: 100px; display: none;" >
			      	<img id="imagemLocal" alt="imagem" src="" style="width: 100%; height: auto;">
			      </div>
			    </div>
              </div>
                
              
              <div class="card">
			    <div class="card-header">Localização</div>
			    <div class="card-body">
                <div class="form-group">
                  <div style="width:100%; display:inline-block;"  >
	                  <label for="latitudeAux">Latitude</label>
	                  <input class="form-control" id="latitudeAux" name="latitudeAux" type="text" value="<%= local.getLatitude() %> " disabled="disable">
                  </div>
                  <div style="width:100%; display:inline-block;">
	                  <label for="longitudeAux">Longitude</label>
	                  <input class="form-control" id="longitudeAux" name="longitudeAux" type="text" value="<%= local.getLongitude() %>" disabled="disable">
                  </div>
                </div>
				  <div id="mapDiv"></div>
			    </div>
			</div>
              <!-- Div para o mapa -->
        	  
        	  <div style="margin-top: 20px;">
    	          <button class="btn btn-outline-primary" type="submit" formaction="locais?cmd=doSalvar" form="formLocal">Salvar</button>
	        	  <button class="btn btn-outline-danger" type="submit" form="formLocal">Sair</button>
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
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-analytics.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-auth.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-storage.js"></script>
  
  <!-- Métodos firebase -->
  <script src="js/firebase-metodos.js"></script>
  
  <script>
  
  firebase.analytics();
  
  var auth = firebase.auth();
  var storageRef = firebase.storage().ref();

  function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    var file = evt.target.files[0];

    var metadata = {
      'contentType': file.type
    };
    
    var fileNameStorage = document.getElementById("idLocal").value + ".png";

    // Push to child path.
    // [START oncomplete]
    storageRef.child('locais/' + fileNameStorage).put(file, metadata).then(function(snapshot) {
      console.log('Uploaded', snapshot.totalBytes, 'bytes.');
      console.log('File metadata:', snapshot.metadata);
      // Let's get a download URL for the file.
      snapshot.ref.getDownloadURL().then(function(url) {
        console.log('File available at', url);
        // [START_EXCLUDE]
        document.getElementById('linkbox').innerHTML = '<a href="' +  url + '">Click For File</a>';
        // [END_EXCLUDE]
      });
    }).catch(function(error) {
      // [START onfailure]
      console.error('Upload failed:', error);
      // [END onfailure]
    });
    // [END oncomplete]
  }

  window.onload = function() {
    document.getElementById('file').addEventListener('change', handleFileSelect, false);
    document.getElementById('file').disabled = true;

    auth.onAuthStateChanged(function(user) {
      if (user) {
        //console.log('Anonymous user signed-in.', user);
        document.getElementById('file').disabled = false;
      } else {
        console.log('There was no anonymous session. Creating a new anonymous user.');
        // Sign the user in anonymously since accessing Storage requires the user to be authorized.
        auth.signInAnonymously().catch(function(error) {
          if (error.code === 'auth/operation-not-allowed') {
            window.alert('Anonymous Sign-in failed. Please make sure that you have enabled anonymous ' + 'sign-in on your Firebase project.');
          }
        });
      }
    });
    
    var nomeFileStorage = document.getElementById('idLocal').value + ".png";
    
    storageRef.child('locais/'+nomeFileStorage).getDownloadURL().then(function(url) {

    	  // This can be downloaded directly:
    	  var xhr = new XMLHttpRequest();
    	  xhr.responseType = 'blob';
    	  xhr.onload = function(event) {
    	    var blob = xhr.response;
    	  };
    	  xhr.open('GET', url);
    	  xhr.send();

    	  // Or inserted into an <img> element:
    	  var img = document.getElementById('imagemLocal');
    	  img.src = url;
    	  document.getElementById("divImagem").style.display = "block";

    	}).catch(function(error) {
    	  // Handle any errors
    	});
  }
  
</script>

<script type="text/javascript" src="js/mapa-adm.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCcyfb90uvyNi6HGsdGfclkUWEvwQQfrRs&callback=initMap"></script>
</body>

</html>
