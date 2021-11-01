/**
 * 
 */
package com.example.demo.listener;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.example.demo.model.ProductModel;

/**
 * @author electem 2
 *
 */
@Component
public class MyProductWriter extends FlatFileItemWriter<ProductModel>{
	public MyProductWriter() {
		setResource(new FileSystemResource("E:\\data\\outputing.csv"));
		setLineAggregator(getDelimitedLineAggregator());
	}
	
	public DelimitedLineAggregator<ProductModel> getDelimitedLineAggregator() {
		BeanWrapperFieldExtractor<ProductModel> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<ProductModel>();
		beanWrapperFieldExtractor.setNames(new String[] {"id", "name","dept","amount"});

		DelimitedLineAggregator<ProductModel> delimitedLineAggregator = new DelimitedLineAggregator<ProductModel>();
		delimitedLineAggregator.setDelimiter(",");
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
		return delimitedLineAggregator;
		
	}
}
