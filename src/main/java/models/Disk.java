package models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@PrimaryKeyJoinColumn(name = "productId")
public abstract class Disk extends Product{
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	protected Integer capacity, weight;
	
	@NotEmpty
	protected String diskInterface,diskSize;

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDiskInterface() {
		return diskInterface;
	}

	public void setDiskInterface(String diskInterface) {
		this.diskInterface = diskInterface;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disk other = (Disk) obj;
		return producer==other.producer&&model==other.model&&id==other.id;
	}
	*/
}
