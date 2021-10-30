/**
 * 
 */
package com.example.demo.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.ProductModel;
import com.example.demo.repository.ProductRepository;

/**
 * @author electem 2
 *
 */
@Component
public class DBWriter implements ItemWriter<ProductModel> {
	private ProductRepository productRepository;

    @Autowired
    public DBWriter (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	@Override
	public void write(List<? extends ProductModel> items) throws Exception {
		System.out.println("Data Saved for Products: " + items);
		items.forEach(productRepository::save);
		
	}

}
