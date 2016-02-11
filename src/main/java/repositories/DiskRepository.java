package repositories;

import org.springframework.transaction.annotation.Transactional;

import models.Disk;

@Transactional
public interface DiskRepository extends DiskBaseRepository<Disk> {

}
