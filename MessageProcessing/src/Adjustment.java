
public class Adjustment
{

	private String productName;
	private int productValue;
	private String operationType;
	
	public Adjustment(String productName, int productValue, String operationType) {
		this.productName = productName;
		this.productValue = productValue;
		this.operationType = operationType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}


}
