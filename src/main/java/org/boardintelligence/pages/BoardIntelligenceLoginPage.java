package org.boardintelligence.pages;

import org.boardintelligence.LightningComponent;
import org.boardintelligence.TestContext;
import org.boardintelligence.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BoardIntelligenceLoginPage extends LightningComponent {
	
	
    public static final String BI_MAIN_PAGE = "//a[@aria-label='Visit our Home Page']";
    public static final String Login = "(//span[@data-type='subnav'])[4]";
    public static final String Lucia_Login ="//a[@href='https://lucia.boardiq.co.uk']";
    public static final String email ="//input[@id='login_email']";
    public static final String continue_button = "//*[@id='login']";
    public static final String error_alert = "//*[@class='error-area']//span[1]";
    
    By errorAlert = By.xpath(error_alert);

    public BoardIntelligenceLoginPage(TestContext context) {
        super(context.getRoman());
    }

    public String getUri() {
        return Utils.getBaseUrl();
    }

    public void navigateToBoardIntelligenceApp() {
        navigateTo(getUri());
    }

    public boolean pageDisplayed() {
        return validateElementIsEnabledAndDisplayed(by(BI_MAIN_PAGE));
    }
    
    
    public void clickOnLogin()
    {
    	//clickElement(Login);
    }
    
    public void clickOnLuciaLogin()
    {
    	clickElement(Lucia_Login);
    }
    
    public void enterEmailAddres(String text)
    {
    	sendKeysText(text, email);
    	clickElement(continue_button);
    }
    
    public void verifyErrorMessage()
    {
    	String actualMessage = getText(errorAlert);
    	System.out.println(actualMessage);
    }
    
}









