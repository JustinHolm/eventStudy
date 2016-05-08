package eventStudy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.lang.Object;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//import weka.classifiers.Classifier;
//import weka.classifiers.Evaluation;
//import weka.classifiers.evaluation.NominalPrediction;
//import weka.classifiers.rules.DecisionTable;
//import weka.classifiers.rules.PART;
//import weka.classifiers.trees.DecisionStump;
//import weka.classifiers.trees.J48;
//import weka.core.FastVector;
//import weka.core.Instances;

public class eventProvider {

	public static void main(String[] args) throws IOException {
		//List<String> list = File.readAllLines(new File("country.txt").getPath(), Charset.defaultCharset() );
		String [] news= {"C://Temp//es//news//newsid=30620548&qm_symbol=ABX","q://data//news//newsid=28760506&qm_symbol=ABX"};
		HashMap<String, Integer> hmap =  eventProvider.wordCount("Our mission:  to to to to define  exploration quality mineral exploration opportunities ");

		//stockRefData.csv
		//String [] tickers = {"AAV","ARE","AEM","AGU","AIM","AC","ASR","AGI","AD","AQN","ATD.B","AP.UN","ALA","AYA","ARX","AX.UN","ACO.X","ATH","ATA","ACQ","AVO","BTO","BAD","BMO","BNS","ABX","BTE","BCE","BIR","BB","BEI.UN","BBD.B","BNP","BNE","BYD.UN","BAM.A","BPY.UN","BEP.UN","DOO","CAE","CCO","CF","CAR.UN","CEU","CM","CNR","CNQ","COS","CP","REF.UN","CTC.A","CU","CWB","CFP","CPX","CCL.B","CLS","CVE","CG","GIB.A","CSH.UN","CHE.UN","CIX","CGX","CCA","CIG","CUF.UN","CMG","CXR","CSU","CJR.B","BCB","CPG","CR","CRR.UN","DSG","DGC","DH","DHX.B","DOL","DDC","DII.B","DRG.UN","D.UN","ELD","EFN","EMA","EMP.A","ENB","ENF","ECA","ECI","EFX","ERF","ESL","ESI","EXE","FFH","FTT","FCR","FR","FM","FSV","FTS","FNV","FRU","MIC","WN","GEI","GIL","G","GTE","GRT.UN","GC","GWO","HR.UN","HCG","HBM","HBC","HSE","IMG","IGM","IMO","IAG","INE","IFC","IPL","IFP","ITP","PJC.A","JE","KEL","KEY","KXS","K","LIF","LB","LNR","L","LUN","MDA","MG","MBT","MFC","MFI","MRE","MEG","MX","MRU","MNW","MTL","NA","NSU","NFI","NGD","NBD","NPI","NVU.UN","NG","NVA","OGC","OCX","OTC","OR","PAA","POU","PXT","PKI","PSI","PPL","PGF","PWT","PSG","PEY","POT","POW","PWF","PSK","PD","PVG","P","BIN","PLI","AAR.UN","QBR.B","RRX","QSR","REI.UN","RBA","RCI.B","RON","RY","RUS","SAP","SES","SMF","VII","SJR.B","SCL","SW","SSO","SLW","SRU.UN","SNC","STN","SJ","SLF","SU","SPB","SGY","THO","TCK.B","T","IT","NWC","TRI","X","TOG","TXG","TIH","TD","TOU","TA","TRP","TCL.A","TFI","TCN","TDG","TRQ","UNS","VRX","VSN","VET","WFT","WEF","WJA","WTE","WCP","WSP","YRI"};
		String [] tickers = refdataProvider.getStockList();
		analysisSet[] arr = new analysisSet[5];

		arr[0] = new analysisSet();
		arr[0].file_location = "C://Temp//es//news//newsid=30620548&qm_symbol=ABX";
		arr[1] = new analysisSet();
		arr[1].file_location ="C://Temp//es//news//newsid=28760506&qm_symbol=ABX";
		arr[2] = new analysisSet();
		arr[2].file_location = "C://Temp//es//news//newsid=27905096&qm_symbol=ABX";
		arr[3] = new analysisSet();
		arr[3].file_location = "C://Temp//es//news//newsid=10006651&qm_symbol=WTE";
		arr[4] = new analysisSet();
		arr[4].file_location = "C://Temp//es//news//newsid=10084852&qm_symbol=TDG";
		
		
		for (Integer i = 0 ; i < arr.length; i++ ){	
			try {
				arr[i].newsText = runStudy.readNews(arr[i].file_location);
				arr[i].newsText = removeSparse(arr[i].newsText);
				arr[i].storyWC = eventProvider.wordCount(arr[i].newsText);
				arr[i].storyWC = sortByValues(arr[i].storyWC);

				List list = new LinkedList(arr[i].storyWC.entrySet());
				
				System.out.println(arr[i].file_location);
				
				for (Integer j = 0 ; j < 10; j++){
					System.out.println(list.get(j));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Boolean simpleDivEvent(String newStory){
		Boolean b;
		b = newStory.matches("(?i).*dividend.*");
		return b;
	}
	public static Boolean isCorpEventDiv(String newStory){
		Boolean b = false;
		
		if (newStory.matches("Regular Cash Distribution") || newStory.matches("(?i).*dividend.*")){
			b = true;
		};
		//b = newStory.matches("(?i).*dividend.*");
		return b;
	}
	public static Boolean isCorpEvent(String newStory){
		Boolean b = false;
		if ((newStory.matches("Results and Conference Call")) || newStory.matches("(?i).*broadcast .*")){
			b = true;
		};
		//b = newStory.matches("(?i).*dividend.*");
		return b;
	}
	public static ArrayList<String> relStocks(String newStory,String ticker) throws IOException{
		ArrayList<String> rs = new ArrayList<String>();
		//String [] tickers = {"AAV","ARE","AEM","AGU","AIM","AC","ASR","AGI","AD","AQN","ATD.B","AP.UN","ALA","AYA","ARX","AX.UN","ACO.X","ATH","ATA","ACQ","AVO","BTO","BAD","BMO","BNS","ABX","BTE","BCE","BIR","BB","BEI.UN","BBD.B","BNP","BNE","BYD.UN","BAM.A","BPY.UN","BEP.UN","DOO","CAE","CCO","CF","CAR.UN","CEU","CM","CNR","CNQ","COS","CP","REF.UN","CTC.A","CU","CWB","CFP","CPX","CCL.B","CLS","CVE","CG","GIB.A","CSH.UN","CHE.UN","CIX","CGX","CCA","CIG","CUF.UN","CMG","CXR","CSU","CJR.B","BCB","CPG","CR","CRR.UN","DSG","DGC","DH","DHX.B","DOL","DDC","DII.B","DRG.UN","D.UN","ELD","EFN","EMA","EMP.A","ENB","ENF","ECA","ECI","EFX","ERF","ESL","ESI","EXE","FFH","FTT","FCR","FR","FM","FSV","FTS","FNV","FRU","MIC","WN","GEI","GIL","G","GTE","GRT.UN","GC","GWO","HR.UN","HCG","HBM","HBC","HSE","IMG","IGM","IMO","IAG","INE","IFC","IPL","IFP","ITP","PJC.A","JE","KEL","KEY","KXS","K","LIF","LB","LNR","L","LUN","MDA","MG","MBT","MFC","MFI","MRE","MEG","MX","MRU","MNW","MTL","NA","NSU","NFI","NGD","NBD","NPI","NVU.UN","NG","NVA","OGC","OCX","OTC","OR","PAA","POU","PXT","PKI","PSI","PPL","PGF","PWT","PSG","PEY","POT","POW","PWF","PSK","PD","PVG","P","BIN","PLI","AAR.UN","QBR.B","RRX","QSR","REI.UN","RBA","RCI.B","RON","RY","RUS","SAP","SES","SMF","VII","SJR.B","SCL","SW","SSO","SLW","SRU.UN","SNC","STN","SJ","SLF","SU","SPB","SGY","THO","TCK.B","T","IT","NWC","TRI","X","TOG","TXG","TIH","TD","TOU","TA","TRP","TCL.A","TFI","TCN","TDG","TRQ","UNS","VRX","VSN","VET","WFT","WEF","WJA","WTE","WCP","WSP","YRI"};
		String [] tickers = refdataProvider.getStockList();
		//String [] relStocks = {};

		for(Integer i = 0 ; i < tickers.length; i++){
			if ((newStory.contains("TSX:" +tickers[i])) && (ticker != tickers[i])){
				rs.add(tickers[i]);
			}
		}
		return rs;
	}
	public static ArrayList<String> relcountry(String newStory){
		ArrayList<String> rs = new ArrayList<String>();
		String [] country = {"Singapore","New Zealand","Denmark","South Korea","Hong Kong","United Kingdom","United States","Sweden","Norway","Finland","Australia","Iceland","Ireland","Germany","Georgia","Canada","Estonia","Malaysia","Taiwan","Switzerland","Lithuania","Thailand","Mauritius","United Arab Emirates","Latvia","Macedonia","Saudi Arabia","Japan","Netherlands","Austria","Portugal","Rwanda","Slovenia","Chile","Israel","Belgium","Armenia","France","Cyprus","Puerto Rico","South Africa","Peru","Colombia","Montenegro","Poland","Bahrain","Oman","Qatar","Slovakia","Kazakhstan","Tunisia","Spain","Mexico","Hungary","Panama","Botswana","Tonga","Bulgaria","Brunei","Luxembourg","Samoa","Fiji","Belarus","Saint Lucia","Italy","Trinidad and Tobago","Ghana","Kyrgyzstan","Turkey","Azerbaijan","Antigua and Barbuda","Greece","Romania","Vanuatu","Czech Republic","Mongolia","Dominica","Moldova","Guatemala","Seychelles","San Marino","Saint Vincent and the Grenadines","Zambia","Bahamas","Sri Lanka","Kosovo","Morocco","Uruguay","Croatia","Albania","Barbados","Russia","Serbia","Jamaica","Maldives","China","Solomon Islands","Namibia","Vietnam","Palau","Saint Kitts and Nevis","Costa Rica","Malta","Kuwait","Nepal","Belize","Grenada","Philippines","Paraguay","Pakistan","Lebanon","Ukraine","Papua New Guinea","Marshall Islands","Guyana","Brazil","Dominican Republic","El Salvador","Jordan","Indonesia","Cape Verde","Kiribati","Swaziland","Nicaragua","Ethiopia","Argentina","Honduras","Egypt","Kenya","Bangladesh","Bosnia and Herzegovina","Uganda","Yemen","India","Ecuador","Lesotho","Cambodia","Palestine","Mozambique","Burundi","Bhutan","Sierra Leone","Tajikistan","Liberia","Tanzania","Uzbekistan","Nigeria","Madagascar","Sudan","Gambia","Iraq","Iran","Algeria","Burkina Faso","Mali","F.S. Micronesia","Togo","Comoros","Laos","Djibouti","Suriname","Bolivia","Gabon","Afghanistan","Syria","Equatorial Guinea","Ivory Coast","Cameroon","São Tomé and Príncipe","Zimbabwe","Malawi","Timor-Leste","Mauritania","Benin","Guinea","Niger","Haiti","Senegal","Angola","Guinea-Bissau","Venezuela","Myanmar","Democratic Republic of the Congo","Eritrea","Congo","South Sudan","Libya","Central African Republic","Chad"};

		//String [] relStocks = {};

		for(Integer i = 0 ; i < country.length; i++){
			if (newStory.contains(country[i])) {
				rs.add(country[i]);
			}
		}

		//b = newStory.matches("(?i).*dividend.*");

		return rs;
	}
	public static String removeSparse(String newsStory){
		String[] sparse = {" has "," on "," will "," were "," was ","/>","<br","&#xA0;&#xA0"," of "," as "," to "," that "," the "," a ", " and "," in ","<p>","</p>"," is "," it "};
		newsStory = newsStory.toLowerCase();

		if (newsStory.contains("/>About ")) {
			newsStory = newsStory.substring(newsStory.indexOf("/>About "));
		}

		for (Integer i = 0; i< sparse.length; i++){
			newsStory = newsStory.replace(sparse[i], "");
		}
		return newsStory;
	}
	private static HashMap sortByValues(HashMap map) { 
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		} 
		return sortedHashMap;
	}
	public static Integer sentimentNews(String newStory){

		String[] negWords = {"fee","warned","incurred a loss","mark-to-market losses","less","expenses incurred","investigation","significant impact"};
		String[] posWords = {"profit","gains","more","regained","increase"};
		Integer sentiment = 0 ;
		HashMap <String,Integer> hm = wordCount(newStory);

		for (Integer i = 0; i< negWords.length; i++){
			if (hm.containsKey(negWords[i])) {
				sentiment = sentiment - Integer.parseInt(hm.get(negWords[i]).toString());
			};
		}
		for (Integer i = 0; i< posWords.length; i++){
			if (hm.containsKey(posWords[i])) {
				sentiment = sentiment + Integer.parseInt(hm.get(posWords[i]).toString());
			};
		}
		return sentiment;
	}
	public static Integer sentimentNews2(String newStory){

		//Boolean b;
		String[] negWords = {"fee","warned","incurred a loss","mark-to-market losses","less","expenses incurred","investigation","significant impact"};
		String[] posWords = {"profit","gains","more","regained","increase"};
		Integer sentiment = 0 ;
		//HashMap <String,Integer> hm = wordCount(newStory);

		for (Integer i = 0; i< negWords.length; i++){
			if (newStory.matches(negWords[i]))
				sentiment = sentiment--;
		}
		for (Integer i = 0; i< posWords.length; i++){
			if (newStory.matches(posWords[i]))
				sentiment = sentiment++;
		}
		return sentiment;
	}
	public static HashMap <String,Integer> wordCount(String newStory){

		String delims = "[ ]+";
		String[] tokens = newStory.split(delims);
		HashMap<String,Integer> hmap = new HashMap<String,Integer>();

		for (int i = 0 ; i < tokens.length ; i++){
			if (hmap.containsKey(tokens[i])){
				hmap.put(tokens[i],hmap.get(tokens[i])+1);
			}else{
				hmap.put(tokens[i],1);
			}
		}
		return hmap;
	}
}