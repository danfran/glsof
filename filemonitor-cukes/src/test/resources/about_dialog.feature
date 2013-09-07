Feature:
  In order to read general information about the application I want to open
  the about dialog from the main menu

  Background:
    Given I clicked on the 'About' option
    When 'About' dialog is displayed

  Scenario: As I click Close button on the About dialog, it should be closed
    When I click 'Close' button on the dialog
    Then the 'About' dialog should be closed

  Scenario: As I click about from the menu, I should see the about dialog
    Then I should see general information about the application

  Scenario: As I click 'License' button on the about dialog, I should see the license's dialog
    When I click the 'License' button
    Then I should see the 'License' dialog
    When I click 'Close' button on the dialog
    Then the 'License' dialog should be closed