package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import models.Product;


@NoRepositoryBean
public interface ProductBaseRepository<T extends Product> extends JpaRepository<T, Long> {

}
