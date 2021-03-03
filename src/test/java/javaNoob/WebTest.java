package javaNoob;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import util.UtilWeb;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(OrderAnnotation.class)
public class WebTest {
    private UtilWeb utilWeb;
	
    @Test
    void testNavigateGoogle(){
    	utilWeb = new UtilWeb();
    	boolean check;
    	String html;
    	
    	check = utilWeb.openBrowserFirefox();
    	if(!check)
    		fail("didn't open browser");
    	
        check = utilWeb.navigateUrl("www.google.com");
    	if(!check)
    		fail("didn't navigate to google");
    	
    	try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	
    	html = utilWeb.getHTML();
        if(!html.contains("Disponibilizado pelo Google"))
            fail("HTML is different then expected");
        html = null;
        
        if(!"Google".equals(utilWeb.getTitle()))
        	fail("Title is different then expected");
        
        if(utilWeb.getQtdWindows() != 1)
        	fail("QTD winfows is different then expected");
        
        if(!utilWeb.isPageFound())
        	fail("isPageFound is different then expected");
        
        utilWeb = null;
        assertTrue(true);
    }
}