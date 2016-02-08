package productFilters;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public abstract class DiskFilter extends ProductFilter{
	@NotEmpty
	protected List<String> diskInterfaceList, 
	diskSizeList;
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer minCapacity, maxCapacity;
	
	public DiskFilter(){
		super();
		minCapacity=new Integer(0);
		maxCapacity=new Integer(0);
	}

	public List<String> getDiskInterfaceList() {
		return diskInterfaceList;
	}

	public void setDiskInterfaceList(List<String> diskInterfaceList) {
		this.diskInterfaceList = diskInterfaceList;
	}

	public List<String> getDiskSizeList() {
		return diskSizeList;
	}

	public void setDiskSizeList(List<String> diskSizeList) {
		this.diskSizeList = diskSizeList;
	}

	public Integer getMinCapacity() {
		return minCapacity;
	}

	public void setMinCapacity(Integer minCapacity) {
		this.minCapacity = minCapacity;
	}

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
}
