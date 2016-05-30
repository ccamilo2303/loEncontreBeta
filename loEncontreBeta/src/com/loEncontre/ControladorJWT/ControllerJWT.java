package com.loEncontre.ControladorJWT;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.bson.Document;

import com.loEncontre.Config.MongoSingletonConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author crick
 * 28/05/2016
 */
public class ControllerJWT {
	
	/**
	 * Variable que llama la instancia de la conexion
	 */
	private MongoSingletonConnection con = MongoSingletonConnection.getInstancia();

	/**
	 * Variable de instancia
	 */
	private static ControllerJWT instance_ = null;
	
	/**
	 * Contructor privado
	 */
	private ControllerJWT(){}
	
	/**
	 * 
	 * @return instancia de this
	 */
	public static ControllerJWT getInstance(){
		if(instance_ == null){
			instance_ = new ControllerJWT();
		}
		return instance_;
	}
	
	/**
	 * Metodo que retorna el JWT con el que se arma el codigo QR
	 * @param ID
	 * @return String
	 * @throws NoSuchAlgorithmException 
	 */
	public String generateJWT(String ID) throws NoSuchAlgorithmException{
		SecretKey secretKey = KeyGenerator.getInstance(getKey()).generateKey();
		return Jwts.builder().setId(ID).signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}
	
	/**
	 * Metodo que retorna el ID qiue encuentra en el JWT
	 * @param JWT
	 * @return String
	 */
	public String DecodeJWT(String JWT){
		return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(JWT).getBody().getId();
	}
	
	/**
	 * Metodo que retorna la llave para encriptar el JWT
	 * @return String
	 */
	public String getKey(){
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("publicKey");
		FindIterable<Document> keys = coll.find();
		MongoCursor<Document> cursor = keys.iterator();
		Document documento = null;
		if(cursor.hasNext()){
			documento = cursor.next();
		}
		return documento.getString("key1");
	}
}
