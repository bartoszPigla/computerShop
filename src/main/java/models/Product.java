package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import validators.ImageUrl;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;
	
	@NotEmpty
	@Length(max=30)
	protected String producer;
	
	@Length(max=20)
	@NotEmpty
	protected String model;
	
	@NotEmpty
	@ImageUrl(imageType={"img","jpg","jpeg","png"})
	protected String imageDirectory;

	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer price;
	
	@ManyToMany(mappedBy="products",fetch = FetchType.LAZY)
	protected List<ProductOrder> orders=new ArrayList<ProductOrder>();
	
	@NotEmpty
	protected String description,dimensions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImageDirectory() {
		return imageDirectory;
	}

	public void setImageDirectory(String imageDirectory) {
		this.imageDirectory = imageDirectory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public List<ProductOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ProductOrder> orders) {
		this.orders = orders;
	}
	
	public void addOrder(ProductOrder order){
		orders.add(order);
	}
	
	public void removeOrder(ProductOrder order){
		orders.remove(order);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dimensions == null) ? 0 : dimensions.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((imageDirectory == null) ? 0 : imageDirectory.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
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
		Product other = (Product) obj;
		return id == other.id && producer == other.producer && model == other.model;
	}






}
