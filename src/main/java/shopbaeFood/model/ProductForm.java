package shopbaeFood.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
	private Long id;
	private String name;
	private String shortDescription;
	private String numberOrder;
	private Double oldPrice;
	private Double newPrice;
	private MultipartFile image;

	public ProductForm() {
	}

	public ProductForm(Long id, String name, String shortDescription, String numberOrder, Double oldPrice,
			Double newPrice, MultipartFile image) {
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.numberOrder = numberOrder;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
		this.image = image;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
