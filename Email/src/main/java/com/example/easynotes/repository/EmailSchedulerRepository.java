package com.example.easynotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.EmailScheduler;

@Repository
public interface EmailSchedulerRepository extends JpaRepository<EmailScheduler, Long> {

	
	@Query(value="select * from email_scheduler e where e.send_status=1",nativeQuery=true)
	List<EmailScheduler> findByStatus();
	
	@Query(value="select e.user_id from email_scheduler e where e.manager_user_ids = :userId",nativeQuery=true)
	Integer userIdSearch(@Param(value="userId") String userId);
}