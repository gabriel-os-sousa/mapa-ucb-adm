
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
	   	alert("logout");
		//sucess
	    console.log('Logout');
	  }, function(error) {
		//error
	    console.error( error );
	  });
  }
  
  function doLogin() {
	 /* if(firebase.auth().currentUser) {
		  firebase.auth().signOut();
	  } else {*/
		  var email = document.getElementById('email').value;
	      var password = document.getElementById('password').value;
	      
	      // Tratamento de erro dos campos
	      if (email.length < 1) {
            alert('Por favor, insira o email!');
            return;
          }
	      if (password.length < 1) {
            alert('Por favor, insira a senha!');
            return;
          }
	        
	      // Login com email e senha
		  firebase.auth().signInWithEmailAndPassword(email, password)
		  .then(function(user) {
			  // Sucesso 
			  firebase.auth().currentUser.getIdToken(false).then(function(idToken) {
				  //Seta os values do form hidden
				  document.getElementById("cmd").value = "doLogin";
				  document.getElementById("idToken").value = idToken;
				  document.getElementById("formLogin").submit();
				  
			  }).catch(function(error) {
		          console.log(error);
			  });
		  })
		  .catch(function(error) {
			  // Tratamento de erros
	          var errorCode = error.code;
	          var errorMessage = error.message;

	          if (errorCode === 'auth/wrong-password') {
	            alert('Senha incorreta. Por favor, tente novamente!');
	          } else if(errorCode === 'auth/user-not-found') {
	        	  alert("Usuário não encontrado na base de dados!");
	          } else if(errorCode === 'auth/invalid-email') {
	        	  alert("Formato de email inválido!");
	          } else {
	        	  alert("Código do erro: "+ errorCode +" -->"+ errorMessage);
	          }
			  console.log("Erro login!");
		  });
	  /*}*/
  }
