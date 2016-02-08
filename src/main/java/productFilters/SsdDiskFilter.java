package productFilters;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class SsdDiskFilter extends DiskFilter {
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer minWriteSpeed,minReadSpeed, maxReadSpeed,maxWriteSpeed;
	
	public SsdDiskFilter(){
		super();
		minWriteSpeed=new Integer(0);
		minReadSpeed=new Integer(0);
		maxReadSpeed=new Integer(0);
		maxWriteSpeed=new Integer(0);
	}

	public Integer getMinReadSpeed() {
		return minReadSpeed;
	}

	public void setMinReadSpeed(Integer minReadSpeed) {
		this.minReadSpeed = minReadSpeed;
	}

	public Integer getMinWriteSpeed() {
		return minWriteSpeed;
	}

	public void setMinWriteSpeed(Integer minWriteSpeed) {
		this.minWriteSpeed = minWriteSpeed;
	}

	public Integer getMaxReadSpeed() {
		return maxReadSpeed;
	}

	public void setMaxReadSpeed(Integer maxReadSpeed) {
		this.maxReadSpeed = maxReadSpeed;
	}

	public Integer getMaxWriteSpeed() {
		return maxWriteSpeed;
	}

	public void setMaxWriteSpeed(Integer maxWriteSpeed) {
		this.maxWriteSpeed = maxWriteSpeed;
	}
}
