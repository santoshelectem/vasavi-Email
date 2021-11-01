/**
 * 
 */
package com.example.demo.listener;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.ProductModel;

/**
 * @author electem 2
 *
 */
@Component
public class MyProductReader extends JdbcCursorItemReader<ProductModel> implements ItemReader<ProductModel>{
	public MyProductReader(@Autowired DataSource dataSource) {
		setDataSource(dataSource);
		setSql("SELECT id, name, dept,amount,time FROM product_model");
		setFetchSize(100);
		setRowMapper(new ProductRowMapper());
	}
	public class ProductRowMapper implements RowMapper<ProductModel> {
		@Override
		public ProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductModel productModel  = new ProductModel();
			productModel.setId(rs.getInt("id"));
			productModel.setName(rs.getString("name"));
			productModel.setAmount(rs.getInt("amount"));
			productModel.setDept(rs.getString("dept"));
			productModel.setTime(rs.getDate("Time"));
			return productModel;
		}

		
	}
}
