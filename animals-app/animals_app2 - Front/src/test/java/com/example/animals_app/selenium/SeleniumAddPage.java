package com.example.animals_app.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumAddPage {

        public static final String URL="http://localhost:8081/animals/addAnimal";

        WebDriver webDriver;

        public SeleniumAddPage(WebDriver webDriver){
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
            nameInput.sendKeys("Pikachu");
            typeInput.sendKeys("Capybara");
            lvlInput.sendKeys("10");
        }

        public void submit(){
            submitBtn.submit();
        }

}
