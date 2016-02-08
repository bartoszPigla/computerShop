package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import models.HardDisk;

@Transactional
public interface HardDiskRepository extends DiskBaseRepository<HardDisk>,JpaSpecificationExecutor<HardDisk> {
	@Query("SELECT DISTINCT d.producer FROM HardDisk d")
	public List<String> getHardDiskDiskProducers();
	
	@Query("SELECT DISTINCT d.diskInterface FROM HardDisk d") 
	public List<String> getHardDiskInterfaces();
	
	@Query("SELECT DISTINCT d.diskSize FROM HardDisk d")
	public List<String> getHardDiskSizes();
	
	@Query("SELECT DISTINCT d.rotationSpeed FROM HardDisk d")
	public List<Integer> getHardDiskRotationSpeeds();
	
	@Query("SELECT COUNT(d) FROM HardDisk d")
	public long count();
}
