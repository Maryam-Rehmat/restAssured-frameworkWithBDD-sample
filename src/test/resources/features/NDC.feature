Feature: NDC API to Retrieve Strength against NDC Codes Using Redis DB

  Scenario: Get strength for a valid NDC code
    Given a valid NDC code "63545-998-03"
    When I send a request with the "STRENGTH" attribute
    Then the correct strength is displayed

  Scenario: Get Strength against given valid NDC(11 digit code without hyphen)
    Given a valid NDC code "00002298026"
    When I send a request with the "STRENGTH" attribute
    Then the correct strength is displayed

  Scenario: Get Strength against given valid NDC with mutliple attributes
    Given a valid NDC code "63545-998-03"
    When I send a request with the "STRENGTH" attribute
    Then the correct strength is displayed

  Scenario: Not Found error should be shown when user give valid NDC code but it does not exist in redis db
    Given a valid NDC code "63545-998-00"
    When I send a request with the "STRENGTH" attribute
    Then Not found error should be shown

  Scenario: 400 Bad Request Error for Invalid Request
    Given a valid NDC code "63545-998-03"
    When I send a invalid request
    Then a Bad Request error is returned

  Scenario: 500 Internal Server Error When extra comma is added while giving attributes
    Given a valid NDC code "63545-998-03"
    When I send a request with the "STRENGTH" attribute by giving extra comma
    Then Internal Server Error is returned



