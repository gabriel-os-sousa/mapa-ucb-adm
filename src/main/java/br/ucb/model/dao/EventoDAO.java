package br.ucb.model.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.api.core.SettableApiFuture;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import br.ucb.model.Evento;
import br.ucb.util.ConfiguracaoFirebase;

public class EventoDAO extends AbstractDAO<Evento> {
	
	public String getIdFirebase() {
		DatabaseReference firebase =  ConfiguracaoFirebase.getDatabaseReference().child("eventos");
		String id = firebase.push().getKey();
		return id;
	}

	public void salvar(Evento evento) {
		if (evento.isPersistido()) {
			atualizar(evento);
		}
		else {
			inserir(evento);
		}
	}
	
	private void inserir(Evento evento) {
		DatabaseReference firebase =  ConfiguracaoFirebase.getDatabaseReference().child("eventos");
		String id = evento.getId();
		evento.setDataCadastro(System.currentTimeMillis());
		firebase.child(id).setValueAsync(evento);
	}

	private void atualizar(Evento evento) {
		Evento eventoPersistido = obterEvento(evento.getId());
		if (eventoPersistido == null) {
			throw new IllegalStateException("Não foi localizado o Local com nome " + evento.getNome());
		}
		eventoPersistido.setNome(evento.getNome());
		eventoPersistido.setTipo(evento.getTipo());
		eventoPersistido.setDescricao(evento.getDescricao());
		eventoPersistido.setLocal(evento.getLocal());
		
		DatabaseReference dr =  ConfiguracaoFirebase.getFirebaseDatabase().getReference("eventos").child(evento.getId());
		dr.setValueAsync(eventoPersistido);
	}
	
	public void excluir(Evento evento) {
		Evento eventoPersistido = obterEvento(evento.getId());
		if (eventoPersistido == null) {
			throw new IllegalStateException("O Local não foi encontrado na base de dados.");
		}
		
		try {
			ConfiguracaoFirebase.getDatabaseReference().child("eventos").child(eventoPersistido.getId()).removeValueAsync().get();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Evento obterEvento(String id) {
		Evento evento = null;
		try {

			// https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
			SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();
			
			DatabaseReference eventosRef = ConfiguracaoFirebase.getDatabaseReference();
			Query query = eventosRef.child("eventos").orderByChild("id").equalTo(id);

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
			
			evento = future.get().getValue(Evento.class);

		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return evento;
	}
	
	@Override
	public List<Evento> obterEntidades(String pathString) {
		List<Evento> eventos = super.obterEntidades(pathString);
		Collections.sort(eventos, Collections.reverseOrder(Comparator.comparing(Evento::getDataCadastro)));
		return eventos;
	}
}
