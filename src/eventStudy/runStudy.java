package eventStudy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import eventStudy.newStory.newsCategory;

public class runStudy {

	public static void main (String args[]) throws IOException  {
		//Find all stories to run in the study C:\Temp\es
		File[] files = new File("C://Temp//es//News//").listFiles();
		Boolean verbose = false;
		final char QUOTE = '"';
		PrintWriter writer = new PrintWriter("C://Temp//es//output.csv", "UTF-8");
		writer.println("Ticker,Name,Price on News,Price 21 days after,spread,Sentiment,News Date,Link,Date Count,Avg Move,Sentiment2,Category,Related Stocks,Stock List, Country List");
//http://web.tmxmoney.com/article.php?newsid=82868023&qm_symbol=ATD.B
		for (File file : files) {
			if (file.isFile()) {
				boolean b;
				int studyOffset = 20;

				//load news story
				String tmx = "http://web.tmxmoney.com/article.php?";
				String tmp = readNews(file.getAbsoluteFile().toString());
				String ticker = file.getName().toString();
				String prefix = "qm_symbol=";
				ticker = ticker.substring(ticker.indexOf(prefix)+ prefix.length());
				
				newStory ns = new newStory();
				ns.setStory(readNews(file.getAbsoluteFile().toString()));
				ns.ticker = ticker;
				ns.setCompanyName();
				ns.setIndustry();
				ns.newsDate = dateParse.getDate(tmp,false);
				ns.sentiment = eventProvider.sentimentNews(ns.newsText.toString());
				ns.setCategory();

				Date newsdate = dateParse.getDate(tmp,false);
				
				b =  eventProvider.simpleDivEvent(tmp);
				ArrayList<String> relS = eventProvider.relStocks(ns.newsText.toString(),ns.ticker);
				ArrayList<String> relC = eventProvider.relcountry(ns.newsText.toString());
				
				StringBuilder sb = new StringBuilder();
				
				try{
					if (newsdate == null){
					}
					else{
						Calendar cal = Calendar.getInstance();
						cal.setTime(newsdate);
						cal.add(Calendar.DATE,21);
						Date lagDate = cal.getTime();
						//writer.println(ns.ticker + "," + ns.companyName + "," + priceProvider.getPrice(ns.ticker, ns.newsDate) + "," + priceProvider.priceWithOffset(ns.ticker, ns.newsDate, studyOffset) + "," + ns.sentiment + "," + ns.newsDate + "," + tmx + file.getName().toString() + "," + dateParse.getDateCount(ns.newsText,false)+ "," + priceProvider.getAvgMove(ticker, ns.newsDate, lagDate) + "," + eventProvider.sentimentNews(ns.newsText.toString())+ "," + ns.newsCategory + "," + relS.size() + "," + QUOTE + relS + QUOTE + "," + QUOTE + relC + QUOTE);
						
						sb.append(ns.ticker+ ",");
						sb.append(ns.companyName+ ",");
						sb.append(priceProvider.getPrice(ns.ticker, ns.newsDate)+ ",");
						
						sb.append(priceProvider.priceWithOffset(ns.ticker, ns.newsDate, studyOffset)+ ",");
						sb.append(ns.sentiment+ ",");
						sb.append(ns.newsDate+ ",");
						
						sb.append(tmx + file.getName().toString()+ ",");
						sb.append(dateParse.getDateCount(ns.newsText,false)+ ",");
						sb.append(priceProvider.getAvgMove(ticker, ns.newsDate, lagDate)+ ",");
						sb.append(priceProvider.getSpread(ticker,"BMO",ns.newsDate,lagDate)+ ",");
						sb.append(eventProvider.sentimentNews(ns.newsText.toString())+ ",");
						sb.append(ns.newsCategory+ ",");
						sb.append(relS.size()+ ",");
						sb.append(QUOTE + relS.toString() + QUOTE + ",");
						sb.append(QUOTE + relC.toString() + QUOTE + ",");
						sb.append(ns.companyIndustry);
						writer.println(sb.toString());
					}
				}
				catch(IOException e){
					sb = null;
					
					System.out.println("error");
				}
				finally{
					
					sb.append(ns.ticker+ ",");
					sb.append(ns.companyName+ ",");
					sb.append("no price"+ ",");
					sb.append("no price"+ ",");
					sb.append(ns.sentiment+ ",");
					sb.append("no date"+ ",");
					
					sb.append(tmx + file.getName().toString()+ ",");
					sb.append("no price"+ ",");
					sb.append("no price"+ ",");
					sb.append(eventProvider.sentimentNews(ns.newsText.toString())+ ",");
					sb.append(ns.newsCategory+ ",");
					sb.append(relS.size()+ ",");
					sb.append(QUOTE + relS.toString() + QUOTE + ",");
					sb.append(QUOTE + relC.toString() + QUOTE + ",");
					sb.append(ns.companyIndustry);
					writer.println(sb.toString());
					
				};
				if (verbose) {System.out.println(file.getName().toString());}
			} 
		}
		writer.close();
	}
	private static newStory newStory() {
		// TODO Auto-generated method stub
		return null;
	}
	public static String readNews(String fileLocation) throws IOException  {
		String strNews;
		strNews = "";

		File story = new File(fileLocation);
		FileReader storystr = new  FileReader(story);
		StringBuffer stringBuffer = new StringBuffer();
		int numCharsRead;
		String tmp;
		char[] charArray = new char[1024];
		while ((numCharsRead = storystr.read(charArray)) > 0) {
			stringBuffer.append(charArray, 0, numCharsRead);
		}
		strNews = stringBuffer.toString();
		storystr.close();
		return strNews;
	}
}
