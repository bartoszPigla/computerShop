package specifications;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import productFilters.LaptopFilter;

public class LaptopSpecification<T> extends ProductSpecification<T> {
	public LaptopSpecification(LaptopFilter filter) {
		super(filter);
	}

	@Override
	public Predicate toPredicate(Root<T> laptop, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		LaptopFilter laptopFilter=(LaptopFilter)filter;
		
		int minRam=laptopFilter.getMinRam();
		int maxRam=laptopFilter.getMaxRam();
		int minStorage=laptopFilter.getMinStorage();
		int maxStorage=laptopFilter.getMaxStorage();
		
		List<String> processorNames=laptopFilter.getProcessorNameList();
		List<String> displayResolutionLists=laptopFilter.getDisplayResolutionList();
		
		Predicate predicate= super.toPredicate(laptop, cq, cb);
		
		if(predicate==null)
			predicate=cb.conjunction();
		
		predicate=minRam==maxRam?predicate:cb.and(predicate,cb.between(laptop.<Integer>get("ram"), minRam,maxRam));
		predicate=minStorage==maxStorage?predicate:cb.and(predicate,cb.between(laptop.<Integer>get("storage"), minStorage,maxStorage));
		
		predicate= processorNames==null?predicate:cb.and(predicate,laptop.get("processorName").in(processorNames));
		predicate= displayResolutionLists==null?predicate:cb.and(predicate,laptop.get("displayResolution").in(displayResolutionLists));
		return predicate;
	}
}
