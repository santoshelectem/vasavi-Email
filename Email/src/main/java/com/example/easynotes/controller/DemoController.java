package com.example.easynotes.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.model.Employeee;
import com.example.easynotes.model.Tasks;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
public class DemoController {
	@GetMapping("/get/tags")
	public void getHttpApi() throws ClientProtocolException, IOException, JAXBException
	{

//		    CloseableHttpClient httpclient = HttpClients.createDefault();
//            //HTTP GET method
//            HttpGet httpget = new HttpGet(

//            httpget.setHeader("Accept", "application/json, application/json");
//            httpget.setHeader("Content-Type", "application/json;charset=UTF-8");
//            httpget.setHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YzRhYjNkZi1jMWMyLTQ4ZTktYWQ0ZS0zYzE5MGUyYTdjYmIiLCJleHAiOjE2MzUzMjAxODJ9.Obwb2g68-k8YxbEY2ekfVj2TA7G39umeArVuF5XNrDeSZd8LPf2F5rJSXRgVvBzLG35Z-5yKkNosmVBywBIujQ");    
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                // return it as a String
//                String result = EntityUtils.toString(entity);
//                System.out.println(result);
//                response.close();
//                httpclient.close();
//            }
		
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		    try
		    {
		        //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
		        //Choice depends on type of method you will be invoking.
		        HttpGet getRequest = new HttpGet("http://localhost:8080/articles/slug1");
		         
		        //Set the API media type in http accept header
		        getRequest.addHeader("accept",  "application/json, application/json");
		        getRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YzRhYjNkZi1jMWMyLTQ4ZTktYWQ0ZS0zYzE5MGUyYTdjYmIiLCJleHAiOjE2MzUzMjAxODJ9.Obwb2g68-k8YxbEY2ekfVj2TA7G39umeArVuF5XNrDeSZd8LPf2F5rJSXRgVvBzLG35Z-5yKkNosmVBywBIujQ");
		          
		        //Send the request; It will immediately return the response in HttpResponse object
		        HttpResponse response = httpClient.execute(getRequest);
		         
		        //verify the valid error code first
		        int statusCode = response.getStatusLine().getStatusCode();
		      
		        //Now pull back the response object
		        HttpEntity httpEntity = response.getEntity();
		        String apiOutput = EntityUtils.toString(httpEntity);
		         
		        //Lets see what we got from API
		        System.out.println(apiOutput); //<user id="10"><firstName>demo</firstName><lastName>user</lastName></user>
		         
		        //In realtime programming, you will need to convert this http response to some java object to re-use it.
		        //Lets see how to jaxb unmarshal the api response content
		        //JAXBContext jaxbContext = JAXBContext.newInstance(HashMap.class);
		        
		        ObjectMapper objectMapper = new ObjectMapper();
		        HashMap<String, Object> task  = objectMapper.readValue(apiOutput, HashMap.class);
		       

		       //// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		       // jaxbUnmarshaller.setProperty(Unmarshaller., Boolean.TRUE);
		      //  HashMap<String, Object> task = (HashMap<String, Object>) jaxbUnmarshaller.unmarshal(new StringReader(apiOutput));
		         
		        //Verify the populated object
		        System.out.println(task);
		       // System.out.println(task.getName());
		    }
		    finally
		    {
		        //Important: Close the connect
		        httpClient.close();
		    }

		}
	@PostMapping("/employe")
	public static void httpPost() throws IOException, JAXBException
	{
//		 CloseableHttpClient httpclient = HttpClients.createDefault();
//		 String result = "";
//		   HttpPost httppost = new HttpPost("http://192.168.1.4:8080/employee");
//		   httppost.setHeader("Authorization","Basic YWRtaW46bmltZGE=");
//		   httppost.setHeader("Accept", "application/json, application/json");
//		   httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
//		   StringBuilder json = new StringBuilder();
//	        json.append("{");
//	        json.append("\"firstName\":\"mkyong\",");
//	        json.append("\"emailId\":\"hello@gmail.com\"");
//	        json.append("}");
//	        httppost.setEntity(new StringEntity(json.toString()));
//	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//	                CloseableHttpResponse response = httpClient.execute(httppost)){
//
//	               result = EntityUtils.toString(response.getEntity());
//	           }
//
//	           return result;
	           
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	           Employeee employee = new Employeee();
	           employee.setFirstName("Lokesh");
	           employee.setLastName("getha");
	           employee.setEmailId("hello@");
	            
	           StringWriter writer = new StringWriter();
	           JAXBContext jaxbContext = JAXBContext.newInstance(Employeee.class);
	           Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	           jaxbMarshaller.marshal(employee, writer);
	            
	           try
	           {
	               //Define a postRequest request
	               HttpPost postRequest = new HttpPost("http://192.168.1.4:8080/employee");
	                
	               //Set the API media type in http content-type header
	             //  postRequest.addHeader("content-type", "application/xml");
	               postRequest.addHeader("Authorization","Basic YWRtaW46bmltZGE=");
	    		   postRequest.addHeader("Accept", "application/json, application/json");
	    		   postRequest.addHeader("Content-Type", "application/json;charset=UTF-8");
	               //Set the request post body
	               StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
	               postRequest.setEntity(userEntity);
	                 
	               //Send the request; It will immediately return the response in HttpResponse object if any
	               HttpResponse response = httpclient.execute(postRequest);
	               //result = EntityUtils.toString(response.getEntity());
	                
	               //verify the valid error code first
	               int statusCode = response.getStatusLine().getStatusCode();
	               if (statusCode != 201) 
	               {
	                   throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	               }
	           }
	           finally
	           {
	               //Important: Close the connect
	        	   httpclient.getConnectionManager().shutdown();
	           }
			
	       

	}
	
}
