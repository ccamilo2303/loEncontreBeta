package com.loEncontre.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author crick
 * 22/05/2016
 */
public class MongoSingletonConnection {

	/**
	 * Constructor privado
	 */
	private MongoSingletonConnection(){}
	
	/**
	 * Varibale con instancia this
	 */
	private static  MongoSingletonConnection instancia_ = null;
	
	/**
	 * Varible estatica que almacena el cliente de mongo
	 */
	private static MongoClient cliente_ = null;
	
	/**
	 * Metodo que retorna una unica instancia
	 * @return this
	 */
	public static MongoSingletonConnection getInstancia(){
		if(instancia_ == null){
			instancia_ = new MongoSingletonConnection();
			cliente_ = new MongoClient(new MongoClientURI("mongodb://crick:camiloBeltran123@ds025232.mlab.com:25232/loencontrebeta"));
		}
		return instancia_;
	}
	
	/**
	 * Metodo que retorna la DB
	 * @return MongoDatabase
	 */
	public MongoDatabase getDataBase(){
		return cliente_.getDatabase("loencontrebeta");
	}
}
