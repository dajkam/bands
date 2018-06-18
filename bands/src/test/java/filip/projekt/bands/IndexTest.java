/**
 zaliczenie selenium
 */
/*
package filip.projekt.bands;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

public class IndexTest {
    private WebDriver driver; 
    private String startURL;
    private boolean acceptNextAlert = true; 
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void seting() throws Exception 
    {
        driver = new ChromeDriver();
       
      // startURL ="file:///home/filip/Pulpit/tau/lab1/bands-rest/bands/bands/src/test/java/filip/projekt/bands/index/index.html";
     // startURL = "index/index.html";
     startURL = "http://szuflandia.pjwstk.edu.pl/~s13033/str/";
       driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
    }

    @Test
    public void test() throws Exception {
       driver.get(startURL);
       // driver.findElement(By.id("log")).clear();
       // driver.findElement(By.id("chas")).clear();
       driver.findElement(By.id("log")).click();
        driver.findElement(By.id("log")).sendKeys("sopotfilip@gmail.com");
        driver.findElement(By.id("chas")).click();
        driver.findElement(By.id("chas")).sendKeys("123fil");
        driver.findElement(By.tagName("small")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("raz.jpg", driver
            .findElement(By.tagName("img")).getText()
        
        
        ); 

        File scrshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrshot, new File("obraz.png"));
        
    }

    @After
    public void shut() throws Exception {
        driver.quit();
        String errors = verificationErrors.toString();
        if (!"".equals(errors)) {
            fail(errors);
            
        }        
    }
    
}*/