package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import models.Laptop;

@Transactional
public interface LaptopRepository extends ProductBaseRepository<Laptop>,JpaSpecificationExecutor<Laptop> {
	@Query("SELECT DISTINCT laptop.producer FROM Laptop laptop")
	public List<String> getLaptopProducers();
	
	@Query("SELECT DISTINCT laptop.processorName FROM Laptop laptop")
	public List<String> getProcessorNames();
	
	@Query("SELECT DISTINCT laptop.displayResolution FROM Laptop laptop")
	public List<String> getDisplayResolutions();	
}