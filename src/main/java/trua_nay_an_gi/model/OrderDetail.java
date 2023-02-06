package trua_nay_an_gi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetail {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name= "product_id")
	    private Product product;

	    @ManyToOne
	    @JoinColumn(name="order_id")
	    private Order order;
	    
	    private int quantity;
	    
	    @Column(name = "deleteFlag")
	    private boolean deleteFlag;

		public OrderDetail() {
		}

		public OrderDetail(Long id, Product product, Order order, int quantity, boolean deleteFlag) {
			super();
			this.id = id;
			this.product = product;
			this.order = order;
			this.quantity = quantity;
			this.deleteFlag = deleteFlag;
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

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}
	    
		
	    
}
