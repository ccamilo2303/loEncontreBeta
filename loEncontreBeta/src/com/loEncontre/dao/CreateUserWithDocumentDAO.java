package com.loEncontre.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.bson.Document;

import com.loEncontre.config.MongoSingletonConnection;
import com.loEncontre.controlador.jwt.ControllerJWT;
import com.loEncontre.tarea.insert.qr.Seguridad;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
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

	public int verifyDocument(String token){
		System.out.println("ENTRO VERIFICAR TOKEN ");
		/*
		 * Se valida que el documento que llega sea original
		 * osea que esxista en la DB
		 */
		boolean validacionDocumentoOriginal = verifyValidityOfTheDocument(token);

		if(validacionDocumentoOriginal == false){
			return 1;
			//			return "El codigo QR es invalido";
		}

		/*
		 * Valida que el documento no este registrado
		 */
		boolean validacionDocumentoRegistrado = verifyNotRegistered(token);

		if(validacionDocumentoRegistrado == true){
			return 2;
			//			return "El documento ya está registrado";
		}

		return 3;
	}

	/**
	 * Metodo que valida si ya esta registrado el documento
	 * @param token
	 * @return boolean
	 */
	private boolean verifyNotRegistered(String token){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		for(Document documento :  coll.find(new Document("token",token))){
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
		for(Document documento :  coll.find(new Document("token",token))){
			valorJWT = documento.getString("value");
			break;
		}
		if(valorJWT.trim().equals(new Seguridad("loEncontre").desencriptar(token)) && !valorJWT.replace(" ", "").equals("")){
			return true;
		}
		return false;
	}

	public int registerDocument(String token, String nombrePropietario, String email, String descripcion){
		int validacion = verifyDocument(token);
		System.out.println("=> ENTRO A REGISTRAR DOCUMENTO "+validacion+" --- "+new Seguridad("loEncontre").desencriptar(token));
		if(validacion != 3){
			return validacion;
		}
		
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		FindIterable<Document> cursor = coll.find(new Document("email", email));
		if(cursor.first() != null){
			System.out.println("=> ENTRO A ACTUALIZAR");
			Document persona = cursor.first();
			Object id = persona.getObjectId("_id");
			System.out.println(id+" ----- ");
			List<Document> documentos = (List<Document>) persona.get("documents");
			documentos.add(new Document().append("description", descripcion).append("lostDocument", false));

			System.out.println(persona);
			
			coll.updateOne(new Document("_id",id), new Document("$set",persona));
		}else{
			System.out.println("=> ENTRO A CREAR");
			Document persona = new Document();
			persona.append("token", token);
			persona.append("name", nombrePropietario);
			persona.append("email", email);
			List<Document> documentos = new ArrayList<Document>();
			documentos.add(new Document().append("description", descripcion).append("lostDocument", false));
			persona.put("documents", documentos);
			Calendar date = GregorianCalendar.getInstance();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			persona.append("dateRegistration", ""+formato.format(date.getTime())+"");
			System.out.println(persona);
			coll.insertOne(persona);
		}
		return 3;
	}

	public String findNameByEmail(String email){
		String nombre = "";
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		for(Document d : coll.find(new Document("email", email))){
			nombre = d.getString("name");
			break;
		}
		return nombre;
	}
}
