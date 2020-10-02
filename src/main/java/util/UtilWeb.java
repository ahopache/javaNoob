/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 * @version 1.0
 */
package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.ArrayList;

public class UtilWeb {
    protected WebDriver driver;
    protected boolean isLastNavigateOK = true;
    protected String lastUrlNavigate = "";
    protected FirefoxOptions options = null;
    protected FirefoxProfile profile = null;
    private String driverLocation = null;

    /**
     * Construtor definindo o local do driver para pasta default
     * Se necessario, utilize o m�todo setDriverFireforDirectory() para definir outro local 
     */
    public UtilWeb(){
    	this.setDriverFireforDirectory("D:\\programas\\selennium_driver\\geckodriver.exe");
    }
    
    /**
     * Definir local do drive para conex�o com Firefox
     * 
     * @param driverLocation
     */
    public void setDriverFireforDirectory(String driverLocation) {
    	this.driverLocation = driverLocation;
    	
    }

    /**
     * set FirefoxOptions before open Firefox
     * 
     * @param options
     */
    public void setOptions(FirefoxOptions options){
        this.options = options;
    }

    /**
     * Open Firefox Browser
     */
    public boolean openBrowserFirefox(){
        System.setProperty(
                "webdriver.gecko.driver",
                this.driverLocation
        );
        if(this.options == null)
            this.driver = new FirefoxDriver();
        else
            this.driver = new FirefoxDriver(this.options);

        return true;
    }

    /**
     * Navigate to a url using the browser opened
     * 
     * @param url
     */
    public boolean navigateUrl(String url){
        this.lastUrlNavigate = url;
        try{
            this.driver.get( url );
            this.isLastNavigateOK = true;
            return true;
        }catch (Exception e){
            if(
                e.getMessage().contains("Malformed URL: URL constructor") ||
                e.getMessage().contains("Reached error page: about:neterror?e=connectionFailure") ||
                e.getMessage().contains("Reached error page: about:neterror?e=dnsNotFound") ||
                e.getMessage().contains("Reached error page: about:neterror?e=netTimeout") ||
                e.getMessage().contains("Reached error page: about:neterror?e=nssFailure") ||
                e.getMessage().contains("Reached error page: about:neterror?e=redirectLoop")  ||
                e.getMessage().contains("Reached error page: about:neterror?e=unknownProtocolFound") ||
                e.getMessage().contains("Timeout loading page after 300000ms")
            ){
                this.isLastNavigateOK = false;
            }else{
            	UtilLog.setLog("Erro:\nMessage: " + e.getMessage());
            	UtilLog.setLog("\n\n\nHTML: ");
            	UtilLog.setLog(this.getHTML());
            }
        }
        return false;
    }

    /**
     * 
     * @return page source off opened page 
     */
    public String getHTML(){
        return this.driver.getPageSource();
    }

    /**
     * 
     * @return title of opened page
     */
    public String getTitle(){
        return this.driver.getTitle();
    }

    /**
     * 
     * @return qtd of windows opened
     */
    public int getQtdWindows(){
        return driver.getWindowHandles().size();
    }

    /**
     * 
     * @return true/false if page is found
     */
    public boolean isPageFound(){
        if(this.isLastNavigateOK)
            return !checkHTMLForPageNotFound(driver.getTitle(), driver.getPageSource());
        else
            return false;
    }

    /**
     * 
     * @param title
     * @param html
     * 
     * @return true/false if page is not found
     */
    private boolean checkHTMLForPageNotFound(String title, String html){
        ArrayList<String> listTitle = new ArrayList<>();
        listTitle.add("Problemas ao carregar a página");
        listTitle.add("Not Found");
        listTitle.add("Whoops! There was an error.");
        listTitle.add("Bitly | Page Not Found | 404");
        listTitle.add("Pastebin.com - Not Found (#404)");

        if (listTitle.contains(title))
            return true;
        else if (html.contains("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">\n<H1>Not Found</H1>\n" +
                "<H2>Error 404</H2>\n</BODY>"))
            return true;
        else
            return false;

    }

    /**
     * Open browser console, execute an script that return a list of files in this page 
     * 
     * @return String with all files
     */
    public String getNetworkFiles(){
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        return ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
    }

    /**
     * put a value in field
     * 
     * @param fieldName
     * @param fieldValue
     */
    public void sendValueToField(String fieldName, String fieldValue){
        WebElement campoDeTexto = driver.findElement(By.name(fieldName));
        campoDeTexto.sendKeys(fieldValue);
    }

    /**
     * submit a filed
     * 
     * @param fieldName
     */
    public void submitField(String fieldName){
        WebElement campoDeTexto = driver.findElement(By.name(fieldName));
        campoDeTexto.submit();
    }
}
