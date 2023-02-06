package trua_nay_an_gi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
//@Table(name="product_cart")
public class ProductCartMap {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product;
	    @ManyToOne
	    @JoinColumn(name = "cart_id")
	    @JsonBackReference
	    private Cart cart;
		public ProductCartMap() {
		}
		public ProductCartMap(Long id, Product product, Cart cart) {
			this.id = id;
			this.product = product;
			this.cart = cart;
		}
		public ProductCartMap(Cart cart, Product product) {
			this.product = product;
			this.cart = cart;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public Cart getCart() {
			return cart;
		}
		public void setCart(Cart cart) {
			this.cart = cart;
		}
	    
	    
	    
}
