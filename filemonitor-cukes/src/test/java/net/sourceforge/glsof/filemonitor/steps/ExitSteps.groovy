package net.sourceforge.glsof.filemonitor.steps
import cucumber.api.java.After
import cucumber.api.java.Before
import groovy.transform.Field
import org.fest.swing.security.NoExitSecurityManagerInstaller

import java.awt.Event
import java.awt.event.KeyEvent

import static net.sourceforge.glsof.filemonitor.steps.RunCukesTest.getWindow
import static org.fest.swing.core.KeyPressInfo.keyCode

this.metaClass.mixin(cucumber.api.groovy.EN)

@Field
NoExitSecurityManagerInstaller noExitSecurityManagerInstaller

@Before("@exit")
def beforeScenario() {
    noExitSecurityManagerInstaller = NoExitSecurityManagerInstaller.installNoExitSecurityManager()
}

@After("@exit")
def afterScenario() {
    noExitSecurityManagerInstaller.uninstall()
}

Then(~'^the application should be closed$') {->
    window.requireNotVisible()
}

Given(~'^I pressed the shortcut \'Ctrl Q\'$') {->
    window.pressAndReleaseKey(keyCode(KeyEvent.VK_Q).modifiers(Event.CTRL_MASK))
}