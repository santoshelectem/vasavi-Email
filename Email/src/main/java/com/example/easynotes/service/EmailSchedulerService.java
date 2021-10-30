/**
 * 
 */
package com.example.easynotes.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.easynotes.model.EmailScheduler;
import com.example.easynotes.model.Report;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.EmailSchedulerRepository;
import com.example.easynotes.repository.UserRepository;

/**
 * @author Electem2
 *
 */
@Service
public class EmailSchedulerService implements Serializable {
	/**
	 * 
	 */
	private final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private String sendReportTemplate = "velocity/reportTemplate.vm";
	/**
	 * 
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * 
	 */
	@Autowired
	private HtmlTemplateService htmlTemplateService;
	/**
	 * 
	 */
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 
	 */
	@Autowired
	private EmailSchedulerRepository emailScedulerRepository;

	/**
	 * 
	 */

	public List<Report> getReports(final Long integer) {
		List<Object[]> datas = null;
		final String queryStr = "SELECT r.report_id,r.created_date,r.report_name,type FROM email_scheduler e \r\n"
				+ "   INNER JOIN user u INNER JOIN customer_model c\r\n"
				+ "	 INNER JOIN report r  WHERE e.user_id= u.user_id \r\n"
				+ "            AND c.user_id= u.user_id AND r.customer_id =c.customer_id \r\n"
				+ "            and u.role='developer' and u.user_id=" + integer;
		final Query query = entityManager.createNativeQuery(queryStr);
		datas = (List<Object[]>) query.getResultList();
		final List<Report> reports = convertObjectsToReport(datas);
		return reports;
	}

	private List<Report> convertObjectsToReport(final List<Object[]> datas) {
		final List<Report> reports = new ArrayList<Report>();
		for (final Object[] obj : datas) {
			final Report report = new Report();
			final Long value = (long) ((BigInteger) obj[0]).intValue();
			final DateTime dateTime = new DateTime(obj[1]);
			report.setReportId(value);
			report.setCreatedDate(dateTime);
			report.setReportName((String) obj[2]);
			report.setType((String) obj[3]);
			reports.add(report);
		}
		return reports;
	}

	/**
	 * 
	 * @param emailScheduler
	 * @return
	 */
	public EmailScheduler saveUpdate(final @Valid EmailScheduler emailScheduler) {

		return emailScedulerRepository.save(emailScheduler);
	}

	/**
	 * @param emailScheduler
	 * @return
	 */
	public void sendEmails() {
		List<EmailScheduler> emailScheduler = null;
		final List<Report> reportsAll = new ArrayList<Report>();
		List<Report> reports = new ArrayList<Report>();
		log.info("Send Email Started...");
		emailScheduler = emailScedulerRepository.findByStatus();
		log.error("List of EmailSchedular : size :" + emailScheduler.size());
		if (emailScheduler.size() > 0) {
			for (final EmailScheduler email : emailScheduler) {
				String userEmailIds = null;
				String[] userIds = null;
				String reportUrl = null;
				if (email.getUser().getUserId() != null) {
					reports = getReports(email.getUser().getUserId());
				}

				if (reports.size() != 0 && email.getManagerUserIds() != null) {
					if (userEmailIds != null) {
						userEmailIds = email.getManagerUserIds() + ',' + userEmailIds;
					} else {
						userEmailIds = email.getManagerUserIds();
					}
				}
				if (reports.size() > 0 && userEmailIds != null) {

					userIds = userEmailIds.split(",");
					final String[] withOutDuplicate = StringUtils.removeDuplicateStrings(userIds);
					String manageUserEmails = null;
					for (final String UserId : withOutDuplicate) {
						if (UserId != null) {
							final User user = userRepository.findByUserId(Long.parseLong(UserId));
							if (manageUserEmails != null) {
								manageUserEmails = manageUserEmails + ',' + user.getEmailId();
							} else {
								manageUserEmails = user.getEmailId();
							}
						}
					}

					final Map<String, Object> templateData = extractReports(reports, email.getUser());
					final String message = htmlTemplateService.renderTemplate(sendReportTemplate, templateData);

					reportUrl = genarateReportPdf(message);
					htmlTemplateService.sendMailTrap(null, message, manageUserEmails, "Report test",
							"saivasavi14@gmail.com", reportUrl);
					log.info("Send Email Ended ...");
				}
			}
		}
	}

