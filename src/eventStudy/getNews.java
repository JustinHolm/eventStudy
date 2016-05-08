package eventStudy;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class getNews {
	public static void main (String args[]) throws IOException  {
		   File[] files = new File("q://data//News//").listFiles();
		   for (File file : files) {
			   if (file.isFile()) {
				   File story = new File(file.getAbsoluteFile().toString());
		           FileReader storystr = new  FileReader(story);
		           StringBuffer stringBuffer = new StringBuffer();
		           int numCharsRead;
		           String tmp;
				   char[] charArray = new char[1024];
				   while ((numCharsRead = storystr.read(charArray)) > 0) {
					   stringBuffer.append(charArray, 0, numCharsRead);
				   }

				   boolean b;
				   tmp = stringBuffer.toString();
				   String delims = "[ ]+";
				   String[] tokens = tmp.split(delims);
				   b = tmp.matches("(?i).*dividend.*");
				   String ticker = file.getName().toString();
				   String prefix = "qm_symbol=";
				   ticker = ticker.substring(ticker.indexOf(prefix)+ prefix.length());

				   int x = 30;
				   if (b) {
					   try{
						   Date newsdate = dateParse.getDate(tmp,false);
						   Calendar cal = Calendar.getInstance();
						   cal.setTime(newsdate);
						   cal.add(Calendar.DATE,x);
						   if(cal.get(Calendar.DAY_OF_WEEK) == 1) cal.add(Calendar.DATE,1);
						   if(cal.get(Calendar.DAY_OF_WEEK) == 7) cal.add(Calendar.DATE,2);

						   Date laterDate = cal.getTime();		   
						   System.out.println("Divdend noted and date found: " + newsdate + " price was: " + priceProvider.getPrice(ticker, newsdate) + " and later price was: " + priceProvider.getPrice(ticker, laterDate) + " on " + laterDate);
					   		}
					   catch(Exception NullPointerException){
						   System.out.println("error with " +ticker);
					   		}
					   finally{
						   
					   }
					   
				   	}
				   	storystr.close();
			   } 
		   }   
	} 
}
