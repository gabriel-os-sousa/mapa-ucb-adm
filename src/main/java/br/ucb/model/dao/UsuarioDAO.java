package br.ucb.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import br.ucb.model.Usuario;
import br.ucb.util.ConfiguracaoFirebase;
import br.ucb.util.Strings;
import br.ucb.util.ValidacaoException;

public class UsuarioDAO {
	
	private static UsuarioDAO instance;
	
	// Configura o SDK de authentication firebase
	FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
	
	private UsuarioDAO() {
	}
	
	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}
	
	public List<Usuario> obterUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();

		// Configura o SDK de authentication firebase
		FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
		
		// Iterate through all users. This will still retrieve users in batches,
		// buffering no more than 1000 users in memory at a time.
			ListUsersPage page;
			try {
				page = FirebaseAuth.getInstance().listUsers(null);
				page = auth.listUsers(null);
				for (ExportedUserRecord user : page.iterateAll()) {
					Usuario usuario = new Usuario();
					usuario.setUserRecord((UserRecord)user);
					usuarios.add(usuario);
				}
			} catch (FirebaseAuthException e) {
				e.printStackTrace();
				System.out.println(e.getMessage()); 
			}
		
		return usuarios;
	}
	
	public void excluir(String id) {
		
		try {
			auth.deleteUser(id);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}
	
	public UserRecord recuperarUsuario (String id) {
		UserRecord user = null;
		
		try {
			user = auth.getUser(id);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void salvar(Usuario usuario) {
		//validação de dados
		validar(usuario);
		
		if (Strings.isNotEmpty(usuario.getId()) || Strings.isNotNull(usuario.getId())) {
			atualizar(usuario);
		} else {
			inserir(usuario);
		}
	}
	
	private void validar(Usuario usuario) {
		ValidacaoException exception = new ValidacaoException();
		
		if (Strings.isNull(usuario.getNome())){
			exception.getErros().add("O Nome do Usuário é obrigatório");
		}
		
		if (Strings.isNull(usuario.getEmail())){
			exception.getErros().add("O Email do Usuário é obrigatório");
		}
		
		if (Strings.isNull(usuario.getSenha())){
			exception.getErros().add("A senha do Usuário é obrigatória");
		}
		
		if (Strings.isNull(usuario.getTipo())){
			exception.getErros().add("O Tipo do Usuário é obrigatório");
		}
		
	
		if (!exception.getErros().isEmpty()) {
			throw exception;
		}
		
	}

	private void atualizar (Usuario usuario) {
		UpdateRequest upRequest = new UpdateRequest(usuario.getId())
				.setEmail(usuario.getEmail())
				.setEmailVerified(true)
				.setDisplayName(usuario.getNome())
				.setDisabled(false);
		
		// Faz o update e também Recupera os dados do usuário
		UserRecord usuarioRecuperado = null;
		try {
			usuarioRecuperado = FirebaseAuth.getInstance().updateUser(upRequest);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw new IllegalStateException("Erro ao atualizar usuario");
		}
		System.out.println("Successfully updated user: " + usuarioRecuperado.getUid());
		
		atualizarTipoUsuario(usuario, usuarioRecuperado);
		
	}
	
	//Criar os claims, se for admin seta o claim admin como 'true', senão 'false'
	private void atualizarTipoUsuario(Usuario usuario, UserRecord usuarioRecuperado) {
		Map<String, Object> claims = new HashMap<>();
		if(usuario.getTipo().equalsIgnoreCase("admin"))
			claims.put("admin", true);
		else
			claims.put("admin", false);
		
		try {
			auth.setCustomUserClaims(usuarioRecuperado.getUid(), claims);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw new IllegalStateException("Erro ao adicionar usuario2");
		}
	}

	private void inserir (Usuario usuario) {
		try {
		CreateRequest createRequest = new CreateRequest()
			    .setEmail(usuario.getEmail())
			    .setEmailVerified(true)
			    .setPassword(usuario.getSenha())
			    .setDisplayName(usuario.getNome())
			    .setDisabled(false);

		UserRecord usuarioRecuperado = null;
		
		usuarioRecuperado = FirebaseAuth.getInstance().createUser(createRequest);
		System.out.println("Successfully created new user: " + usuarioRecuperado.getUid());
		
		atualizarTipoUsuario(usuario, usuarioRecuperado);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new IllegalStateException("Erro ao adicionar usuario");
			
			ValidacaoException exception = new ValidacaoException();

			exception.getErros().add("Erro ao adicionar usuário!");
			
			if (!exception.getErros().isEmpty()) {
				throw exception;
			}
		}
	}
}
