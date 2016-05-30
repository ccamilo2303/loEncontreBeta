package com.loEncontre.TareaInsertQR;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bson.Document;

import com.loEncontre.Config.MongoSingletonConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TareaInsert {
	public static void main(String[] args) {
		MongoSingletonConnection con = MongoSingletonConnection.getInstancia();
		MongoDatabase db = con.getDataBase();
		MongoCollection<Document> coll = db.getCollection("validDocuments");
		SecretKey secretKey = new SecretKeySpec("loEncontre".getBytes(), "AES");
		for(int a = 1 ; a <= 20 ; a ++){
			Document insert = new Document();
			String JWT = Jwts.builder().setId(String.valueOf(a)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
			insert.append("JWT", JWT);
			insert.append("value", String.valueOf(a));
			Calendar date = GregorianCalendar.getInstance();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			insert.append("registrationDate", ""+formato.format(date.getTime())+"");
			System.out.println("INSERT .... "+JWT);;
			coll.insertOne(insert);
		}
	}
}
