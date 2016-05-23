package com.loEncontre.DAO;

import com.loEncontre.config.MongoSingletonConnection;

/**
 * 
 * @author crick
 *	22/05/2016
 */
public class CreateUserWithDocumentDAO {

	/**
	 * Constructor privado
	 */
	private CreateUserWithDocumentDAO(){}
	
	/**
	 * Variable que almacena la instancia de this
	 */
	private static CreateUserWithDocumentDAO instancia_ = null;
	
	/**
	 * Variable que llama la instancia de la conexion
	 */
	private MongoSingletonConnection con = MongoSingletonConnection.getInstancia();
	
	
	/**
	 * Metodo que retorna una unica instancia de this
	 * @return this
	 */
	public CreateUserWithDocumentDAO getInstancia(){
		if(instancia_ == null){
			instancia_ = new CreateUserWithDocumentDAO();
		}
		return instancia_;
	}
	
	public String createUserAndVerifyDocument(String token){
		return"";
	}

}
