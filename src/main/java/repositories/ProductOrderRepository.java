package repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import models.ProductOrder;

@Transactional
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
	@Query("SELECT productOrder FROM ProductOrder productOrder WHERE productOrder.purchasingCustomer.id=:customerId")
	public Page<ProductOrder> getOrdersForCustomer(@Param("customerId")long customerId,Pageable pageable);
	
	@Query("SELECT size(customer.orders) FROM Customer customer WHERE customer.id=:customerId")
	public Long countOfOrdersForCustomer(@Param("customerId")long customerId);
	
	@Query("SELECT productOrder FROM ProductOrder productOrder JOIN FETCH productOrder.products  WHERE productOrder.id = :productOrderId")
	public ProductOrder findOrderByIdAndFetchProductsEagerly(@Param("productOrderId") Long productOrderId);
	
	@Query("SELECT productOrder FROM ProductOrder productOrder JOIN FETCH productOrder.products")
	public List<ProductOrder> findAllOrdersAndFetchProductsEagerly();
}
