package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import models.SsdDisk;

@Repository
public interface SsdDiskRepository extends DiskBaseRepository<SsdDisk>,JpaSpecificationExecutor<SsdDisk>{
	@Query("SELECT DISTINCT d.producer FROM SsdDisk d")
	public List<String> getSsdDiskProducers();
	
	@Query("SELECT DISTINCT d.diskInterface FROM SsdDisk d") 
	public List<String> getSsdDiskInterfaces();
	
	@Query("SELECT DISTINCT d.diskSize FROM SsdDisk d")
	public List<String> getSsdDiskSizes();	
}