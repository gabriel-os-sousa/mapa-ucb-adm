package br.ucb.model.dao;

import java.util.List;

import com.google.api.core.SettableApiFuture;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import br.ucb.model.Local;
import br.ucb.util.ConfiguracaoFirebase;

public class LocalDAO extends AbstractDAO<Local> {
	
	private static LocalDAO instance;
	private List<Local> locais;
	
	private LocalDAO() {
	}
	
	public static LocalDAO getInstance() {
		if (instance == null) {
			instance = new LocalDAO();
		}
		return instance;
	}
	
	
	/**
	 * Utilizar este método para evitar overhead no firebase
	 * @return {@link List} Locais
	 */
	public List<Local> getLocaisPersisted() {
		if (locais == null) {
			locais = obterEntidades("locais");
		}
		return locais;
	}
	
	public String getIdFirebase() {
		DatabaseReference firebase =  ConfiguracaoFirebase.getDatabaseReference().child("locais");
		String id = firebase.push().getKey();
		return id;
	}

	public void salvar(Local local) {
		if (local.isPersistido()) {
			atualizar(local);
		}
		else {
			inserir(local);
		}
		locais = null;
	}
	
	private void inserir(Local local) {
		DatabaseReference firebase =  ConfiguracaoFirebase.getDatabaseReference().child("locais");
		String id = local.getId();
		local.setDataCadastro(System.currentTimeMillis());
		firebase.child(id).setValueAsync(local);
		locais = null;
	}

	private void atualizar(Local local) {
		Local localPersistido = obterLocal(local.getId());
		if (localPersistido == null) {
			throw new IllegalStateException("Não foi localizado o Local com nome " + local.getNome());
		}
		localPersistido.setNome(local.getNome());
		localPersistido.setTipo(local.getTipo());
		localPersistido.setDescricao(local.getDescricao());
		localPersistido.setLatitude(local.getLatitude());
		localPersistido.setLongitude(local.getLongitude());
		
		DatabaseReference dr =  ConfiguracaoFirebase.getFirebaseDatabase().getReference("locais").child(local.getId());
		dr.setValueAsync(localPersistido);
		locais = null;
	}
	
	public void excluir(Local local) {
		Local localPersistido = obterLocal(local.getId());
		if (localPersistido == null) {
			throw new IllegalStateException("O Local não foi encontrado na base de dados.");
		}
		
		try {
			ConfiguracaoFirebase.getDatabaseReference().child("locais").child(localPersistido.getId()).removeValueAsync().get();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		locais = null;
	}

	public Local obterLocal(String id) {
		Local local = null;
		try {

			// https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
			SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();
			
			DatabaseReference locaisRef = ConfiguracaoFirebase.getDatabaseReference();
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
					future.setException(error.toException());
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
		return super.obterEntidades("locais");
	}

}