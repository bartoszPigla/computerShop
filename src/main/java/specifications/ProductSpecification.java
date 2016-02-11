package specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import productFilters.ProductFilter;

public class ProductSpecification<T> implements Specification<T> {
	protected ProductFilter filter;
	public ProductSpecification(ProductFilter filter) {
		this.filter=filter;
	}
	@Override
	public Predicate toPredicate(Root<T> product, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		int minPrice=filter.getMinPrice();
		int maxPrice=filter.getMaxPrice();
		List<String>diskProducerList=filter.getProducerList();
		Predicate p1=minPrice==maxPrice?cb.conjunction():cb.between(product.<Integer>get("price"), minPrice,maxPrice);
		return (diskProducerList==null)?p1:cb.and(p1,product.get("producer").in(diskProducerList));
	}
}
