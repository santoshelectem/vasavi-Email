/**
 * 
 */
package com.example.easynotes.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.EmailScheduler;
import com.example.easynotes.repository.EmailSchedulerRepository;
import com.example.easynotes.service.EmailSchedulerService;

/**
 * @author Electem2
 *
 */
@RestController
@RequestMapping("/rest/Emailcontroller")
public class EmailScedulerController {
	/**
	 * 
	 * Loggers
	 *
	 */
	private final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 * emailSchdulerService
	 *
	 */
	@Autowired
	EmailSchedulerService emailSchdulerService;
	@Autowired
	EmailSchedulerRepository emailSchedulerRepository;
	/**
	 * 
	 * @param <Customer>
	 *
	 */
	@PostMapping("/Email")
	public EmailScheduler createNote(@Valid @RequestBody final EmailScheduler emailScheduler) {
		log.error("Start of EmailSchedulerController : createNote ");
		EmailScheduler emailSchedulers = new EmailScheduler();
		try {
			if (emailScheduler != null) {
				emailSchedulers = emailSchdulerService.saveEmailScheduler(emailScheduler);
			}

		} catch (ResourceNotFoundException e) {
			log.error("EmailScheduler", "createNote", "failed");
		}
		log.error("EmailScedulerController : createNote : ended");
		return emailScheduler;
	}

	/**
	 * @author Electem2
	 * 
	 */
	@GetMapping("/sendEmailScheduler")
	public void sendEmailSchedular() {
		log.error("Start of sendEmailSchedular : started.... :");
		try {
			emailSchdulerService.sendEmails();
			log.info("End of sendEmailSchedular : emailScheduler sent successfully... ");
		} catch (ResourceNotFoundException e) {
			log.error("Error in sending EmailScheduler Error message :sendEmailSchedular:" + e.getMessage());
			log.error("sendEmailSchedular", "status", "failed");
		}
		log.error("EmailScedulerController : sendEmailSchedular : ended");
	}
	@GetMapping("/emailScheduler/{id}")
	public Optional<EmailScheduler> getEmailsForSending(@PathVariable(value = "id") Long emailScheduleId) {
		Optional<EmailScheduler> emailScheduler = null;
		try {
			log.info("emailSchedulerId :"+ emailScheduleId);
			if(emailScheduleId != null) {
				emailScheduler = emailSchedulerRepository.findById(emailScheduleId);
			}
		}catch (ResourceNotFoundException e) {
			log.info("Error in fetching EmailScheduler by Id:"+ emailScheduleId+ "Error message :"+ e.getMessage());
			throw new ResourceNotFoundException("EmailScheduleId", "id", emailScheduleId);
		}
		return emailScheduler;
	}
	
}
