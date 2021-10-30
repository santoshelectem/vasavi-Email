/**
 * 
 */
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;




/**
 * @author electem 2
 *
 */
@Entity
public class ProductModel {
	 @Id
	    private Integer id;
	    private String name;
	    private String dept;
	    private Integer amount;
	    private Date time;
		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the dept
		 */
		public String getDept() {
			return dept;
		}
		/**
		 * @param dept the dept to set
		 */
		public void setDept(String dept) {
			this.dept = dept;
		}
		/**
		 * @return the amount
		 */
		public Integer getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(Integer amount) {
			this.amount = amount;
		}
		
		/**
		 * @param id
		 * @param name
		 * @param dept
		 * @param amount
		 * @param time
		 */
		public ProductModel(Integer id, String name, String dept, Integer amount, Date time) {
			super();
			this.id = id;
			this.name = name;
			this.dept = dept;
			this.amount = amount;
			this.time = time;
		}
		
		/**
		 * 
		 */
		public ProductModel() {
			super();
		}
		@Override
		public String toString() {
			return "ProductModel [id=" + id + ", name=" + name + ", dept=" + dept + ", amount=" + amount + ", time="
					+ time + "]";
		}
		/**
		 * @return the time
		 */
		public Date getTime() {
			return time;
		}
		/**
		 * @param time the time to set
		 */
		public void setTime(Date time) {
			this.time = time;
		}
	   
	    
}
