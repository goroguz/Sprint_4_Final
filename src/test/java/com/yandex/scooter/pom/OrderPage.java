package com.yandex.scooter.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderPage {

    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы формы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.className("select-search__input"); // поиск метро
    private final By metroSuggestion = By.className("select-search__option"); // первое из списка
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Вторая страница
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.className("Dropdown-placeholder");
    private final By rentalOption1Day = By.xpath("//div[text()='сутки']");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    private final By successModal = By.className("Order_ModalHeader__3FDaJ");
    private final By pageHeader = By.className("Order_Header__BZXOb");

    public void fillPersonalInfo(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(metroField).sendKeys(metro);
        driver.findElement(metroSuggestion).click();
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillOrderDetails(String date, String comment, String color) {
        driver.findElement(dateField).sendKeys(date);
        driver.findElement(pageHeader).click();

        WebElement dropdown = driver.findElement(rentalPeriod);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();
        driver.findElement(rentalOption1Day).click();
        driver.findElement(getScooterColorCheckbox(color)).click();
        driver.findElement(commentField).sendKeys(comment);
        driver.findElement(orderButton).click();
        driver.findElement(confirmYesButton).click();
    }

    private By getScooterColorCheckbox(String color) {
        return By.id(color.toLowerCase());
    }

    public boolean isSuccessModalVisible() {
        return driver.findElement(successModal).isDisplayed();
    }
}