package repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import models.Product;
import models.ProductOrder;

@Transactional
public interface ProductRepository extends ProductBaseRepository<Product> {
	@Query("SELECT productOrder.products FROM ProductOrder productOrder WHERE productOrder.id=:productOrderId")
	public List<Product> getProductsFromOrder(@Param("productOrderId")long productOrderId);
	
	@Query("select distinct product from Product product inner join product.orders productOrder where productOrder.purchasingCustomer.id = :customerId")
	public Page<Product> getProductsFromCustomer(@Param("customerId")long customerId,Pageable pageable);
	
	@Query("select COUNT(DISTINCT product) from Product product inner join product.orders productOrder where productOrder.purchasingCustomer.id = :customerId")
	public Long countOfProductsFromCustomer(@Param("customerId")long customerId);
	
	@Query("SELECT product FROM Product product JOIN FETCH product.orders  WHERE product.id = :productId")
	public Product findByProductIdAndFetchOrdersEagerly(@Param("productId") Long productId);
	
	@Query("SELECT product FROM Product product WHERE :productOrder MEMBER OF product.orders")
	public Page<Product> getProductByOrder(@Param("productOrder")ProductOrder productOrder, Pageable pageable);
	
	@Query("SELECT COUNT(product) FROM Product product WHERE :productOrder MEMBER OF product.orders")
	public Long getCountOfProductByOrder(@Param("productOrder")ProductOrder productOrder);
	
	@Query("SELECT COUNT(product) FROM Product product WHERE :productOrder MEMBER OF product.orders")
	public Long countOfProductsFromOrder(@Param("productOrder")ProductOrder productOrder);


	@Query("SELECT product FROM Product product JOIN FETCH product.orders  WHERE product.id = :productId")
	Product findByIdAndFetchOrdersEagerly(@Param("productId") Long productId);
	
	/*
	 * 	@Query("SELECT DISTINCT d.diskInterface FROM SsdDisk AS d") 
	public List<String> getAllInterfaces();
	
	@Query("SELECT DISTINCT d.diskSize FROM SsdDisk AS d")
	public List<String> getAllDiskSizes();
	
	@Query("SELECT DISTINCT d.dimensions FROM SsdDisk AS d")
	public List<String> getAllDimensions();
	 */
}