package com.loEncontre.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bson.Document;

import com.loEncontre.config.MongoSingletonConnection;
import com.loEncontre.controlador.jwt.ControllerJWT;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
	 * Variable que llama la instancia del controlador JWT
	 */
	private ControllerJWT controllerJWT = ControllerJWT.getInstance();
	
	/**
	 * Metodo que retorna una unica instancia de this
	 * @return this
	 */
	public static CreateUserWithDocumentDAO getInstancia(){
		if(instancia_ == null){
			instancia_ = new CreateUserWithDocumentDAO();
		}
		return instancia_;
	}
	
	public String createUserAndVerifyDocument(String token,String nombrePropietario,  String email){
		System.out.println("ENTRO A REGISTRAR USUARIO ... ");
		/*
		 * Se valida que el documento que llega sea original
		 * osea que esxista en la DB
		 */
		boolean validacionDocumentoOriginal = verifyValidityOfTheDocument(token);
		
		if(validacionDocumentoOriginal == false){
			return "El codigo QR es invalido";
		}
		
		/*
		 * Valida que el documento no este registrado
		 */
		boolean validacionDocumentoRegistrado = verifyNotRegistered(token);
		
		if(validacionDocumentoRegistrado == true){
			return "El documento ya está registrado";
		}
		
		/*
		 * Se registra el documento si cumple todas las validaciones
		 */
		registerDocument(token, nombrePropietario, email);
		
		return "Documento registrado con exito";
	}
	
	/**
	 * Metodo que valida si ya esta registrado el documento
	 * @param token
	 * @return boolean
	 */
	private boolean verifyNotRegistered(String token){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		for(Document documento :  coll.find(new Document("JWT",token))){
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo que valida si el documento escaneado sea original
	 * osea que exista en la DB
	 * @param token
	 * @return boolean
	 */
	private boolean verifyValidityOfTheDocument(String token){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("validDocuments");
		String valorJWT = "";
		for(Document documento :  coll.find(new Document("JWT",token))){
			valorJWT = documento.getString("value");
			break;
		}
		if(valorJWT.trim().equals(controllerJWT.DecodeJWT(token))){
			return true;
		}
		return false;
	}

	private void registerDocument(String token, String nombrePropietario, String email){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		Document documento = new Document();
		documento.append("JWT", token);
		documento.append("owner", nombrePropietario);
		documento.append("email", email);
		documento.append("lostDocument", false);
		Calendar date = GregorianCalendar.getInstance();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		documento.append("dateRegistration", ""+formato.format(date.getTime())+"");
		coll.insertOne(documento);
	}
}
