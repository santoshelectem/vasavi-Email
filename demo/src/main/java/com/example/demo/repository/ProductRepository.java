/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ProductModel;


/**
 * @author electem 2
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer>{

}
