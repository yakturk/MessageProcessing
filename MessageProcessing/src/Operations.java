import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Operations {
	
	// keeps list of sale operations
	ArrayList<Sale> saleList;
	ArrayList<Adjustment> adjList;
	boolean acceptMsg = true;
	
	// keeps count of incoming message
	int countOfIncomingMsg = 0;
	
	public Operations()
	{
		saleList = new ArrayList<>();
		adjList = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param message
	 * gets a string message and parse it according to message types
	 * and then update the saleList
	 * @throws Exception 
	 */
	public  void processIncomingMessages(String message) throws Exception
	{
		// message can be null or empty string.
		// If it is, then the function return false 
		// that means the message could not be parsed
		if(message != null && !message.isEmpty())
		{
			String[] lines = message.split("\\n");
			for(int j=0; j< lines.length; j++)
			{
				String productType = null;
				int productValue = 0;
				// some messages consists counts of sales operation, 
				// so we need to keep it
				int salesCount = 0;
				String operationType = null;
				String newMessage = lines[j].toLowerCase().trim();
				
				// split the message by space
				String[] partsOfMsg = newMessage.split("\\s+");
				
				if(partsOfMsg[1].equals("sales") && partsOfMsg[2].equals("of"))
				{
					try 
					{
						salesCount = Integer.parseInt(partsOfMsg[0]);
					} 
					catch (Exception e) {
						System.out.println(" There is an error while converting string to integer");
					}
					
					productType = EnglishNoun.singularOf(partsOfMsg[3]);
					
					String value = partsOfMsg[5].substring(0, partsOfMsg[5].length()-1);
					productValue = Integer.parseInt(value);
					
					Sale sale =  new Sale(productType, productValue, salesCount);		
					saleList.add(sale);
				}
				
				else if(partsOfMsg[1].equals("at"))
				{
					productType = EnglishNoun.singularOf(partsOfMsg[0]);
					String value = partsOfMsg[2].substring(0, partsOfMsg[2].length()-1);
					productValue = Integer.parseInt(value);
					// this message is sent for only one sale operation
					salesCount = 1;
					
					Sale sale =  new Sale(productType, productValue, salesCount);		
					saleList.add(sale);
				}
				
				else
				{
					switch (partsOfMsg[0]) {
					case "add":
						operationType = "add";
						break;
					
					case "subtract":
						operationType = "subtract";
						break;
						
					case "multiply":
						operationType = "multiply";
						break;

					default:
						throw new Exception("Inccorrect message format");
					}
					
					if(operationType != null && saleList != null && !saleList.isEmpty())
					{
						// we have to keep value of the product as integer but it has 'p' string
						// so we have to remove it before converting message from string to integer
						String value = partsOfMsg[1].substring(0, partsOfMsg[1].length()-1);
						productValue = Integer.parseInt(value);			
						productType = EnglishNoun.singularOf(partsOfMsg[2]);
						int count=0;
						for(int i=0; i<saleList.size(); i++)
						{
							Sale sale = saleList.get(i);
							
							if(sale.getProductType().equals(productType))
							{
								if(operationType.equals("add"))
								{
									sale.setProductValue(sale.getProductValue()+productValue);
								}
								else if(operationType.equals("subtract"))
								{
									sale.setProductValue(sale.getProductValue()-productValue);
								}
								else if(operationType.equals("multiply"))
								{
									sale.setProductValue(sale.getProductValue()*productValue);
								}
								if(count ==0){
									Adjustment adjLog = new Adjustment(sale.getProductType(), productValue, operationType);
									this.adjList.add(adjLog);
									count=count+1;
								}
							}
						}
					}
				}
			}

			
		}
		else
			throw new Exception("message should not be null or empty");
	}
	
	public boolean createLog(String incomingMessage) throws Exception
	{
		if(!acceptMsg)
		{
			System.out.println("message count access to 50");
			return false;
		}
		processIncomingMessages(incomingMessage);
		countOfIncomingMsg++;
		
		SaleLog log = new SaleLog(saleList);
		if((countOfIncomingMsg%10) == 0)
		{
			log.writeLogToFile();
		}
		
		if((countOfIncomingMsg%50) == 0)
		{
			acceptMsg = false;
			log.writeAdjLogToFile(adjList);
			
		}
		return true;
	}

	public static void main(String[] args) 
	{	
		Operations opt =  new Operations();
		try {
			String filename = "C:/Users/yasemin/workspace/MessageProcessing/testData";
			String filename2 = "C:/Users/yasemin/workspace/MessageProcessing/testDataError1";
			String filename3 = "C:/Users/yasemin/workspace/MessageProcessing/testDataError2";
			String filename4 = "C:/Users/yasemin/workspace/MessageProcessing/testDataError3";
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String currentLine;
			boolean sendMsg = true;
			while((currentLine = br.readLine())!= null && sendMsg)
			{
				sendMsg = opt.createLog(currentLine);
			}
			if(!sendMsg)
			{
				System.out.println("the system could not accept messages after 50. incoming messages");
			}
			if(currentLine == null)
			{
				System.out.println("empty file");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
