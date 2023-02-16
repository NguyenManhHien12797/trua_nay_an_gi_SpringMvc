package shopbaeFood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quantity;
	private Double price;
	private Double totalPrice;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user_id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "deleteFlag")
	private boolean deleteFlag;

	public Cart() {
	}

	public Cart(Long id, int quantity, Double price, Double totalPrice, AppUser user_id, Product product,
			boolean deleteFlag) {
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.user_id = user_id;
		this.product = product;
		this.deleteFlag = deleteFlag;
	}

	public Cart(int quantity, double price, AppUser user, Product product, Double totalPrice, boolean deleteFlag) {
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.user_id = user;
		this.product = product;
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

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
