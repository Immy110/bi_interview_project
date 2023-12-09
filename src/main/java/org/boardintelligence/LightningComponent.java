package org.boardintelligence;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import roman.Roman;
import selenium.AbstractPage;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public abstract class LightningComponent extends AbstractPage {
    private final Duration defaultTimeout = Duration.ofSeconds(15L);

    public LightningComponent(Roman roman) {
        super(roman);
    }

    @Override
    protected String getUri() {
        return null;
    }

    public boolean validateElementIsEnabledAndDisplayed(String xpath, Duration duration) {
        return validateElementIsEnabledAndDisplayed(By.xpath(xpath), duration);
    }

    public boolean waitForNumberOfOpenWindowsToBe(int numberOfWindows) {
        try {
            (new WebDriverWait(this.driver, this.defaultTimeout))
                    .until(numberOfWindowsToBe(numberOfWindows));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean validateElementIsPresent( By element, Duration duration) {
        try {
            (new WebDriverWait(this.driver, duration))
                    .until(ExpectedConditions.presenceOfElementLocated(element));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean validateElementIsPresent(By element) {
        try {
            (new WebDriverWait(this.driver, this.defaultTimeout))
                    .until(ExpectedConditions.presenceOfElementLocated(element));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void clickElement(String xpath) {
        click(By.xpath(xpath));
    }

    public void pressEnter() {
        var element = driver.switchTo().activeElement();
        element.sendKeys(Keys.END);
        element.sendKeys(Keys.ENTER);
    }

    public void hoverOverElement(String selector) {
        var by = By.xpath(selector);
        WebElement ele = driver.findElement(by);
        Actions action = new Actions(driver);
        action.moveToElement(ele).perform();
    }

    protected boolean waitForAttributeWithValue(
            By element,
            String attributeOrCss,
            String attributeValue,
            Duration waitFor
    ) {
        try {
            (new WebDriverWait(this.driver, waitFor))
                    .until(ExpectedConditions.attributeToBe(element, attributeOrCss, attributeValue));
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    protected boolean waitForAttributeWithValue(
            By element,
            String attributeOrCss,
            String attributeValue
    ) {
        return this.waitForAttributeWithValue(element, attributeOrCss, attributeValue, this.defaultTimeout);
    }

    protected By by(String xpath, Object... args) {
        return By.xpath(xpath.formatted(args));
    }

    protected void enterText(By selector, String text) {
        var element = findElement(selector);
        scrollToElementUsingActions(selector);
        click(selector);
        element.sendKeys(text);
    }

    protected void clearAndEnterText(By selector, String text) {
        var element = findElement(selector);
        scrollToElementUsingActions(selector);
        click(selector);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", element);
        element.sendKeys(text);
    }

    protected void clearText(By selector) {
        var element = findElement(selector);
        scrollToElementUsingActions(selector);
        click(selector);
        element.clear();
    }
    
    public String getText(By elementBy)
    {
    	WebElement element = driver.findElement(elementBy);
    	return element.getText();
    }

    protected boolean validateElementIsDisplayedOrNot(By by, String displayed) {
        return switch (displayed.toLowerCase()) {
            case "see" -> validateElementIsEnabledAndDisplayed(by, defaultTimeout);
            case "not see" -> validateElementIsNotDisplayed(by, defaultTimeout);
            default -> throw new IllegalArgumentException("'see' or 'not see' expected. %s given".formatted(displayed));
        };
    }

    protected boolean validateElementIsEnabledOrDisabled(By by, String enabled) {
        return switch (enabled.toLowerCase()) {
            case "enabled" -> validateElementIsEnabledAndDisplayed(by);
            case "disabled" -> validateElementIsNotDisplayed(by);
            default -> throw new IllegalArgumentException("'enabled' or 'disabled' expected. %s given".formatted(enabled));
        };
    }

    protected boolean validateElementIsSelectedOrNot(By by) {
        return this.validateElementIsSelectedOrNot(by, this.defaultTimeout);
    }

    protected boolean validateElementIsSelectedOrNot(By by, Duration timeout) {
        try {
            return ((new WebDriverWait(this.driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(by))).isSelected();
        } catch (Exception var4) {
            System.err.println(var4.getMessage());
            return false;
        }
    }

    protected boolean validateElementIsEnabledOrNot(By by, Duration timeout) {
        try {
            return ((new WebDriverWait(this.driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(by))).isEnabled();
        } catch (Exception var4) {
            System.err.println(var4.getMessage());
            return false;
        }
    }

    protected boolean validateElementIsEnabledOrNot(By by) {
        return this.validateElementIsEnabledOrNot(by, this.defaultTimeout);
    }

    protected boolean validateElementVisibleInViewport(By by) {
        return (Boolean) ((JavascriptExecutor) this.driver).executeScript("var elem = arguments[0], box = elem.getBoundingClientRect();if (box.top < window.innerHeight){ return true}  return false  ", new Object[]{this.findElement(by)});
    }

    protected void sendKeysText(By selector, String text) {
        var element = findElement(selector);
        scrollToElementUsingActions(selector);
        driver.switchTo().activeElement();
        element.sendKeys(text);
    }

    protected void highlightText(String text) {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.HOME).build().perform();
        int length = text.substring(0, text.indexOf(" ")).length();

        actions.keyDown(Keys.LEFT_SHIFT);
        for (int i = 0; i < length; i++) {
            actions.sendKeys(Keys.ARROW_RIGHT);
        }
        actions.keyUp(Keys.LEFT_SHIFT);
        actions.build().perform();
    }
}
