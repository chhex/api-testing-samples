# Created by chhex at 19.05.21
Feature: Some basic Test scenarios against the Sample Project

  Background:
    * url baseUrl

  Scenario: Delete all Testdata , Create, Update and Delete some Testdata
    * configure headers = { Authorization: 'Bearer .... whatever ' }
    # Delete all Testdata
    Given path 'testdata'
    When method delete
    Then status 200

    # Check empty
    Given path 'testdata'
    When method get
    Then status 200
    And match response == []
    

    # Create Testdata
    Given path 'testdata'
    And request { textData: 'Initial Testdata' }
    When method post
    Then status 200
    And match response == { id: '#notnull', textData: 'Initial Testdata' }

    * def first_id = response.id

    # Update Testdata by Id
    Given path 'testdata',first_id
    And request { id: '#(first_id)', textData: 'Update Testdata' }
    When method put
    Then status 200
    And match response == { id: '#(first_id)', textData: 'Update Testdata' }

    # Find by Id
    Given path 'testdata',first_id
    When method get
    Then status 200
    And match response == { id: '#(first_id)', textData: 'Update Testdata' }

    # Find all
    Given path 'testdata'
    When method get
    Then status 200
    And match response contains { id: '#(first_id)', textData: 'Update Testdata' }
    And response.length == 1

    # Create additional Testdata
    Given path 'testdata'
    And request { textData: 'Some Additional Testdata' }
    When method post
    Then status 200
    And match response == { id: '#notnull', textData: 'Some Additional Testdata' }

    * def next_id = response.id

    # Find by Id
    Given path 'testdata',next_id
    When method get
    Then status 200
    And match response == { id: '#(next_id)', textData: 'Some Additional Testdata' }

    # Find all
    Given path 'testdata'
    When method get
    Then status 200
    And match response contains { id: '#(first_id)', textData: 'Update Testdata' }
    And match response contains { id: '#(next_id)', textData: 'Some Additional Testdata' }
    And response.length == 2

    # Calculate
    Given path 'testdata',next_id, '2+2'
    And request { }
    When method put
    Then status 200
    And match response == { id: '#(next_id)', textData: 'Calculation Result of 2+2 is 4.0' }

    # Delete First
    Given path 'testdata',first_id
    When method delete
    Then status 200

     # Find all
    Given path 'testdata'
    When method get
    Then status 200
    And match response contains { id: '#(next_id)', textData: 'Calculation Result of 2+2 is 4.0' }
    And response.length == 1