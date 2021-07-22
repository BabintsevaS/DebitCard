package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class TestCreditCard {

    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldShowMessageAfterSendRequest() {
        driver.get("http://localhost:9999");
        WebElement formName = driver.findElement(By.cssSelector("[data-test-id=name]"));
        WebElement formPhone = driver.findElement(By.cssSelector("[data-test-id=phone]"));
        WebElement formAgr = driver.findElement(By.cssSelector("[data-test-id=agreement]"));

        formName.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов Иван");
        formPhone.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79632581478");
        formAgr.findElement(By.cssSelector("[class=\"checkbox__box\"]")).click();

        driver.findElement(By.className("button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldShowMessageAfterSendWrongRequest() {
        driver.get("http://localhost:9999");
        WebElement formName = driver.findElement(By.cssSelector("[data-test-id=name]"));
        formName.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Ivan");

        driver.findElement(By.className("button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[class=input__sub]")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText);

    }
}
