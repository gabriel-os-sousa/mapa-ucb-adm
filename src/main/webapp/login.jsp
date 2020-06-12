<!DOCTYPE html>
<html lang="pt-br">

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
              <div class="col-lg-6 d-none d-lg-block bg-login-image "></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">�rea Segura!</h1>
                  </div>
                  <form class="user" action="login" method="post">
                    <div class="form-group">
                      <input type="email" name="email" class="form-control form-control-user" id="email" aria-describedby="emailHelp" placeholder="Email" required="required">
                    </div>
                    <div class="form-group">
                      <input type="password" name="password" class="form-control form-control-user" id="password" placeholder="Senha" required="required">
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
  <script src="https://www.gstatic.com/firebasejs/7.14.4/firebase-auth.js"></script>
  
  <!-- M�todos firebase -->
  <script src="js/firebase-metodos.js"></script>
  
  <script type="text/javascript">
	// Using jQuery.
	
	const password = document.getElementById('password');
	const email = document.getElementById('email');
	password.addEventListener('keyup', function(e){
		  var key = e.which || e.keyCode;
		  if (key == 13) { // codigo da tecla enter
			  doLogin();
		  }
		});
	
	email.addEventListener('keyup', function(e){
		  var key = e.which || e.keyCode;
		  if (key == 13) { // codigo da tecla enter
			  doLogin();
		  }
		});
	
	</script>
</body>

</html>
