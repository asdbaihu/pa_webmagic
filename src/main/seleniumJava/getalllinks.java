package seleniumJava;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class getalllinks
{
    public static void main(String[] args)
    {

       // System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
        //System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");



        //WebDriver driver = new FirefoxDriver();
        System.setProperty("Webdriver.chrome.driver", "D:\\runajian\\firefoxdriver\\chrome\\chromedriver.exe");
        WebDriver driver= new ChromeDriver();
        driver.navigate().to("www.baidu.com");
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of Links in the Page is " + links.size());
        for (int i = 1; i<=links.size(); i=i+1)
        {
            System.out.println("Name of Link# " + i + links.get(i).getText());
        }
    }
}