	private Map<String, Object> extractReports(final List<Report> reports, final User user) {
		final Map<String, Object> userData = new HashMap<String, Object>();
		userData.put("Report", reports);
		userData.put("User", user);
		return userData;
	}

	/*
	 * *genarateReportPdf
	 * 
	 */
	public String genarateReportPdf(String html) {
		/*
		 * @url
		 */
		String url = "E:\\Email\\Reporter\\tempReport.pdf";
		/*
		 * @pdfTempFile
		 */
		final File pdfTempFile = new File("E:\\Email\\Reporter","tempReport.pdf");
		/*
		 * @file
		 */
		final File file = new File("E:\\Email\\Reporter","tempDatasheet.html");
		try {
			FileUtils.writeStringToFile(file, html);
			pdfGeneration(html, pdfTempFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("Not Generated : EmailSchedular : genarateReportPdf");
		}
		return url;
	}
	
	

	private void pdfGeneration(final String html, final File localFile) {
		final HttpURLConnection urlConnection = null;
		byte[] buffer = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String apiEndpoint = "https://selectpdf.com/api2/convert/";
			String key = "d098f751-40ca-4811-9069-13a42fe3209f";

			Map<String, Object> parameters = new LinkedHashMap<>();
			parameters.put("key", key);
			String html1 = "<html><head><body><div style=\"font-size: 20px;\">hello world</div></body></head></html>";
			parameters.put("html", html1);

			String encodedParameters = encodeParameters(parameters);

			ArrayList<NameValuePair> postParameters;

			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("key", key));
			postParameters.add(new BasicNameValuePair("html", html));

			HttpPost post;
			post = new HttpPost("https://selectpdf.com/api2/convert/"); // local

			post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
			HttpResponse response = (HttpResponse) httpClient.execute(post);
			// URL apiUrl = new URL(apiEndpoint + "?" + encodedParameters);
			// urlConnection = (HttpURLConnection)apiUrl.openConnection();
			final BufferedInputStream inputStream = new BufferedInputStream(response.getEntity().getContent());
			final FileOutputStream fileOutput = new FileOutputStream(localFile);
			final BufferedOutputStream outputStream = new BufferedOutputStream(fileOutput);
			final byte[] byteC = new byte[8 * 1024];
			int read = 0;
			while ((read = inputStream.read(byteC)) > -1) {
				outputStream.write(byteC, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();

			// buffer = EntityUtils.toByteArray(response.getEntity());

			if (urlConnection != null) {
				if (urlConnection.getResponseCode() != 200) {
					log.info("HTTP Response Code: " + urlConnection.getResponseCode());
					log.info("HTTP Response Message: " + urlConnection.getResponseMessage());
				}
			}
		} catch (Exception ex) {
			log.info("Error: " + ex.getMessage());
		}
	}

	/**
	 * @param params
	 * @return
	 */
	private static String encodeParameters(Map<String, Object> params) {
		try {
			StringBuilder data = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (data.length() != 0)
					data.append('&');
				data.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				data.append('=');
				data.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				byte[] out = data.toString().getBytes(StandardCharsets.UTF_8);
				int length = out.length;
			}

			return data.toString();
		} catch (Exception ex) {
			ex.printStackTrace();

			// throw(ex);
		}
		return "";
	}

	/*
	 * saveEmailScheduler
	 * 
	 */
	public EmailScheduler saveEmailScheduler(@Valid EmailScheduler emailScheduler) {
		/*
		 * saveEmailScheduler
		 * 
		 */
		return emailScedulerRepository.save(emailScheduler);
	}
}
