package com.yandex.scooter.test;

import com.yandex.scooter.pom.MainPage;
import com.yandex.scooter.pom.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFlowTest {

    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String comment;
    private final boolean useTopButton;
    private final String scooterColor;

    public OrderFlowTest(String name, String surname, String address, String metro,
                         String phone, String date, String comment, boolean useTopButton, String scooterColor) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.useTopButton = useTopButton;
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
            {"Иван", "Петров", "ул. Ленина, 10", "Черкизовская", "+79990001122", "20.05.2025", "Позвоните за час", true, "black"},
            {"Мария", "Иванова", "пр. Мира, 5", "Сокольники", "+79995556677", "21.05.2025", "Код от двери 123", false, "grey"}
        });
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/A13039316/Downloads/chromedriver-mac-arm64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testOrderFlowWithValidData() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        if (useTopButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        orderPage.fillPersonalInfo(name, surname, address, metro, phone);
        orderPage.fillOrderDetails(date, comment, scooterColor);

        assertTrue("Окно подтверждения не отображается", orderPage.isSuccessModalVisible());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
