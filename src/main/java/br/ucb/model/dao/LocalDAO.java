package br.ucb.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.core.SettableApiFuture;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.ucb.model.Local;
import br.ucb.util.ConfiguracaoFirebase;

public class LocalDAO {

	public void salvar(Local local) {
	}

	public void excluir(Local local) {
	}

	public void atualizar(Local local) {
		Local localBanco = obterLocal(local.getId());
		if (localBanco == null) {
			throw new IllegalStateException("Não foi localizado o Local com nome " + local.getNome());
		}
		localBanco.setNome(local.getNome());
		localBanco.setTipo(local.getTipo());
		localBanco.setDescricao(local.getDescricao());
		
	}

	public Local obterLocal(String id) {
		Local local = null;
		try {

			// https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
			SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();
			
			DatabaseReference locaisRef = ConfiguracaoFirebase.getFirebaseDatabase();
			Query query = locaisRef.child("locais").orderByChild("id").equalTo(id);

			ChildEventListener vel = new ChildEventListener() {
				
				@Override
				public void onChildRemoved(DataSnapshot snapshot) {
				}
				
				@Override
				public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				}
				
				@Override
				public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				}
				
				@Override
				public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
					future.set(snapshot);
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
				}
			};
			
			query.addChildEventListener(vel);
			
			local = future.get().getValue(Local.class);

		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return local;
	}

	public List<Local> obterLocais() {

		List<Local> locais = new ArrayList<>();
		try {
			SettableApiFuture<DataSnapshot> future = execute("locais");
			for (DataSnapshot dados : future.get().getChildren()) {
				Local local = dados.getValue(Local.class);
				locais.add(local);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		return locais;
	}

	private SettableApiFuture<DataSnapshot> execute(String pathString) {

		// https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
		SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();

		DatabaseReference locaisRef = ConfiguracaoFirebase.getFirebaseDatabase().child(pathString);
		
		ValueEventListener vel = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				future.set(snapshot);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				future.setException(error.toException());
			}
		};
		locaisRef.addValueEventListener(vel);
		return future;
	}

	public static void main(String[] args) {
		LocalDAO dao = new LocalDAO();
		
		Local local = dao.obterLocal("-M5NC-UFkDqrKPOjgERw");
		
		System.out.println(local.getDescricao());
		local.setDescricao(new Date().toString());
		
		dao.atualizar(local);
		System.out.println(local.getDescricao());
	}

}