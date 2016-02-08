package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ProductOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@DateTimeFormat(pattern = "dd-mm-yyyy hh:mm:ss")
	private Date orderDate=new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PURCHASING_CUSTOMER_ID")
	private Customer purchasingCustomer;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PRODUCTORDER_PRODUCT", 
			joinColumns = @JoinColumn(name = "PRODUCT_ORDER_ID") , 
			inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID") )
	private List<Product> products=new ArrayList<Product>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getPurchasingCustomer() {
		return purchasingCustomer;
	}

	public void setPurchasingCustomer(Customer purchasingCustomer) {
		this.purchasingCustomer = purchasingCustomer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product){
		products.add(product);
	}
	
	public void removeProduct(Product product){
		products.remove(product);
		product.removeOrder(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOrder other = (ProductOrder) obj;
		return id==other.id&&orderDate.equals(other.orderDate);
	}
}
