Feature: Sample service to get names from database

Scenario: client makes call to GET /names
    When the client calls /names
    Then the client receives status code of 200
    And the client receives list of names with 2 elements
    
Scenario: client makes call to POST /names
	When the client calls /names with id 3 and name AT&T
	Then the client receives status code of 200 on POST
	And GET call returns 3 elements with 3rd id 3 and name AT&T