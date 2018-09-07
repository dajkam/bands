/**
 zaliczenie selenium
 */

package filip.projekt.bands;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import filip.projekt.bands.bandCRUD.webb.BandApi;

public class IndexTest {
    private WebDriver driver; 
    private String startURL;
    private String startURL0;
    private boolean acceptNextAlert = true; 
    private StringBuffer verificationErrors = new StringBuffer();


    @BeforeClass
    public static void init() {

        //BandApi api = new BandApi();
       // api.fill2();
        
    }

    @Before
    public void seting() throws Exception 
    {

       // String[] cmd = new String[]{"/bin/sh", "/home/filip/Pulpit/tau/wrzesien/bands/bands/scripts/skrypt.sh"};
      //  Process pr = Runtime.getRuntime().exec(cmd);


        driver = new ChromeDriver();
       
      // startURL ="file:///home/filip/Pulpit/tau/lab1/bands-rest/bands/bands/src/test/java/filip/projekt/bands/index/index.html";
     // startURL = "index/index.html";

        

     startURL = "http://localhost:8080/api/Szukaj";

     startURL0 = "http://localhost:8080/api/Fill2";

    
       
        
    }

    @Test
    public void test() throws Exception {
       driver.get(startURL0);
       driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
       driver.get(startURL);
       driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
       // driver.findElement(By.id("log")).clear();
       // driver.findElement(By.id("chas")).clear();
       //driver.findElement(By.id("log")).click();
       // driver.findElement(By.id("log")).sendKeys("sopotfilip@gmail.com");
       // driver.findElement(By.id("chas")).click();
       // driver.findElement(By.id("chas")).sendKeys("123fil");
        //driver.findElement(By.tagName("small")).click();
       // driver.findElement(By.cssSelector("button")).click();
        String s = "/home/filip/Pulpit/tau/wrzesien/bands/bands/src/main/java/filip/projekt/bands/bandCRUD/webb/szukaj.html";
       assertEquals("szukaj",this.getValue(s));

       assertEquals(startURL, driver.getCurrentUrl());

       driver.findElement(By.id("szukane")).clear();

       driver.findElement(By.id("szukane")).sendKeys("amon amarth");

       driver.findElement(By.id("button")).click();

       String endURL = "http://localhost:8080/api/Search?szukane=amon+amarth";

       assertEquals(endURL, driver.getCurrentUrl());

       assertNotEquals(startURL0, driver.getCurrentUrl());


      // assertEquals("raz.jpg", driver)
         //   .findElement(By.tagName("img")).getText()
        
        
      //  ); 

        File scrshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrshot, new File("obraz.png"));

    //assertEquals("szukaj",);));
        
    }

    public String getValue(String s) throws IOException {

        File file = new File(s);
        Document document = Jsoup.parse(file, "UTF-8");
        Element something = document.select("input[type=submit]").last();
        String value = something.val();
        return value;
// ...

        
    }


    @After
    public void shut() throws Exception {
        driver.quit();
        String errors = verificationErrors.toString();
        if (!"".equals(errors)) {
            fail(errors);
            
        }        
    }
    
}