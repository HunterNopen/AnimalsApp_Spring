package com.example.animals_app.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumIndexPage {

    public static final String URL="http://localhost:8081/animals/index";

    WebDriver webDriver;

    public SeleniumIndexPage(WebDriver webDriver){
        this.webDriver=webDriver;
    }

    @FindBy(css = "a[href='/animals/edit/1']")
    WebElement editButton;

    //cssSelector("a[href='https://example.com/page']")
    @FindBy(css = "a[href='/animals/delete/1']")
    WebElement deleteButton;

    @FindBy(css = "a[href='/animals/addAnimal']")
    WebElement addButton;

    @FindBy(xpath = "//*[text()='Grogg']")
    WebElement animalName;

    @FindBy(xpath = "//*[text()='Pikachu']")
    WebElement findNameAdd;

    @FindBy(xpath = "//*[text()='Fluffy']")
    WebElement findNameEdit;

    public void open(){
        webDriver.get(URL);
        PageFactory.initElements(webDriver, this);
    }

    public void switchEditView(){
        editButton.click();
    }

    public void clickDeleteButton(){
        deleteButton.click();
    }
    public void switchAddView(){
        addButton.click();
    }

    public String getAnimal(){
        return animalName.getText();
    }

    public boolean getFindAnimal(){
        try {
            webDriver.findElement(By.xpath("//*[text()='FINDME')]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
