Feature: Basic Authentication
		
	@Tag2	
   Scenario: Login with invalid credentials
    Given Login page is displayed
    When Enter Invalid Credentials
    When Click Login Button
    Then Verify the Validation Error message

  @Tag1
  Scenario: Login with valid credentials
    Given Login page is displayed
    When Enter Valid Credentials
    When Click Login Button
    Then Verify the user is logged in
