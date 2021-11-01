/**
 * 
 */
package com.example.demo.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.model.ProductModel;

/**
 * @author electem 2
 *
 */
@Component
public class Processor implements ItemProcessor<ProductModel, ProductModel>{
	private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
	 private static final Map<String, String> DEPT_NAMES =
	            new HashMap<>();
	 public Processor() {
	        DEPT_NAMES.put("1", "Technology");
	        DEPT_NAMES.put("2", "Operations");
	        DEPT_NAMES.put("3", "Accounts");
	    }
	
		 @Override
		 public ProductModel process(ProductModel item) throws Exception {
		        String deptCode = item.getDept();
		        String dept = DEPT_NAMES.get(deptCode);
		        item.setDept(dept);
		        item.setTime(new Date());
		        System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
		        return item;
		    
	}
	/*
	 * public WriterSO process(final ReaderSO item) throws Exception {
	 * LOGGER.info("Processing Record: {}", item); WriterSO writerSo = new
	 * WriterSO(); writerSo.setId(item.getId());
	 * writerSo.setFullName(item.getFirstName() + " " + item.getLastName());
	 * writerSo.setRandomNum(String.valueOf(Math.random()).substring(3, 8));
	 * LOGGER.info("Processed Writer: {}", writerSo); return writerSo; }
	 */
}
