package com.example.animals_app.selenium;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;


public class SeleniumTest {
    WebDriver webDriver;
    private SeleniumAddPage addPage;
    private SeleniumIndexPage indexPage;
    private SeleniumEditPage editPage;

    @BeforeEach
    public void setUp(){
        webDriver=new ChromeDriver();
        addPage=new SeleniumAddPage(webDriver);
        indexPage=new SeleniumIndexPage(webDriver);
        editPage=new SeleniumEditPage(webDriver);
    }

    @AfterEach
    public void tearDown(){
        webDriver.close();
    }

    @Test
    public void testIfVisibleAnimal(){
        indexPage.open();
        assertTrue(indexPage.animalName.isDisplayed());
    }

    @Test
    public void testIfAddPage(){
        indexPage.open();
        indexPage.switchAddView();

        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("http://localhost:8081/animals/addAnimal", currentUrl);
    }

    @Test
    public void testIfEditPage(){
        indexPage.open();
        indexPage.switchEditView();

        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("http://localhost:8081/animals/edit/1", currentUrl);
    }

    @Test
    public void testAddPage(){
        addPage.open();
        addPage.fillInForm();
        addPage.submit();

        indexPage.open();
        assertTrue(indexPage.findNameAdd.isDisplayed());
    }

    @Test
    public void testEditPage(){
        editPage.open();
        editPage.fillInForm();
        editPage.submit();

        indexPage.open();
        assertTrue(indexPage.findNameEdit.isDisplayed());
    }

    @Test
    public void testDeleteButton(){
        indexPage.open();
        indexPage.clickDeleteButton();

        assertFalse(indexPage.getFindAnimal());
    }

}
