package eventStudy;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class runStudyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testdateParse() {
		
		String str1 = new String("fafadsfadfdf");
		String str2 = new String("fafadsfadfdf");
		Boolean b = eventProvider.simpleDivEvent("fdadfdasfdasfa dividend");
		Boolean c = eventProvider.simpleDivEvent("fdadfdasfdadend");
		assertEquals(b,true);
		assertEquals(c,false);
		
		
		Date d1 = dateParse.getDate("On july 31, 2008, the Company entered into a support agreement",false);
		
		assertEquals(str1,str2);
		//fail("fdasfNot yet implemented");
	}
	@Test
	public void testeventProvider() {
		
		HashMap hmap =  eventProvider.wordCount("Our mission:  to to to to define  exploration quality mineral exploration opportunities ");
	    System.out.println(hmap.toString()); 
	    System.out.println(hmap.get("exploration")); 
	    Integer exCount = Integer.parseInt(hmap.get("exploration").toString());
	    Integer n = 2;
	    assertEquals(exCount,n);
	}
	@Test
	public void testeventSentiment() {
		
		HashMap hmap =  eventProvider.wordCount("Our mission:  to to to to define  explora incurred a loss tion quality mineral exploration opportunities ");
	    //System.out.println(hmap.toString()); 
	    //System.out.println(hmap.get("exploration")); 
	    
		//Integer actSent = hmap.getEntry("to");
		
	    Integer actSent= eventProvider.sentimentNews("Our mission:  to to to to  investigation define g incurred a loss ains exploration quality mineral exploration opportunities ");
	    Integer exSent = -1;
	    		
	    assertEquals(actSent,exSent);
	}
	@Test
	public void testmultiDate() {
		
		String str1 = new String("On july 31, 2008, the Company entere  july 31, 2008 d into a support agreement");
		Integer int1 = dateParse.getDateCount(str1, false);
		Integer test1 = 2;

		String str2 = new String("On july 31, 2008, the Company e july 31, 2012  ntere  july 31, 2010 d into a support agreement");
		Integer int2 = dateParse.getDateCount(str2, false);
		Integer test2 = 3;
		
		assertEquals(test1,int1);
		assertEquals(test2,int2);
	    //assertEquals(3,dateParse.getDateCount(str2, false));
	}

}
