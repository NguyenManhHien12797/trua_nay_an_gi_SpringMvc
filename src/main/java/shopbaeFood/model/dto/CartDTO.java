package shopbaeFood.model.dto;

public class CartDTO {

	private Long id;
	private double price;
	private Long user_id;
	private Long product_id;

	public CartDTO() {
	}

	public CartDTO(Long id, double price, Long user_id, Long product_id) {
		this.id = id;
		this.price = price;
		this.user_id = user_id;
		this.product_id = product_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

}
