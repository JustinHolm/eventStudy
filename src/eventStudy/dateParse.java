package eventStudy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dateParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str2 = new String("On july 31, 2008, the Company e july 31, 2012  ntere  july 31, 2010 d into a support agreement");
		
		System.out.println(dateMap(str2));
		System.out.println(dateMap(str2).size());
		System.out.println(getDateCount(str2, false));
		
		System.out.println("Testing individual date parse:");
		System.out.println(getDate("On july 31, 2008, the Company entered into a support agreement",false));
		System.out.println(getDate("On July 31, 2008, the Company entered into a support agreement",false));
		System.out.println(getDate("dsdsd2015-11-19wemay 8, 2015wewdsfaadsfdasfewe",false));
		System.out.println(getDate("dsdsd201dfadsf-19wedecember 1, 2015wewdsfaadsfdasfewe",false));
	}
	
	public static Date getDate(String newsTxt,Boolean debug){
		
		int startPosition = 0;
		int endPosition = 0;
		String dateToparse = null;
		String newsText = newsTxt;
		

		Pattern p = Pattern.compile("january 0*1*2*3*\\d, \\d{4}|february 0*1*2*3*\\d, \\d{4}|march 0*1*2*3*\\d, \\d{4}|april  0*1*2*3*\\d, \\d{4}|may 0*1*2*3*\\d, \\d{4}|june 0*1*2*3*\\d, \\d{4}|july 0*1*2*3*\\d, \\d{4}|august 0*1*2*3*\\d, \\d{4}|september 0*1*2*3*\\d, \\d{4}|october 0*1*2*3*\\d, \\d{4}|november 0*1*2*3*\\d, \\d{4}|december 0*1*2*3*\\d, \\d{4}");
		Matcher m = p.matcher(newsText.toLowerCase());
		if (m.find()) {
		   startPosition = m.start();
		   endPosition = m.end();
		}
		
		Pattern p2 = Pattern.compile("jan. 0*1*2*3*\\d, \\d{4}|feb. 0*1*2*3*\\d, \\d{4}|mar. 0*1*2*3*\\d, \\d{4}|apr.  0*1*2*3*\\d, \\d{4}|may. 0*1*2*3*\\d, \\d{4}|jun. 0*1*2*3*\\d, \\d{4}|jul. 0*1*2*3*\\d, \\d{4}|aug. 0*1*2*3*\\d, \\d{4}|sep. 0*1*2*3*\\d, \\d{4}|oct. 0*1*2*3*\\d, \\d{4}|nov. 0*1*2*3*\\d, \\d{4}|dec. 0*1*2*3*\\d, \\d{4}");
		Matcher m2 = p.matcher(newsText.toLowerCase());
		if (m2.find()) {
			   startPosition = m2.start();
			   endPosition = m2.end();
			}
		
		dateToparse = newsText.substring(startPosition, endPosition);
		Date dateFound = strToDate(dateToparse);
		return dateFound;
	}
	public static Integer getDateCount(String newsTxt,Boolean debug){
		List<Date> dates = new ArrayList<Date>();
		int startPosition = 0;
		int endPosition = 0;
		int DateCount = 0;
		Boolean eof = false;
		String dateToparse = null;
		String newsText = newsTxt;
		Pattern p = Pattern.compile("january 0*1*2*3*\\d, \\d{4}|february 0*1*2*3*\\d, \\d{4}|march 0*1*2*3*\\d, \\d{4}|april  0*1*2*3*\\d, \\d{4}|may 0*1*2*3*\\d, \\d{4}|june 0*1*2*3*\\d, \\d{4}|july 0*1*2*3*\\d, \\d{4}|august 0*1*2*3*\\d, \\d{4}|september 0*1*2*3*\\d, \\d{4}|october 0*1*2*3*\\d, \\d{4}|november 0*1*2*3*\\d, \\d{4}|december 0*1*2*3*\\d, \\d{4}");
		
		do {
			Matcher m = p.matcher(newsText.toLowerCase());
			if (m.find()) {
			   startPosition = m.start();
			   endPosition = m.end();
			   DateCount++;
			   newsText = newsText.substring(endPosition);
			}else {
				eof = true;
			}			
		} while (eof == false);
		
		return DateCount;
	}
	
	public static HashMap dateMap(String newStory){
		//List<Date> myList = new ArrayList<>();
		int startPosition = 0;
		int endPosition = 0;
		int DateCount = 0;
		Boolean eof = false;
		String dateToparse = null;
		String newsText = newStory;
		Pattern p = Pattern.compile("january 0*1*2*3*\\d, \\d{4}|february 0*1*2*3*\\d, \\d{4}|march 0*1*2*3*\\d, \\d{4}|april  0*1*2*3*\\d, \\d{4}|may 0*1*2*3*\\d, \\d{4}|june 0*1*2*3*\\d, \\d{4}|july 0*1*2*3*\\d, \\d{4}|august 0*1*2*3*\\d, \\d{4}|september 0*1*2*3*\\d, \\d{4}|october 0*1*2*3*\\d, \\d{4}|november 0*1*2*3*\\d, \\d{4}|december 0*1*2*3*\\d, \\d{4}");

		HashMap<Integer,Date> hmap = new HashMap<Integer,Date>();
		
		do {
			Matcher m = p.matcher(newsText.toLowerCase());
			if (m.find()) {
			   startPosition = m.start();
			   endPosition = m.end();
			   Date dateFound = strToDate(newsText.substring(startPosition, endPosition));
			   hmap.put(hmap.size() + 1,dateFound);
			   DateCount++;
			   newsText = newsText.substring(endPosition);
		}
		else {
			eof = true;
		}			
		} 
		while (eof == false);
		return hmap;
	}	

	public static Date strToDate(String strDate){
		
		boolean goodDateFound = false;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
			Date dateFound = sdf.parse(strDate);
			goodDateFound = true;
			return dateFound;
		} 
		catch (ParseException e) {
			System.out.println("No date found");
			//e.printStackTrace();
			goodDateFound = false;
			return null;
		}	
	}
}
