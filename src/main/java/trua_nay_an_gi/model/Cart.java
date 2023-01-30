package trua_nay_an_gi.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
//@Table(name="cart")
public class Cart {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
//	    private int quantity;
//	    private  Double price;
//	    private Double totalPrice;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private AppUser user_id;
	    

	    @ManyToOne
	    @JoinColumn(name = "merchant_id")
	    private Merchant merchant;
	    
	    @OneToMany
	    private Set<CartDetail> carts;
	    
//	    @ManyToOne
//	    @JoinColumn(name = "product_id")
//	    private Product product_id;
	    
	    @Column(name = "deleteFlag")
	    private boolean deleteFlag;

		public Cart() {
		}

		public Cart(Long id, AppUser user_id, Merchant merchant, Set<CartDetail> carts, boolean deleteFlag) {
			this.id = id;
			this.user_id = user_id;
			this.merchant = merchant;
			this.carts = carts;
			this.deleteFlag = deleteFlag;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public AppUser getUser_id() {
			return user_id;
		}

		public void setUser_id(AppUser user_id) {
			this.user_id = user_id;
		}

		public Merchant getMerchant() {
			return merchant;
		}

		public void setMerchant(Merchant merchant) {
			this.merchant = merchant;
		}

		public Set<CartDetail> getCarts() {
			return carts;
		}

		public void setCarts(Set<CartDetail> carts) {
			this.carts = carts;
		}

		public boolean isDeleteFlag() {
			return deleteFlag;
		}

		public void setDeleteFlag(boolean deleteFlag) {
			this.deleteFlag = deleteFlag;
		}

		
		
	    
}
