package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import configurations.Application;
import configurations.ExampleEntityGenerator;
import controllers.ProductManagementController;
import models.Customer;
import models.HardDisk;
import models.Product;
import models.ProductOrder;
import models.SsdDisk;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProductControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductOrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setup() {
		ProductManagementController productController = new ProductManagementController();
		ReflectionTestUtils.setField(productController, "productRepository", productRepository);
		ReflectionTestUtils.setField(productController, "orderRepository", orderRepository);
		ReflectionTestUtils.setField(productController, "customerRepository", customerRepository);
		this.mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
	}
	@Test
	public void testDeleteProductFromAllOrdersForCustomer() throws Exception{
		Customer c1=ExampleEntityGenerator.createExampleCustomer(0);
		c1.setFirstName("bartek");
		
		ProductOrder o1=ExampleEntityGenerator.createExampleOrder(0);
		ProductOrder o2=ExampleEntityGenerator.createExampleOrder(0);
		
		SsdDisk p1=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		p1.setReadSpeed(10);
		p1.setWriteSpeed(20);
		
		o1.addProduct(p1);
		o2.addProduct(p1);
		
		o1.setPurchasingCustomer(c1);
		o2.setPurchasingCustomer(c1);
		
		c1.addOrder(o1);
		c1.addOrder(o2);
		
		productRepository.save(p1);
		
		customerRepository.save(c1);
		
		orderRepository.save(o1);
		orderRepository.save(o2);
		
		mockMvc
		.perform(delete("/adminPage/product/showAll/forCustomer/"+c1.getId()+"/"+p1.getId()));

		p1.removeOrder(o1);
		p1.removeOrder(o2);
		
		assertEquals(Long.valueOf(0L),productRepository.getCountOfProductByOrder(o1));
		
		System.out.println();
	}
	
	@Test
	public void testDeleteProductFromOrder() throws Exception{
		ProductOrder o1=ExampleEntityGenerator.createExampleOrder(0);
		
		SsdDisk p1=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		p1.setReadSpeed(10);
		p1.setWriteSpeed(20);
		
		HardDisk p2=(HardDisk)ExampleEntityGenerator.createExampleDisk(new HardDisk());
		p2.setRotationSpeed(7200);
		
		o1.addProduct(p1);
		o1.addProduct(p2);
		
		productRepository.save(Arrays.asList(p1,p2));
		orderRepository.save(o1);
		
		mockMvc
		.perform(delete("/adminPage/product/showAll/forOrder/"+o1.getId()+"/"+p1.getId()));
		
		assertEquals(Arrays.asList(p2), orderRepository.findOrderByIdAndFetchProductsEagerly(o1.getId()).getProducts());
		
		System.out.println();
	}
	
	@Test
	public void testGetExistingEditProduct() throws Exception{
		SsdDisk p1=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		p1.setReadSpeed(10);
		p1.setWriteSpeed(20);
		
		productRepository.save(p1);
		
		mockMvc
		.perform(get("/adminPage/product/edit/"+p1.getId()))
		.andExpect(status().isOk())
		.andExpect(model().attribute("operation", "edit"))
		.andExpect(model().attribute("ssdDisk", p1))
		.andExpect(view().name("adminPage/productForms/ssdDisk"));
	}
	/*
	@Test
	public void testGetNotExistingEditProduct() throws Exception{
		mockMvc
		.perform(get("/adminPage/product/edit/111"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("operation", "edit"))
		.andExpect(model().attribute("ssdDisk", p1))
		.andExpect(view().name("adminPage/productForms/ssdDisk"));
	}
	*/
	@Test
	public void testShowProductsForOrder() throws Exception{
		List<Object> list=ExampleEntityGenerator.createTwoProductsToOneOrder();
		Product p1=(Product)list.get(0);
		Product p2=(Product)list.get(1);
		ProductOrder o=(ProductOrder)list.get(2);
		 
		productRepository.save(Arrays.asList(p1,p2));
		orderRepository.save(o);
		
		mockMvc
			.perform(get("/adminPage/product/showAll/forOrder/"+o.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attribute("productList", Arrays.asList(p1,p2)))
			.andExpect(model().attribute("numberOfPages", 1L));
	}
	
	@Test
	public void testShowProductsForCustomer() throws Exception{		
		Customer c1=ExampleEntityGenerator.createExampleCustomer(0);
		c1.setFirstName("bartek");
		
		Customer c2=ExampleEntityGenerator.createExampleCustomer(0);
		c2.setFirstName("magda");
		
		ProductOrder o1=ExampleEntityGenerator.createExampleOrder(0);
		ProductOrder o2=ExampleEntityGenerator.createExampleOrder(0);
		
		SsdDisk p1=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		p1.setReadSpeed(10);
		p1.setWriteSpeed(20);
		
		HardDisk p2=(HardDisk)ExampleEntityGenerator.createExampleDisk(new HardDisk());
		p2.setRotationSpeed(7200);
		
		o1.addProduct(p1);
		o2.addProduct(p2);
		
		o1.setPurchasingCustomer(c1);
		o2.setPurchasingCustomer(c2);
		
		c1.addOrder(o1);
		c2.addOrder(o2);
		
		productRepository.save(p1);
		productRepository.save(p2);
		
		
		customerRepository.save(c1);
		customerRepository.save(c2);
		
		orderRepository.save(o1);
		orderRepository.save(o2);
		
		List<Customer> customerList=customerRepository.findAll();
		List<Product> productList=productRepository.findAll();
		List<ProductOrder> orderList=orderRepository.findAll();
		
		mockMvc
		.perform(get("/adminPage/product/showAll/forCustomer/"+c1.getId()))
		.andExpect(status().isOk())
		.andExpect(model().attribute("productList", Arrays.asList(p1)))
		.andExpect(model().attribute("numberOfPages", 1L));
	}
}
