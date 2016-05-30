package com.loEncontre.ControladorJWT;

import java.security.Key;

import org.bson.Document;

import com.loEncontre.Config.MongoSingletonConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 
 * @author crick
 * 28/05/2016
 */
public class ControllerJWT {
	/**
	 * Variable que llama la instancia de la conexion
	 */
	private MongoSingletonConnection con = MongoSingletonConnection.getInstancia();
	
	public String generateJWT(String ID){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("publicKey");
		FindIterable<Document> keys = coll.find();
		MongoCursor<Document> cursor = keys.iterator();
		while(cursor.hasNext()){
			
			Document documento = cursor.next();
			
			System.out.println(documento);
			System.out.println(documento.getString("key1"));;
		}
		
		Key key = MacProvider.generateKey();
		String s = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, key).compact();		
		return"";
	}
	
	public String DecodeJWT(String JWT){
		return"";
	}
	
	public static void main(String[] args) {
		ControllerJWT a = new ControllerJWT();
		a.generateJWT("hola");
	}
}
