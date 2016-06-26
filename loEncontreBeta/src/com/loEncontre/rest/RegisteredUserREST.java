package com.loEncontre.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.loEncontre.dao.CreateUserWithDocumentDAO;

@Path("registrarUsuario")
public class RegisteredUserREST {

	private CreateUserWithDocumentDAO createUser = CreateUserWithDocumentDAO.getInstancia();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{token}/{nombrePropietario}/{email}")
	public String insertDocument(@PathParam("token") String token, @PathParam("nombrePropietario") String nombrePropietario, @PathParam("email") String email){
		System.out.println("1");
		String retorno = createUser.createUserAndVerifyDocument(token, nombrePropietario, email);
		System.out.println("2");
		return "{\"hola\":"+retorno+"}";
	}
}
