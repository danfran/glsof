Feature: Exit from the application
  To quit the application it is possible using the top right menu item or a shortcut

  @exit
  Scenario: quitting the application from the menu
    Given I clicked on the 'Exit' option
    Then the application should be closed

  @exit
  Scenario: quitting the application from the menu's shortcut Ctrl+Q
    Given I pressed the shortcut 'Ctrl Q'
    Then the application should be closed