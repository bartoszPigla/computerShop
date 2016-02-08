package specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import productFilters.SsdDiskFilter;

public class SsdDiskSpecification<T> extends DiskSpecification<T> {
	public SsdDiskSpecification(SsdDiskFilter filter) {
		super(filter);
	}

	@Override
	public Predicate toPredicate(Root<T> ssdDisk, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		SsdDiskFilter ssdDiskFilter=(SsdDiskFilter)filter;
		Predicate predicate= super.toPredicate(ssdDisk, cq, cb);
		
		if(predicate==null)
			predicate=cb.conjunction();
		
		int 
			minReadSpeed=ssdDiskFilter.getMinReadSpeed(),
			maxReadSpeed=ssdDiskFilter.getMaxReadSpeed(),
			minWriteSpeed=ssdDiskFilter.getMinWriteSpeed(),
			maxWriteSpeed=ssdDiskFilter.getMaxWriteSpeed();
		
		predicate=minReadSpeed==maxReadSpeed?predicate:cb.and(predicate,cb.between(ssdDisk.<Integer>get("readSpeed"),minReadSpeed,maxReadSpeed));
		predicate=minWriteSpeed==maxWriteSpeed?predicate:cb.and(predicate,cb.between(ssdDisk.<Integer>get("readSpeed"),minWriteSpeed,maxWriteSpeed));
		return predicate;
	}
}
