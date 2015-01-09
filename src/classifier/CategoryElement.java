package classifier;

public class CategoryElement {

	private String category;
	
	private double yesOnYes,noOnYes,yesOnNo,noOnNO;
	
	
	
	public CategoryElement(String category, double yesOnYes, double noOnYes,
			double yesOnNo, double noOnNO) {
		super();
		this.category = category;
		this.yesOnYes = yesOnYes;
		this.noOnYes = noOnYes;
		this.yesOnNo = yesOnNo;
		this.noOnNO = noOnNO;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getYesOnYes() {
		return yesOnYes;
	}

	public void setYesOnYes(double yesOnYes) {
		this.yesOnYes = yesOnYes;
	}

	public double getNoOnYes() {
		return noOnYes;
	}

	public void setNoOnYes(double noOnYes) {
		this.noOnYes = noOnYes;
	}

	public double getYesOnNo() {
		return yesOnNo;
	}

	public void setYesOnNo(double yesOnNo) {
		this.yesOnNo = yesOnNo;
	}

	public double getNoOnNO() {
		return noOnNO;
	}

	public void setNoOnNO(double noOnNO) {
		this.noOnNO = noOnNO;
	}

	
	
	
	
}
