package com.loEncontre.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.loEncontre.dao.FindAllDocumentDAO;

@Path("encontrarDocumentos")
public class FindAllDocumentsREST {

	private FindAllDocumentDAO findDocuments = FindAllDocumentDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}") 
	public String insertDocument(@PathParam("email") String email){
		String retorno = findDocuments.findDocuments(email);
		JSONObject validacion = new JSONObject(retorno);
		if(validacion.getBoolean("msg") == true){
			validacion.put("message", "No se encontraron documentos para este email");
		}
		System.out.println(validacion.toString());
		return validacion.toString();
	}
	
}
