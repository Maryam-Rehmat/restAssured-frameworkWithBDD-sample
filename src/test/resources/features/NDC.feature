Feature: NDC API to Retrieve Strength against NDC Codes Using Redis DB

  Scenario: Get Strength against given valid NDC(10 digit code with hyphen)
    When send a request with valid NDC code "63545-998-03" and the "STRENGTH" attribute
    Then response shows the strength by combining substanceName, activeNumeratorStrength and ACTIVE_INGRED_UNIT