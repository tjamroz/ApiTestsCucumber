Feature: Basic Api functionality check
  Checking if endpoints are responding, and returning correct data

  Scenario: Is latest endpoint available and returns correct schema
    Given I have basic URL
    When I ask latest endpoint
    Then I get 200 HTTP code
    And I get correct latest JSON schema with currencies

  Scenario: Incomplete endpoint returns correct response
    Given I have basic URL
    When I ask incomplete endpoint
    Then I get 400 HTTP code
    And I get correct error response for incomplete url

#Can be made as scenario outline for edge cases with dates
  Scenario: Is endpoint queried with data available and returns correct schema
    Given I have basic URL
    When I ask for rates from 2019-01-06
    Then I get 200 HTTP code
    And Response contains correct date 2019-01-06
    And I get correct basic JSON schema with currencies

  Scenario: Endpoint queried with future data
    Given I have basic URL
    When I ask for rates from 2219-01-12
    Then I get 200 HTTP code
    And I get correct basic JSON schema with currencies
    And Response contains actual data

  Scenario: Is error response correct when using date before 1999
    Given I have basic URL
    When I ask for rates from 1998-12-31
    Then I get 400 HTTP code
    And I get correct error response for old date

  Scenario: Is latest endpoint available and returns correct schema
    Given I have basic URL
    When I ask latest endpoint
    Then I get 200 HTTP code
    And I get correct latest JSON schema with currencies

  Scenario: Error handling for wrong base parameter
    Given I have basic URL
    And I want to set Base to randomstring
    When I ask latest endpoint
    Then I get 400 HTTP code
    And I get correct error response for not supported base


