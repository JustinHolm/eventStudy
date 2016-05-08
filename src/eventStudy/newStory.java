package eventStudy;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eventStudy.newStory.newsCategory;

public class newStory {

	public String newsText;
	public String ticker;
	public Date newsDate;
	public String companyName;
	public String companyIndustry;
	public Integer sentiment;
	public newsCategory newsCategory;
	public HashMap<String, Integer> storyWC;
	public enum newsCategory {unknown, annualReport, marketNews, companyPub, corpAction,corpEvent,potentialTakeOver,potentialAcquisition};
	public void setStory(String newValue) {
		newsText = newValue;
    }

	
	public static void main(String[] args) throws IOException {
		String tmpstr = "test test test test Explore and Options";
		newStory ns = new newStory();
		ns.newsText = tmpstr;
		ns.setCategory();
		System.out.println(ns.newsCategory);
	}
	public void setCategory(){
		Integer x;
		
		this.newsCategory = newsCategory.unknown;

		if (eventProvider.isCorpEvent(this.newsText.toString())){
			this.newsCategory = newsCategory.corpEvent;
		}
		
		Pattern p = Pattern.compile("(Special Committee|Explore|Strategic|Options|Maximize Shareholder Value|unrealized value)");
		Matcher m = p.matcher(this.newsText.toString());
		x = 0;
		
		while (m.find()){
			x++;
		}
		
		if (x>4){
			this.newsCategory = newsCategory.potentialTakeOver;
		}
		
		if (this.newsText.toString().contains("Acquisition")){
			this.newsCategory = newsCategory.potentialAcquisition;
		}
		if (eventProvider.isCorpEventDiv(this.newsText.toString())){
			this.newsCategory = newsCategory.corpAction;
		}
		if (dateParse.getDateCount(this.newsText.toString(),false) > 20){
			this.newsCategory = newsCategory.annualReport;
		}
	};
	public void setCompanyName() throws IOException{
		this.companyName =  refdataProvider.getName(this.ticker);
	}
	public void setIndustry() throws IOException{
		this.companyIndustry =  refdataProvider.getIndustry(this.ticker);
	}
}
