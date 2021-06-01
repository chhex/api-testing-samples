# Created by chhex at 19.05.21
Feature: Some basic Test scenarios against the Sample Project

  Background:
    * url baseUrl

  Scenario: Delete all Testdata , Create, Update and Delete some Testdata
    * configure headers = { Authorization: 'Bearer .... whatever ' }
    # Delete all Testdata
    Given path 'api/pm'
    When method delete
    Then status 200

     # Create Address
    Given path 'api/pm/address'
    And request { streetNumber: '007', streetName: 'Hackerswyler', postalCode: '8306' }
    When method post
    Then status 200
    And match response == { id: '#notnull', streetNumber: '007', streetName: 'Hackerswyler', postalCode: '8306' }

    * def first_id = response.id
     # Create Another Address
    Given path 'api/pm/address'
    And request { streetNumber: '1', streetName: 'Umgasse', postalCode: '8400' }
    When method post
    Then status 200
    And match response == { id: '#notnull', streetNumber: '1', streetName: 'Umgasse', postalCode: '8400' }

    * def second_id = response.id

     # Find all
    Given path 'api/pm/address'
    When method get
    Then status 200
    And match response contains { id: '#(first_id)',  streetNumber: '007', streetName: 'Hackerswyler', postalCode: '8306' }
    And match response contains { id: '#(second_id)', streetNumber: '1', streetName: 'Umgasse', postalCode: '8400' }
    And response.length == 2