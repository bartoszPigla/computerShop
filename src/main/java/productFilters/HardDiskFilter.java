package productFilters;

import java.util.List;

public class HardDiskFilter extends DiskFilter {
	protected List<Integer> rotationSpeedList;

	public List<Integer> getRotationSpeedList() {
		return rotationSpeedList;
	}

	public void setRotationSpeedList(List<Integer> rotationSpeedList) {
		this.rotationSpeedList = rotationSpeedList;
	}
}
