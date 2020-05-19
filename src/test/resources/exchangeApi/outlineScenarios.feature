Feature: Veryfing parameters with data driven scenarios
  Checking if endpoints are responding, and returning correct data

  Scenario Outline: Getting only 1 currency with symbol set
    Given I have basic URL
    And I want to get rates for currency <symbol>
    When I ask latest endpoint
    Then I get 200 HTTP code
    And I get only specified <symbol> in response

    Examples:
      | symbol |
      | GBP  |
      | HKD  |
      | IDR  |
      | ILS  |
      | DKK  |
      | INR  |
      | CHF  |
      | MXN  |
      | CZK  |
      | SGD  |
      | THB  |
      | HRK  |
      | MYR  |
      | NOK  |
      | CNY  |
      | BGN  |
      | PHP  |
      | SEK  |
      | PLN  |
      | ZAR  |
      | CAD  |
      | ISK  |
      | BRL  |
      | RON  |
      | NZD  |
      | TRY  |
      | JPY  |
      | RUB  |
      | KRW  |
      | USD  |
      | HUF  |
      | AUD  |
      | EUR  |

  Scenario Outline: <base> is correctly set with available currencies
    Given I have basic URL
    And I want to set Base to <base>
    When I ask latest endpoint
    Then I get 200 HTTP code
    And I get correct <base> set

    Examples:
      | base |
      | GBP  |
      | HKD  |
      | IDR  |
      | ILS  |
      | DKK  |
      | INR  |
      | CHF  |
      | MXN  |
      | CZK  |
      | SGD  |
      | THB  |
      | HRK  |
      | MYR  |
      | NOK  |
      | CNY  |
      | BGN  |
      | PHP  |
      | SEK  |
      | PLN  |
      | ZAR  |
      | CAD  |
      | ISK  |
      | BRL  |
      | RON  |
      | NZD  |
      | TRY  |
      | JPY  |
      | RUB  |
      | KRW  |
      | USD  |
      | HUF  |
      | AUD  |
      | EUR  |

