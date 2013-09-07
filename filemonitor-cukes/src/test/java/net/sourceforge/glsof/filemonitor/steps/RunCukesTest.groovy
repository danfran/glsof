package net.sourceforge.glsof.filemonitor.steps
import cucumber.api.groovy.Hooks
import cucumber.api.junit.Cucumber
import net.sourceforge.glsof.filemonitor.FileMonitorWindow
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.FrameFixture
import org.junit.runner.RunWith

import static net.sourceforge.glsof.common.i18n.Messages.initNLS

@RunWith(Cucumber.class)
class RunCukesTest {

    static FrameFixture window

    public RunCukesTest() {
        Hooks.Before({
            initNLS("nl.common")
            def query = { new FileMonitorWindow() } as GuiQuery<FileMonitorWindow>
            FileMonitorWindow frame = GuiActionRunner.execute(query)
            window = new FrameFixture(frame).show()
        })

        Hooks.After() {
            window.cleanUp()
        }

    }


}