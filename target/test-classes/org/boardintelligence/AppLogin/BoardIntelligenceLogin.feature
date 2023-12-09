@BoardIntelligenceLogin
Feature: BoardIntelligenceLogin

  Background:
    Given I navigate to Board Intelligence application

  Scenario: Verify board intelligence main page is shown
    Then I should see Board Intelligence main page
    
    
   Scenario: Login with bad user email or password
    
    When I click on Login link next to Book a Demo button
    And I click on Login under Lucia section
    And I enter in an invalid email address "random@"
    Then I see the error message for invalid login "INVALID EMAIL ADDRESS"
    
    
    
     