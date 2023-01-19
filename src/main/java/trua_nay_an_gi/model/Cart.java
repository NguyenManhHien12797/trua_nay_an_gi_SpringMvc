package trua_nay_an_gi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
//@Table(name="cart")
public class Cart {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private int quantity;
	    private  Double price;
	    private Double totalPrice;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private AppUser user_id;

	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product_id;

		public Cart() {
		}

		public Cart(Long id, int quantity, Double price, Double totalPrice, AppUser user, Product product) {
			this.id = id;
			this.quantity = quantity;
			this.price = price;
			this.totalPrice = totalPrice;
			this.user_id = user;
			this.product_id = product;
		}

		public Cart(int quantity, double price, AppUser user_id, Product product_id, Double totalPrice) {
			this.quantity = quantity;
			this.price = price;
			this.totalPrice = totalPrice;
			this.user_id = user_id;
			this.product_id = product_id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public AppUser getUser() {
			return user_id;
		}

		public void setUser(AppUser user) {
			this.user_id = user;
		}

		public Product getProduct() {
			return product_id;
		}

		public void setProduct(Product product) {
			this.product_id = product;
		}

	    
}
