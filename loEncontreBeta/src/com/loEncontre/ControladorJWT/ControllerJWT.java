package com.loEncontre.ControladorJWT;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bson.Document;

import com.loEncontre.Config.MongoSingletonConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

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
		SecretKey secretKey = new SecretKeySpec(getKey().getBytes(), "AES");
		return Jwts.builder().setId(ID).signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	/**
	 * Metodo que retorna el ID qiue encuentra en el JWT
	 * @param JWT
	 * @return String
	 */
	public String DecodeJWT(String JWT){
		String retorno = "";
		try{
			SecretKey secretKey = new SecretKeySpec(getKey().getBytes(), "AES");
			retorno = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(JWT).getBody().getId();
		}catch(SignatureException e){
			System.out.println("ERROR => "+e.getMessage());
			retorno = "ERROR";
		}
		return retorno;
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
	public static void main(String[] args) throws NoSuchAlgorithmException {
		SecretKey secretKey = new SecretKeySpec("loEncontre29/05/201".getBytes(), "AES");
		System.out.println(Jwts.builder().setId("1").signWith(SignatureAlgorithm.HS512, secretKey).compact());;
		//eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIn0.tLGSfBeI3ln-RUlzAD1KxvSeNvjcXcMZvvLmR624VdFWsm1c5bQjQ8DXM2s9gDi8rTrkKZHkNkjHGxC-mhd-0w
	}
}
