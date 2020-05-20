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

        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">Seja bem-vindo!</h1>
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
