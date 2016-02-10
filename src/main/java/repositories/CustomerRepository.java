package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import models.Customer;
import models.Product;
import models.ProductOrder;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query("SELECT customer.orders FROM Customer customer WHERE customer.id=:customerId")
	List<ProductOrder> getAllOrders(@Param("customerId")long customerId);

	@Query("SELECT customer FROM Customer customer WHERE customer.eMail=:eMail")
	Customer findByeMail(@Param("eMail")String eMail);
}
