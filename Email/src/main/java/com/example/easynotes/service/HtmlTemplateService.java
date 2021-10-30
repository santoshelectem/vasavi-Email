/**
 * 
 */
package com.example.easynotes.service;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.example.streams.VelocityEngine;

/**
 * @author Electem2
 *
 */
@Service
public class HtmlTemplateService {
	private static final String templateEncoding = "UTF-8";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
//	@Value("${mail.smtp.auth}")
//	private String auth;
	
	@Autowired
	private VelocityEngine velocityEngine;

	private transient final VelocityContext velocityContext = new VelocityContext();

	public String renderTemplate(String template, Map<String,Object> context) {
		
		for (String key: context.keySet()) {
			velocityContext.put(key, context.get(key));
		}
		
		String result = generateHTMLWithContext(velocityContext, template, templateEncoding);	
		return result;
	}
	private String generateHTMLWithContext(final VelocityContext context, final String templateName,
			final String encoding) {

		final StringWriter stringWriter = new StringWriter();
		final org.apache.velocity.Template template = velocityEngine.getTemplate(templateName, encoding);
		template.merge(context, stringWriter);

		return stringWriter.toString();
	}
	/*
	 * sendMailTrap
	 */
	public void sendMailTrap(Properties prop, String template, String ccRecipient, String subject, String recipient, String attachment) {
        String from = "hitler@electems.com";
        log.info("Started the Mailtrap :: MailtrapMessage ::sendMessage");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.electems.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp"); 
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
       
        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(props); 
        
        Session session = Session.getInstance(props,
                   new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication() {
                         return new PasswordAuthentication("santosh@electems.com","cybRVE12#");
              }
                   });
        try {
              // Create a default MimeMessage object.
              MimeMessage message = new MimeMessage(session);
           
              // Set From: header field 
              message.setFrom(new InternetAddress(from));
           
              // Set To: header field
              message.setRecipients(Message.RecipientType.TO,
                         InternetAddress.parse(recipient));
           
              if(ccRecipient != null) {
                   message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccRecipient));
              }
             
              // Set Subject: header field
              message.setSubject(subject);
           
              // Put the content of your message
              String charset = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
              VelocityContext ctx = new VelocityContext();
              if(prop != null) {
                  log.info("Started Constructing Mailtrap Template with :" +template);
                  for (Object key : prop.keySet()) {
                      String name = (String) key;
                      if(name != null) {
                          ctx.put(name, prop.getProperty(name));
                      }
                  }
                  
                  Template tpl = Velocity.getTemplate(template);
                  StringWriter sw = new StringWriter();
                  if(tpl != null) {
                      tpl.merge(ctx, sw);
                  }
                  
                  message.setText(sw.toString(), charset, "html");
                  log.info("Ended Constructing Mailtrap Template Properties");
              } else{
                  // below line is commented for attach pdf for reports
                  //message.setText("Hello, <br/> We have Attached a PDF could you please check");
                  
                  message.setText(template.toString(), charset, "html");
              }
              
              if(ccRecipient != null) {
                   MimeBodyPart messageBodyPart =  new MimeBodyPart();
                   messageBodyPart.setText(template.toString(), charset, "html");
                   Multipart multipart = new MimeMultipart();
                   multipart.addBodyPart(messageBodyPart); 
                   messageBodyPart = new MimeBodyPart();
                   DataSource source = new FileDataSource(attachment);  
                         messageBodyPart.setDataHandler(new DataHandler(source));  
                         messageBodyPart.setFileName(attachment);  
                         multipart.addBodyPart(messageBodyPart);  
                     // Put parts in message  
                         message.setContent(multipart);
              }
             
              
              // Send message
              Transport.send(message);



              log.info("Sent message successfully....");



                } catch (MessagingException e) {
                   log.info("Error while sending message through Mailtrap: "+e.getMessage());
                   //throw new RuntimeException(e);
        }
        log.info("Ended the Mailtrap :: MailtrapMessage ::sendMessage");
  }
}
