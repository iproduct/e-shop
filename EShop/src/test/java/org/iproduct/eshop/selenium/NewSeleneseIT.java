/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author admin
 */
public class NewSeleneseIT {

    static WebDriver driver;
    static Wait<WebDriver> wait;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.gecko.driver", "C:\\CourseJavaWeb\\Materials\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\CourseJavaWeb\\Materials\\chromedriver.exe");

    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tierDown() {
        //Close the browser
        driver.quit();
    }

    @Test
    public void testSimple() throws Exception {

//        driver = new MarionetteDriver();
//        wait = new WebDriverWait(driver, 3000);
//        final String url = "https://www.google.com/";
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        try {
//            driver.navigate().to(url);
//        } finally {
//            driver.close();
//        }
        driver.get("http://localhost:8080/EShop");

        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebElement h1 = d.findElement(By.tagName("h1"));
            System.out.println(h1.getText());
            return h1.getText().contains("Welcome to My Bookstore");
        });
    }

    @Test
    public void testNavigationByMenuButton() throws Exception {
        driver.get("http://localhost:8080/EShop");

        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebElement navigation = d.findElement(By.id("navigation"));
            navigation.findElement(By.xpath("//ul/li[2]/a")).click();
            return (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) (WebDriver wd) -> {
                WebElement h1 = wd.findElement(By.tagName("h1"));
                System.out.println(h1.getText());
                return h1.getText().contains("Add New Book");
            });
        });
    }

}
