package com.loEncontre.dao;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loEncontre.config.MongoSingletonConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FindAllDocumentDAO {

	/**
	 * Instancia de this
	 */
	private static FindAllDocumentDAO instance_ = null; 

	/**
	 * Variable que llama la instancia de la conexion
	 */
	private MongoSingletonConnection con = MongoSingletonConnection.getInstancia();

	/**
	 * Constructor privado
	 */
	private FindAllDocumentDAO(){}

	/**
	 * Metodo que retorna la instancia de this
	 * @return this
	 */
	public static FindAllDocumentDAO getInstance(){
		if(instance_ == null){
			instance_ = new FindAllDocumentDAO();
		}
		return instance_;
	}

	/**
	 * Metodo que crea Json con los resultados de la consulta
	 * @param email
	 * @return String
	 */
	public String findDocuments(String email){
		JSONObject retorno = new JSONObject();
		JSONArray emails = new JSONArray();
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("registeredDocuments");
		FindIterable<Document> result = coll.find(new Document("email",email));

		if(result.first() != null){
			for(Document documento :  result){
				JSONObject obj = new JSONObject();
				obj.put("email", documento.get("description"));
				obj.put("lostDocument", documento.get("lostDocument"));
				obj.put("dateRegistration", documento.get("dateRegistration"));
				emails.put(obj);
			}
			retorno.put("msg",false);
			retorno.put("results", emails);
		}else{
			retorno.put("msg",true);
		}
		return retorno.toString();
	}
}