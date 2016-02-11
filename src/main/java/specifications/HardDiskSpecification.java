package specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import productFilters.HardDiskFilter;

public class HardDiskSpecification<T> extends DiskSpecification<T> {
	public HardDiskSpecification(HardDiskFilter filter) {
		super(filter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Predicate toPredicate(Root<T> hardDisk, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		HardDiskFilter hardDiskFilter=(HardDiskFilter)filter;
		Predicate predicate= super.toPredicate(hardDisk, cq, cb);
		
		if(predicate==null)
			predicate=cb.conjunction();
		
		List<Integer>rotationSpeedList=hardDiskFilter.getRotationSpeedList();
		return (rotationSpeedList==null)?predicate:cb.and(predicate,hardDisk.get("rotationSpeed").in(rotationSpeedList));
	}
}
