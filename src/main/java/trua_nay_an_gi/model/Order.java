package trua_nay_an_gi.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
//@Table(name="order")
public class Order {


	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)

	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private AppUser user_id;

	    private String note;

	    private LocalDateTime orderdate;

	    private String status;
	    private Long merchant_id;
	    private double totalPrice;
	    
	    
		public Order() {
		}


		public Order(Long id, AppUser appUser, String note, LocalDateTime orderdate, String status, Long merchant_id,
				double totalPrice) {
			this.id = id;
			this.user_id = appUser;
			this.note = note;
			this.orderdate = orderdate;
			this.status = status;
			this.merchant_id = merchant_id;
			this.totalPrice = totalPrice;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public AppUser getAppUser() {
			return user_id;
		}


		public void setAppUser(AppUser appUser) {
			this.user_id = appUser;
		}


		public String getNote() {
			return note;
		}


		public void setNote(String note) {
			this.note = note;
		}


		public LocalDateTime getOrderdate() {
			return orderdate;
		}


		public void setOrderdate(LocalDateTime orderdate) {
			this.orderdate = orderdate;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public Long getMerchant_id() {
			return merchant_id;
		}


		public void setMerchant_id(Long merchant_id) {
			this.merchant_id = merchant_id;
		}


		public double getTotalPrice() {
			return totalPrice;
		}


		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}
	    
		
	    
	
}
