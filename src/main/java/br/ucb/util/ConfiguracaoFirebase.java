package br.ucb.util;

import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfiguracaoFirebase {

	private static DatabaseReference database;
	private static FirebaseAuth auth;

	// retorna a instancia do FirebaseDatabase
	public static DatabaseReference getFirebaseDatabase() {
		if (database == null) {

			try {
				InputStream serviceAccount = DatabaseReference.class.getClassLoader()
						.getResourceAsStream("chave-privada.json");
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://mapa-ucb.firebaseio.com").build();

				FirebaseApp.initializeApp(options);
				database = FirebaseDatabase.getInstance().getReference(); // retorna a referencia da raiz do banco

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return database;
	}

	public static void main(String[] args) {
		
		DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();
		DatabaseReference locaisRef = firebaseDatabase.child("/");
		boolean pesquisou = false; 
		
		ValueEventListener asdf = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot dados : snapshot.getChildren()) {
					System.out.println("--> " + dados.getValue());
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
			}
		};
		locaisRef.addValueEventListener(asdf);
		System.out.println(asdf.toString());
		System.out.println("Pesquisou " + pesquisou);
		
	}

	// retorna a instancia do FirebaseAuth
	public static FirebaseAuth getFirebaseAutenticacao() {
		if (auth == null) {
			auth = FirebaseAuth.getInstance();
		}
		return auth;
	}

}