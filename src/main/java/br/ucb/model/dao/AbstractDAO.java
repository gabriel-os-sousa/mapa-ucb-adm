package br.ucb.model.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.google.api.core.SettableApiFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.ucb.util.ConfiguracaoFirebase;

public abstract class AbstractDAO<T> {

	protected SettableApiFuture<DataSnapshot> execute(String pathString) {

		// https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
		SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();

		DatabaseReference locaisRef = ConfiguracaoFirebase.getDatabaseReference().child(pathString);
		
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
	
	@SuppressWarnings("unchecked")
	public List<T> obterEntidades(String pathString) {

		List<T> entidades = new ArrayList<>();
		try {
			SettableApiFuture<DataSnapshot> future = execute(pathString);
			for (DataSnapshot dados : future.get().getChildren()) {
				ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
				Class<T> clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
				T entidade = (T) dados.getValue(clazz);
				entidades.add(entidade);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		return entidades;
	}
}
