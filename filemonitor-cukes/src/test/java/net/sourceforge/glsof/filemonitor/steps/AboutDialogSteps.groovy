package net.sourceforge.glsof.filemonitor.steps
import net.sourceforge.glsof.common.about.AboutDialog
import net.sourceforge.glsof.common.about.TextDialog
import org.fest.swing.core.matcher.JButtonMatcher
import org.fest.swing.finder.WindowFinder
import org.fest.swing.fixture.DialogFixture

import static net.sourceforge.glsof.filemonitor.steps.RunCukesTest.getWindow
import static org.fest.assertions.Assertions.assertThat

this.metaClass.mixin(cucumber.api.groovy.EN)

def DialogFixture dialog

def click(String buttonText) {
    ['on': { DialogFixture dialog ->
        dialog.button(JButtonMatcher.withText(buttonText)).click()
    }]
}

Closure checkLabelText = { name, expectedText -> assertThat(dialog.label(name).text()).isEqualTo(expectedText) }

Given(~'^I clicked on the \'(.*)\' option$') { item ->
    window.menuItem(item.toLowerCase()).click()
}

When(~'^\'About\' dialog is displayed$') { ->
    dialog = WindowFinder.findDialog(AboutDialog.class).using(window.robot)
}

Then(~'^I should see general information about the application$') {->
    checkLabelText("description", "Monitor for the activity of files, processes and network");
    checkLabelText("version", "Version 2.4.0");
    checkLabelText("copyright", "Copyleft © 2013 Daniele Francesconi");
    checkLabelText("email", "Email: dfrancesconi12@gmail.com");
}

When(~'^I click the \'(.*)\' button$') { button ->
    click button on dialog
}

Then(~'^I should see the \'License\' dialog$') {->
    dialog = WindowFinder.findDialog(TextDialog.class).using(window.robot)
}

When(~'^I click \'(Close)\' button on the dialog$') { button ->
    click button on dialog
}

Then(~'^the \'(.*)\' dialog should be closed$') { dialogName ->
    dialog.requireNotVisible()
}