package com.loEncontre.rest;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author crick
 * 06/06/2016
 */
@Path("enviarEmail")
public class SendEmailREST {

	
	/**
	 * Metodo REST que envia el correo de contacto
	 * @param nombre
	 * @param correo
	 * @param mensaje
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombre}/{correo}/{mensaje}")
	public String enviarMensajeContacto(@PathParam("nombre") String nombre, @PathParam("correo") String correo, @PathParam("mensaje") String mensaje){
		try{
			enviarCorreo(mensaje, nombre+" - "+correo, "ccamilo2303@gmail.com");
			return "{\"msg\":true}";
		}catch(Exception e){
			e.printStackTrace();
			return "{\"msg\":false}";
		}
	}
	
	/**
	 * Metodo privado generico que envia correos
	 * @param mensaje
	 * @param asunto
	 * @param destinatario
	 * @throws Exception
	 */
	 private void enviarCorreo(String mensaje, String asunto, String destinatario) throws Exception {
	  // El correo gmail de envío
	  String correoEnvia = "ccamilo2303@gmail.com";
	  String claveCorreo = "1024579400";
	 
	  // La configuración para enviar correo
	  Properties properties = new Properties();
	  properties.put("mail.smtp.host", "smtp.gmail.com");
	  properties.put("mail.smtp.starttls.enable", "true");
	  properties.put("mail.smtp.port", "587");
	  properties.put("mail.smtp.auth", "true");
	  properties.put("mail.user", correoEnvia);
	  properties.put("mail.password", claveCorreo);
	 
	  // Obtener la sesion
	  Session session = Session.getInstance(properties, null);
	 
	   // Crear el cuerpo del mensaje
	   MimeMessage mimeMessage = new MimeMessage(session);
	 
	   // Agregar quien envía el correo
	   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Camilo, de Lo Encontre!"));
	    
	   // Los destinatarios
	   InternetAddress[] internetAddresses = {
	     new InternetAddress(destinatario)};
	 
	   // Agregar los destinatarios al mensaje
	   mimeMessage.setRecipients(Message.RecipientType.TO,
	     internetAddresses);
	 
	   // Agregar el asunto al correo
	   mimeMessage.setSubject(asunto);
	 
	   // Creo la parte del mensaje
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();
	   mimeBodyPart.setContent(mensaje,"text/html");
//	   mimeBodyPart.setText(mensaje);
	 
	   // Crear el multipart para agregar la parte del mensaje anterior
	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(mimeBodyPart);
	 
	   // Agregar el multipart al cuerpo del mensaje
	   mimeMessage.setContent(multipart);
	 
	   // Enviar el mensaje
	   Transport transport = session.getTransport("smtp");
	   transport.connect(correoEnvia, claveCorreo);
	   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	   transport.close();
	 
	 }
}
