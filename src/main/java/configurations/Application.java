package configurations;

import models.Customer;
import models.ProductOrder;
import models.SsdDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;

import java.util.Locale;

@SpringBootApplication
@ComponentScan(basePackages = {"configurations", "controllers", "models", "productFilters", "tests", "repositories", "services"})
@EntityScan(basePackages = {"models"})
@EnableJpaRepositories(basePackages = {"repositories"})
@EnableTransactionManagement
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository orderRepository;

    @Autowired
    public void addExampleUserToDb(){
        //customerRepository.save(ExampleEntityGenerator.createExampleCustomer(1L));
    }

    @Autowired
    public void addExampleOrderToDb(){
        productRepository.save(ExampleEntityGenerator.createExampleHardDiskList());
        productRepository.save(ExampleEntityGenerator.createExampleSsdDiskList());
        productRepository.save(ExampleEntityGenerator.createExampleLaptop());

        productRepository.save(ExampleEntityGenerator.createExampleLaptop());


        Customer customer=ExampleEntityGenerator.createExampleCustomer(1L);
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
    }
}
