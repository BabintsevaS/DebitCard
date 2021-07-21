package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCreditCard {

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        // System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldShowMessageAfterSendRequest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79632581478");
        driver.findElement(By.cssSelector("[class=\"checkbox__box\"]")).click();
        driver.findElement(By.cssSelector("[class=\"button__text\"]")).click();
        String actualText = driver.findElement(By.cssSelector("")).getText().strip();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(expectedText, actualText);
    }
}
