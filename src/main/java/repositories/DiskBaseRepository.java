package repositories;

import org.springframework.data.repository.NoRepositoryBean;

import models.Disk;

@NoRepositoryBean
public interface DiskBaseRepository<T extends Disk> extends ProductBaseRepository<T> {

}
