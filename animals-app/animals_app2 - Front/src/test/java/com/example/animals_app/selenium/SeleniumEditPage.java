package com.example.animals_app.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumEditPage {

    public static final String URL="http://localhost:8081/animals/edit/1";

    WebDriver webDriver;

    public SeleniumEditPage(WebDriver webDriver){
        this.webDriver=webDriver;
    }

    @FindBy(id = "name")
    WebElement nameInput;

    @FindBy(id = "type")
    WebElement typeInput;

    @FindBy(id = "cutenessLvl")
    WebElement lvlInput;

    @FindBy(id="submit")
    WebElement submitBtn;

    public void open(){
        webDriver.get(URL);
        PageFactory.initElements(webDriver, this);
    }

    public void fillInForm(){
        nameInput.clear();
        typeInput.clear();
        lvlInput.clear();
        nameInput.sendKeys("Fluffy");
        typeInput.sendKeys("Capybara");
        lvlInput.sendKeys("10");
    }

    public void submit(){
        submitBtn.submit();
    }

}
