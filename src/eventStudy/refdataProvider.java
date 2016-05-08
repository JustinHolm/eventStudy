package eventStudy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class refdataProvider {
	
	public static void main(String[] args) throws Exception {

	//String [][] refdata = getRefData();
		String tmp = refdataProvider.getName("AAV");
		System.out.println(tmp);
	}
	
	public static String getName(String ticker) throws IOException{
		
		String [][] refdata = getRefData();
		String stockName = "";
		String tmp = "";
		
		for (Integer i = 1; i< 500 ; i++){
			if (refdata[i][0] != null){
				tmp = refdata[i][0].toString();
			}
			
			//System.out.println(refdata[i][0].toString());
			if (ticker.equals(tmp)) {
				stockName = refdata[i][1];
			}
		}	
		return stockName;	
	}
	public static String getIndustry(String ticker) throws IOException{
			
			String [][] refdata = getRefData();
			String getIndustry = "";
			String tmp = "";
			
			for (Integer i = 1; i< 500 ; i++){
				if (refdata[i][0] != null){
					tmp = refdata[i][0].toString();
				}
				
				//System.out.println(refdata[i][0].toString());
				if (ticker.equals(tmp)) {
					getIndustry = refdata[i][2];
				}
			}	
			return getIndustry;	
		}
	public static String [] getStockList() throws IOException{
		
		String [][] refdata = getRefData();
		ArrayList<String> stocks = new ArrayList<String>();
		String stockList[]= {};
		
		for (Integer i = 1; i< 500 ; i++){
			if (refdata[i][0] != null){
				stocks.add(refdata[i][0].toString());
			}
		
			stockList=stocks.toArray(new String[stocks.size()]);	

		}	
		return stockList;	
	}
	
	public static String[][] getRefData() throws IOException{
		String[][] RefData =  {{"Stock","Industry","Name"},{"x","y","z"}};
		String[][] RefData2 = new String[1000][3];

		String[] temp;
		double price = 0;
		try {
			BufferedReader CSVFile = new BufferedReader(new FileReader("stockRefData.csv"));

			//BufferedReader datafile = readDataFile("stockRefData.csv");
			
			String dataRow = CSVFile.readLine();
		      // Read the number of the lines in .csv file 
		      // i = row of the .csv file
		      int i = 0; 
		      
		      
		      while (dataRow != null){
		    	  
			    	  i++;
			          dataRow = CSVFile.readLine();
			          if (dataRow != null){
				        	  
				          temp = dataRow.split(",");
				          RefData2[i][0] = temp[0];
				          RefData2[i][1] = temp[1];
				          RefData2[i][2] = temp[2];
		    	  }
		        }

		      CSVFile.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		} finally {
		}
		return RefData2;
	}
}
