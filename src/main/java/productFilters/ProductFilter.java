package productFilters;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public abstract class ProductFilter {
	@NotEmpty
	protected List<String> producerList;
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer minPrice, maxPrice;
	
	public ProductFilter(){
		minPrice=new Integer(0);
		maxPrice=new Integer(0);
	}

	public List<String> getProducerList() {
		return producerList;
	}

	public void setProducerList(List<String> producerList) {
		this.producerList = producerList;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
}
