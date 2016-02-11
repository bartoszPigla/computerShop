package models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@PrimaryKeyJoinColumn(name = "productId")
public class SsdDisk extends Disk {
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	private Integer readSpeed, writeSpeed;

	public Integer getReadSpeed() {
		return readSpeed;
	}

	public void setReadSpeed(Integer readSpeed) {
		this.readSpeed = readSpeed;
	}

	public Integer getWriteSpeed() {
		return writeSpeed;
	}

	public void setWriteSpeed(Integer writeSpeed) {
		this.writeSpeed = writeSpeed;
	}
}
