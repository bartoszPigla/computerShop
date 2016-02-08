package models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@PrimaryKeyJoinColumn(name = "productId")
public class HardDisk extends Disk {
	@NotNull
	@Range(min = 0, max = Integer.MAX_VALUE)
	private Integer rotationSpeed;

	public Integer getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(Integer rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
}
