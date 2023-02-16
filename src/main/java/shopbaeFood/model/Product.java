package shopbaeFood.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DynamicUpdate
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String shortDescription;
	private String numberOrder;
	private Double oldPrice;
	private Double newPrice;
	private String image;
	private int quantity;

	@Column(name = "deleteFlag")
	private boolean deleteFlag;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "merchant_id")
	@JsonBackReference
	private Merchant merchant;

	public Product() {

	}

	public Product(Long id, String name, String shortDescription, String numberOrder, Double oldPrice, Double newPrice,
			String image, int quantity, boolean deleteFlag, Merchant merchant) {
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.numberOrder = numberOrder;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
		this.image = image;
		this.quantity = quantity;
		this.deleteFlag = deleteFlag;
		this.merchant = merchant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(String numberOrder) {
		this.numberOrder = numberOrder;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

}
