package specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import productFilters.DiskFilter;

public class DiskSpecification<T> extends ProductSpecification<T> {

	public DiskSpecification(DiskFilter filter) {
		super(filter);
	}

	@Override
	public Predicate toPredicate(Root<T> disk, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		DiskFilter diskFilter=(DiskFilter)filter;
		int minCapacity=diskFilter.getMinCapacity(), maxCapacity=diskFilter.getMaxCapacity();
		List<String> diskInterfaceList=diskFilter.getDiskInterfaceList();
		List<String> diskSizeList=diskFilter.getDiskSizeList();
		
		Predicate predicate=super.toPredicate(disk, cq, cb);
		if(predicate==null)
			predicate=cb.conjunction();
		
		predicate= minCapacity==maxCapacity?predicate:cb.and(predicate,cb.between(disk.<Integer>get("capacity"), minCapacity,maxCapacity));
		predicate=(diskInterfaceList==null)?predicate:cb.and(predicate,disk.<String>get("diskInterface").in(diskInterfaceList));
		predicate=(diskSizeList==null)?predicate:cb.and(predicate,disk.<String>get("diskSize").in(diskSizeList));
		
		return predicate;
	}

}
