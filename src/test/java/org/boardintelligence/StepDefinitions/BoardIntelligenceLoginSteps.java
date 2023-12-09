package org.boardintelligence.StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.boardintelligence.TestContext;
import org.boardintelligence.pages.BoardIntelligenceLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Driver;
import java.time.Duration;

public class BoardIntelligenceLoginSteps {
    private final BoardIntelligenceLoginPage boardIntelligenceLoginPage;

    public BoardIntelligenceLoginSteps(TestContext context) {
        this.boardIntelligenceLoginPage = new BoardIntelligenceLoginPage(context);
    }

    @Given("I navigate to Board Intelligence application")
    public void iNavigateToBoardIntelligenceApplication() {
        boardIntelligenceLoginPage.navigateToBoardIntelligenceApp();

    }

    @Then("I should see Board Intelligence main page")
    public void iShouldSeeBoardIntelligenceMainPage() {
        assertTrue(boardIntelligenceLoginPage.pageDisplayed(), "Failed to load the Main page");
        
    }
 
//   @When("I click on \"Login\" link next to \"Book a Demo\" button")
//   public void click_on_login_button()
//   {
//     driver.findElement(By.xpath("//span[@data-type='subnav']//*[name()='svg'])[4]")).click();
//	  
//   }
//   @And("I click on Login button under Lucia section")
//   {
//	  //explicit wait
//	   WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
//	   w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://lucia.boardiq.co.uk']")));
//	   
//	   driver.findElement(By.xpath("//a[@href='https://lucia.boardiq.co.uk']")).click();
//		
//   }
//   
//   @And("I enter in an invalid email address {email} and see the error message for invalid login {string} ")
//   public void enter_in_an_invalid_email_address
//   {
//	   WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
//	   w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login_email']")))
//	   driver.findElement(By.xpath("//input[@id='login_email']")).sendKeys("random");
//   }
   
    @When("I click on Login link next to Book a Demo button")
    public void i_click_on_link_next_to_button() {
    	boardIntelligenceLoginPage.clickOnLogin();
    }

    @When("I click on Login under Lucia section")
    public void i_click_on_login_under_lucia_section() {
    	boardIntelligenceLoginPage.clickOnLuciaLogin();
    }

    @When("I enter in an invalid email address {string}")
    public void i_enter_in_an_invalid_email_address(String text) {
    	boardIntelligenceLoginPage.enterEmailAddres(text);
    }

    @Then("I see the error message for invalid login {string}")
    public void i_see_the_error_message_for_invalid_login(String string) {
    	boardIntelligenceLoginPage.verifyErrorMessage();
    }
}
