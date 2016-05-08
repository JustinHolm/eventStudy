package eventStudy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class priceProvider {

	public static void main(String[] args) throws Exception {


		PrintWriter writer = new PrintWriter("C://Temp//es//PriceData.csv", "UTF-8");
		writer.println("Ticker,Price1,Price2");

		String dateToparse = "May 5, 2014";
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
		Date dateFound = sdf.parse(dateToparse);

		String dateToparse2 = "May 5, 2015";
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMMMM dd, yyyy");
		Date dateFound2 = sdf2.parse(dateToparse2);

		System.out.println("Price Move Check");
		System.out.println(getMove("BMO",dateFound,dateFound2));

		String[] tickers =  {"BMO","IMO","BCE","ECA","EMA","GC","MBT","MFC","PWT","S","ATH","SU","T","BNS","TA","TRP","WTE"};

		for (int i = 0; i < tickers.length ; i++){
			System.out.println(tickers[i] + " avg=" + getAvgMove(tickers[i],dateFound,dateFound2));
		}

		for (int i = 0; i < tickers.length ; i++){
			System.out.println(tickers[i] + " move=" + getMove(tickers[i],dateFound,dateFound2));
		}

		//System.out.printf("%,6.2f",getAvgMove("QLT",dateFound,dateFound2)*100);

		System.out.println();
		System.out.println("Price Spread Check:");
		System.out.println(getSpread("BMO","ATA",dateFound,dateFound2));


		writer.println("Day Price Checks");
		writer.println(getPrice("BMO",dateFound));
		writer.println(getPrice("BMO",dateFound2));
		writer.println(getPrice("GBT",dateFound));
		writer.println(getPrice("QLT",dateFound));
		writer.println(getPrice("LAM",dateFound));
		writer.println(getPrice("NAL",dateFound));
		writer.println(getPrice("MFC",dateFound));
		writer.println(getPrice("KFS",dateFound));
		writer.println(getPrice("DPM",dateFound));
		writer.println(getPrice("IMG",dateFound));
		writer.println(getPrice("ATA",dateFound));

		writer.close();
	}
	public static double getSpread(String ticker1,String ticker2, Date priceDateFrom, Date priceDateTo){

		double priceSpread = 0;

		try {
			double priceTo1 = getPrice(ticker1, priceDateTo);
			double priceFrom1 = getPrice(ticker1, priceDateFrom);
			double priceTo2 = getPrice(ticker2, priceDateTo);
			double priceFrom2 = getPrice(ticker2, priceDateFrom);
			priceSpread = (priceTo1/priceFrom1 - 1) - (priceTo2/priceFrom2 - 1);
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		return priceSpread;	
	}
	public static double getAvgMove(String ticker, Date priceDateFrom, Date priceDateTo){

		ArrayList<Double> priceSeries = new ArrayList<Double>(); 
		ArrayList<Double> priceSeriesPerc = new ArrayList<Double>();
		double getAvgMove = 0;

		try {
			for (int i = 1; i < 10; i++){
				priceSeries.add(priceWithOffset(ticker,priceDateFrom, i ));
			}

			double priceTo = getPrice(ticker, priceDateTo);
			for (int i = 0;i< priceSeries.size();i++){
				if (i != 0) priceSeriesPerc.add(priceSeries.get(i)/priceSeries.get(i-1)-1);
			}
			for (int i = 0; i < priceSeriesPerc.size();i++){
				getAvgMove = getAvgMove+ priceSeriesPerc.get(i);
			}

			getAvgMove = getAvgMove/priceSeriesPerc.size();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return getAvgMove;	
	}
	public static double getMove(String ticker, Date priceDateFrom, Date priceDateTo){

		double priceMove = 0;

		try {
			double priceTo = getPrice(ticker, priceDateTo);
			double priceFrom = getPrice(ticker, priceDateFrom);
			priceMove = priceTo/priceFrom - 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return priceMove;	
	}
	public static double getPrice(String ticker,Date priceDate) throws IOException{

		double price = 0;
		try {
			File file2 = new File("C://Temp//es//data//" + ticker + ".TO.csv");
			FileReader histprices = new  FileReader(file2);
			StringBuffer priceBuffer= new StringBuffer();
			int numPriceRead;
			String tmp2;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateinput;
			char[] charArray2 = new char[1024];
			while ((numPriceRead = histprices.read(charArray2)) > 0) {
				priceBuffer.append(charArray2, 0, numPriceRead);
			}
			int j = 0;
			for(int i =0;i < priceBuffer.length(); i++){
				if(priceBuffer.charAt(i) == '\n'){
					Date date;
					try{
						String str = priceBuffer.substring(j+1, j+11);
						String tmpstr;
						char c = str.charAt(0);

						if (c != 'a') {
							date = dateFormat.parse(str);
							if (date.equals(priceDate)){
								tmpstr = priceBuffer.substring(j, i-1);
								String delims = ",";
								String[] tokens = tmpstr.split(delims);
								price = Double.parseDouble(tokens[6]);
							}
						}
					} 
					catch (ParseException e){
						return 999;
						//e.printStackTrace();
					}
					j = i;
				}					   
			}
			histprices.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return 999;
		} finally {
		}
		return price;
	}
	public static double priceWithOffset(String ticker,Date priceDate,int offset){
		int x = 30;
		double price = 0;

		try{

			Calendar cal = Calendar.getInstance();
			cal.setTime(priceDate);
			cal.add(Calendar.DATE,offset);
			if(cal.get(Calendar.DAY_OF_WEEK) == 1) cal.add(Calendar.DATE,1);
			if(cal.get(Calendar.DAY_OF_WEEK) == 7) cal.add(Calendar.DATE,2);

			Date laterDate = cal.getTime();		   
			//System.out.println("Divdend noted and date found: " + newsdate + " price was: " + priceProvider.getPrice(ticker, newsdate) + " and later price was: " + priceProvider.getPrice(ticker, laterDate) + " on " + laterDate);
			price = getPrice( ticker, laterDate);
		}
		catch(Exception NullPointerException){
			System.out.println("error with " +ticker);
		}
		finally{	   
		}   
		return price;
	}
	public static double getVolume(String ticker,int days){
		double vol = 0;

		return vol;

	}
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
}
