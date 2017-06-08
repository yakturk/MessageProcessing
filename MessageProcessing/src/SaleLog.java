import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class SaleLog
{

	// I do not want that anybody can access and change these variables
	// So I created variables as private
	private HashMap<String, int[]> detailedProductMap;
	private ArrayList<Sale> saleList;
	private String log, adjLog;
	private HashMap<String, int[]> detailedAdjustmentList;
	
	public SaleLog(ArrayList<Sale> saleList)
	{
		this.saleList = saleList;
		detailedProductMap = new HashMap<>();
		detailedAdjustmentList = new HashMap<>();
		log = null; 
		createDetailedProductMap();
	}

	private void createDetailedProductMap()
	{
		Sale sale;
		int totalValueOfProduct=0;
		String productName;
		int numberOfSalesOfProduct = 0;
		
		// this will be added to hashMap
		// it keeps number of sales of product and total value of product
		int detailsOfCurrProduct[];
		int detailsOfProductInMap[];
		for(int i=0; saleList!= null && i<saleList.size(); i++)
		{
			// get item in the current index of the list
			sale = saleList.get(i);
			// get values of the Sale in the current index
			totalValueOfProduct = sale.getProductValue();
			productName = sale.getProductType();
			numberOfSalesOfProduct=sale.getSaleCount();
			detailsOfCurrProduct = new int[2];
			
			detailsOfCurrProduct[0] = numberOfSalesOfProduct;
			detailsOfCurrProduct[1] = totalValueOfProduct;
			
			if(!detailedProductMap.containsKey(productName))
			{
				detailedProductMap.put(productName, detailsOfCurrProduct);
			}
			else
			{
				detailsOfProductInMap = detailedProductMap.get(productName);
				detailsOfCurrProduct[0] = detailsOfCurrProduct[0] + detailsOfProductInMap[0];
				detailsOfCurrProduct[1] = detailsOfCurrProduct[1] + detailsOfProductInMap[1];
				detailedProductMap.put(productName, detailsOfCurrProduct);
			}		
			// clear all variable that will be used for another for item
			sale = null;
			totalValueOfProduct = 0;
			productName = null;
			numberOfSalesOfProduct = 0; 
			detailsOfCurrProduct = null;
			detailsOfProductInMap = null;
		}
		createLog();
	}
	
	private void createLog()
	{
		if(detailedProductMap != null && !detailedProductMap.isEmpty())
		{
			Iterator<Entry<String, int[]>> iterator = detailedProductMap.entrySet().iterator();
			String line = "";
			while(iterator.hasNext())
			{
				Entry<String, int[]> item = iterator.next();
				int details[] = (int[]) item.getValue();
				line += "Number of sales of " + item.getKey().toUpperCase() + " is " + details[0] + " and total value is " + details[1] + " \r\n";
			}
			this.log = line;
		}
	}
	
	public void writeLogToFile() throws Exception
	{
		if(log == null)
		{
			throw new Exception("Log could not be created");
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("log.txt"));
			out.write(this.log);
			out.close();
			
		} catch (Exception e) {
			throw new Exception("Error occured while writing file");
		}	
	}
	
	@SuppressWarnings("unchecked")
	public void createAdjustmentLog(ArrayList<Adjustment> adjList)
	{
		Collections.sort(adjList, new Comparator<Adjustment>() {

			@Override
			public int compare(Adjustment o1, Adjustment o2) {
				return o1.getProductName().compareTo(o2.getProductName());
			}
		});
		
		String line = "Product Type \t Product Value \t Operation Type  \r\n";
		for(int i =0; i<adjList.size(); i++)
		{
			Adjustment adj =  adjList.get(i);
			line += adj.getProductName() + "\t" + adj.getProductValue() + "\t" + adj.getOperationType() + " \r\n";
		}
		adjLog = line;
	}
	

	
	public void writeAdjLogToFile(ArrayList<Adjustment> adjList) throws Exception
	{
		createAdjustmentLog(adjList);
		if(adjLog == null)
		{
			throw new Exception("Log could not be created");
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("adjLog.txt"));
			out.write(this.adjLog);
			out.close();
			
		} catch (Exception e) {
			throw new Exception("Error occured while writing file");
		}
	}

	public ArrayList<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(ArrayList<Sale> saleList) {
		this.saleList = saleList;
	}	
}
