package com.example.easynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value="SELECT n FROM User n WHERE n.userId = :userId")
	User getNoteById(@Param(value="userId") Long userId);

	@Query(value="select e.email_id,es.user_id  from user e "+" inner join email_scheduler es"+
			 " on es.user_id=e.user_id where e.user_id=:userId",nativeQuery=true)
	User findByUserId(@Param(value="userId") Long userId);

	

}
