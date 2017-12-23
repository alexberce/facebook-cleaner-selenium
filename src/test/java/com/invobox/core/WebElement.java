package com.invobox.core;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElement {
    private org.openqa.selenium.WebElement webElement;
    private org.openqa.selenium.WebDriver webDriver;

    public WebElement(org.openqa.selenium.WebElement webElement, org.openqa.selenium.WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webElement = webElement;
    }

    public org.openqa.selenium.WebElement getElement() {
        return webElement;
    }

    public void click() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForElement();
        webElement.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(CharSequence... charSequences) {
        waitForElement();
        webElement.sendKeys(charSequences);
    }

    public String getAttribute(String s) {
        return webElement.getAttribute(s);
    }

    private void waitForElement() {
        WebDriverWait visibilityWait = new WebDriverWait(webDriver, 20);
        visibilityWait.until(ExpectedConditions.visibilityOf(webElement));
        visibilityWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }
}
