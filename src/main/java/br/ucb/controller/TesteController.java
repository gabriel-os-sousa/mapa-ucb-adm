package br.ucb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.ucb.model.Local;
import br.ucb.util.ConfiguracaoFirebase;

@WebServlet("/test")
public class TesteController extends HttpServlet {

	private void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Local> locais = new ArrayList<>();

		DatabaseReference locaisRef = ConfiguracaoFirebase.getDatabaseReference().child("locais");
		ValueEventListener vel = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot dados : snapshot.getChildren()) {
					Local local = dados.getValue(Local.class);
					locais.add(local);
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
			}
		};
		locaisRef.addValueEventListener(vel);
		System.out.println(locais.size());

		req.setAttribute("AttrLocais", locais);
		RequestDispatcher dispatcher = req.getRequestDispatcher("index2.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

}
