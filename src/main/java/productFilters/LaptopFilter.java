package productFilters;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class LaptopFilter extends ProductFilter {
	@NotEmpty
	protected List<String> processorNameList, displayResolutionList;
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer minRam,minStorage,maxRam,maxStorage;
	
	public LaptopFilter(){
		super();
		minRam=new Integer(0);
		minStorage=new Integer(0);
		maxRam=new Integer(0);
		maxStorage=new Integer(0);
	}

	public List<String> getProcessorNameList() {
		return processorNameList;
	}

	public void setProcessorNameList(List<String> processorNameList) {
		this.processorNameList = processorNameList;
	}

	public List<String> getDisplayResolutionList() {
		return displayResolutionList;
	}

	public void setDisplayResolutionList(List<String> displayResolutionList) {
		this.displayResolutionList = displayResolutionList;
	}

	public Integer getMinRam() {
		return minRam;
	}

	public void setMinRam(Integer minRam) {
		this.minRam = minRam;
	}

	public Integer getMinStorage() {
		return minStorage;
	}

	public void setMinStorage(Integer minStorage) {
		this.minStorage = minStorage;
	}

	public Integer getMaxRam() {
		return maxRam;
	}

	public void setMaxRam(Integer maxRam) {
		this.maxRam = maxRam;
	}

	public Integer getMaxStorage() {
		return maxStorage;
	}

	public void setMaxStorage(Integer maxStorage) {
		this.maxStorage = maxStorage;
	}

}
