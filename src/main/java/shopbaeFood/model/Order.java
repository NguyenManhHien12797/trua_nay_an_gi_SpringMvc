package shopbaeFood.model;

import java.time.LocalDateTime;

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
	private String address;

	@Column(name = "deleteFlag")
	private boolean deleteFlag;

	public Order() {
	}

	public Order(Long id, AppUser user_id, String note, LocalDateTime orderdate, String status, Long merchant_id,
			double totalPrice, String address, boolean deleteFlag) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.note = note;
		this.orderdate = orderdate;
		this.status = status;
		this.merchant_id = merchant_id;
		this.totalPrice = totalPrice;
		this.address = address;
		this.deleteFlag = deleteFlag;
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

	public AppUser getUser_id() {
		return user_id;
	}

	public void setUser_id(AppUser user_id) {
		this.user_id = user_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
