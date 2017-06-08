
public class Sale {

	private String productType;
	private int productValue;
	private int saleCount;
	
	public Sale(String productType, int productValue, int saleCount)
	{
		
		this.productType = productType;
		this.productValue = productValue;
		this.saleCount = saleCount;
	}
	
	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public int getProductValue() {
		return productValue;
	}


	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	public int getSaleCount()
	{
		return this.saleCount;
	}
	
	public void setSaleCount(int salecount)
	{
		this.saleCount = salecount;
	}
}
