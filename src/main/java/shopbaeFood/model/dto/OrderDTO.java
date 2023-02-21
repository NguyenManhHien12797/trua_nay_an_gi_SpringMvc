package shopbaeFood.model.dto;

public class OrderDTO {
	
	private Long id;
	private String user;
	private String status;
	private String merchant;
	public OrderDTO() {
	}
	public OrderDTO(Long id, String user, String status, String merchant) {
		this.id = id;
		this.user = user;
		this.status = status;
		this.merchant = merchant;
	}
	public OrderDTO(Long id, String user, String status) {
		this.id = id;
		this.user = user;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	
	

}
