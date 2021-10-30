package com.example.easynotes.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.application.data.ArticleData;
import io.spring.core.article.Article;

@RestController
public class ArticleDataController {
	@GetMapping("/get/articleData")
	public ArticleData getHttpApiArticle() throws JAXBException, ClientProtocolException, IOException {
		/*
		 * CloseableHttpClient httpClient = HttpClients.createDefault();
		 * 
		 * HttpGet getRequest = new HttpGet("http://localhost:8080/articles/slug1");
		 * HttpResponse response = httpClient.execute(getRequest);
		 * 
		 * // verify the valid error code first int statusCode =
		 * response.getStatusLine().getStatusCode();
		 * 
		 * // Now pull back the response object HttpEntity httpEntity =
		 * response.getEntity(); String apiOutput = EntityUtils.toString(httpEntity);
		 * 
		 * // Lets see what we got from API System.out.println(apiOutput);
		 */
		
		// File file=new File("c:\temp\responseofarticle.json");
		// readFileToByteArray(File file)
		
		String readFileToString = FileUtils.readFileToString(new File("C://temp/responseofarticle.json"), "UTF8");
		ObjectMapper objectMapper = new ObjectMapper();
		ArticleData articalData = objectMapper.readValue(readFileToString, ArticleData.class);
		System.out.println(articalData);
		return articalData;
	}

}
