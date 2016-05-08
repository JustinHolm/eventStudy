package eventStudy;

import java.util.Date;
import java.util.HashMap;

public class analysisSet {
	
	public newStory ns;
	public String newsText;
	public String ticker;
	public Date newsDate;
	public Integer sentiment;
	public String file_location;
	public HashMap<String, Integer> storyWC;
	
	public void setStory(String newValue) {
		newsText = newValue;
    }
}
