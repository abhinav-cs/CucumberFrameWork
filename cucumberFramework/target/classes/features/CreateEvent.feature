Feature: Create Event

	Scenario: Create event with required fields
	Given Event page is displayed
	When Click add event button
	When Fill required fields
	When Click Submit Button
	Then Verify event is created
	
	Scenario: Create event with all fields
	Given Event page is displayed
	When Click add event button
	When Fill All fields
	When Click Submit Button
	Then Verify event is created