package eventStudy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class KPImonitor {
	
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		
		BufferedInputStream in = new BufferedInputStream(new URL("http://kpimonitor.par.emea.cib/index-summary.php?bli%5B%5D=SA1-FR&bli%5B%5D=SA1-UK#").openStream());
		StringBuilder sb = new StringBuilder();
		//in.wait(3);
		
		while(in.available()>0)
        {
           // read the byte and convert the integer to character
           char c = (char)in.read();
           sb.append(c);
           // print the characters
           //System.out.println("Char: "+c);;
        }
		System.out.println(sb.toString());
		
		//byte data[] = new byte[1024];
	    //int count;
	    //while((count = in.read(data,0,1024)) != -1)
	    //{
	    //	System.out.println(data);
	    //}	
				
		
		
	}

}
