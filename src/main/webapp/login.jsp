<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>MAPA UCB - Login</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.css" rel="stylesheet">
  
</head>

<body class="bg-gradient-primary">

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Área Segura!</h1>
                  </div>
                  <form class="user" action="login" method="post">
                    <div class="form-group">
                      <input type="email" name="email" class="form-control form-control-user" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Usuário">
                    </div>
                    <div class="form-group">
                      <input type="password" name="password" class="form-control form-control-user" id="exampleInputPassword" placeholder="Senha">
                    </div>
                    <!-- <a href="login?cmd=doAutenticar" class="btn btn-primary btn-user btn-block">Login</a> -->
                    <hr>
                  </form>
                    <button type="submit" id="doLogin" class="btn btn-primary btn-user btn-block" onclick="doLogin()">Login</button>
                  <div class="text-center">
                    <a class="small" href="login?cmd=resetPassword">Esqueceu a senha?</a>
                  </div>
                  <!-- <div class="text-center">
                    <a class="small" href="/locais?cmd=createAccount">Criar uma conta!</a>
                  </div> -->
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>
  
  <form style="display: none" action="login" method="POST" id="formLogin">
    <input type="hidden" id="cmd" name="cmd" value=""/>
    <input type="hidden" id="idToken" name="idToken" value=""/>
  </form>


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
	    console.log(user);
	    console.log( "Usuario logado: " + user.displayName +" Is annony: "+ user.isAnonymous);
	  } else {
	    //offline
	    console.log(user);
	    console.log("Usuario n logado" );
	  }
  });
  
  function doLogin() {
	  firebase.auth().signInWithEmailAndPassword("gabriiel.dfx@gmail.com", "111111")
	   .then(function(user) {
	       // Success 
	       console.log("Logou");
	       
	       firebase.auth().currentUser.getIdToken(false).then(function(idToken) {
	    	   //Seta os values do form hidden
	    	   document.getElementById("cmd").value = "doAutenticar";
	    	   document.getElementById("idToken").value = idToken;
	    	   document.getElementById("formLogin").submit();
	    	   
	    	   // Send token to your backend via HTTPS
// 	      	 window.location.href = "/mapa-ucb-adm/login?cmd=doAutenticar&idToken="+idToken;
	    	   // ...
	    	 }).catch(function(error) {
	    	   // Handle error
	    	 });
	       
	   })
	  .catch(function(error) {
	       // Error Handling
		  console.log("Não Logou");
	  });
  }
  
  </script>

</body>

</html>
