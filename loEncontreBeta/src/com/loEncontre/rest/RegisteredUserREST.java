package com.loEncontre.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.loEncontre.dao.CreateUserWithDocumentDAO;
/**
 * 
 * @author crick
 * 26/06/2016
 */
@Path("registrarUsuario")
public class RegisteredUserREST {

	private CreateUserWithDocumentDAO createUser = CreateUserWithDocumentDAO.getInstancia();

	/**
	 * Valida que el codigo QR no este registrado y tambiena valida que sea valido
	 * @param token
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validar/{token}")
	public String validateQR(@PathParam("token") String token){
		int retorno = createUser.createUserAndVerifyDocument(token);
		return "{\"indice\": "+retorno+" }";	
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public String findNameByEmail(@PathParam("email") String email){
		return "{\"name\": \" "+createUser.findNameByEmail(email)+"\" }";
	}
	
}
