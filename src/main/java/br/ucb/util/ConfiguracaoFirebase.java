package br.ucb.util;

import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfiguracaoFirebase {

	private static FirebaseAuth auth;
	private static boolean isInitialized = false;

	public static FirebaseDatabase getFirebaseDatabase() {
		if (!isInitialized) {
			try {
				InputStream serviceAccount = DatabaseReference.class.getClassLoader()
						.getResourceAsStream("chave-privada.json");
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://mapa-ucb.firebaseio.com").build();
				FirebaseApp.initializeApp(options);
				isInitialized = true;

			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
		return FirebaseDatabase.getInstance();
	}
	

	// retorna a instancia do FirebaseDatabase
	public static DatabaseReference getDatabaseReference() {
		return getFirebaseDatabase().getReference();
	}

	public static void main(String[] args) {
		try {
			FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
			ActionCodeSettings settings = ActionCodeSettings.builder().setUrl("https://mapa-ucb-adm.herokuapp.com/eventos").setDynamicLinkDomain("mapa-ucb-adm.herokuapp.com").build();
			String generateSignInWithEmailLink = autenticacao.generateSignInWithEmailLink("gabriiel.dfx@gmail.com", settings);
			System.out.println(generateSignInWithEmailLink);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}

	// retorna a instancia do FirebaseAuth
	public static FirebaseAuth getFirebaseAutenticacao() {
		if (auth == null) {
			auth = FirebaseAuth.getInstance(getFirebaseDatabase().getApp());
		}
		return auth;
	}
	
}