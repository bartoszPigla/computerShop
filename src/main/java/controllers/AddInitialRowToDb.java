package controllers;

import configurations.ExampleEntityGenerator;
import models.Customer;
import models.ProductOrder;
import models.SsdDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;

@Controller
@RequestMapping("/adminPage/initial")
public class AddInitialRowToDb {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductOrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String createInitialDbRows() {
        productRepository.save(ExampleEntityGenerator.createExampleHardDiskList());
        productRepository.save(ExampleEntityGenerator.createExampleSsdDiskList());
        productRepository.save(ExampleEntityGenerator.createExampleLaptop());

        productRepository.save(ExampleEntityGenerator.createExampleLaptop());


        Customer customer=ExampleEntityGenerator.createExampleCustomer(0);
        ProductOrder order = ExampleEntityGenerator.createExampleOrder(0);

        SsdDisk d=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
        d.setReadSpeed(12);
        d.setWriteSpeed(23);
        order.addProduct(d);

        order.setPurchasingCustomer(customer);
        customer.addOrder(order);

        productRepository.save(d);
        customerRepository.save(customer);
        orderRepository.save(order);

        return "adminPage/adminPage";
    }
}
