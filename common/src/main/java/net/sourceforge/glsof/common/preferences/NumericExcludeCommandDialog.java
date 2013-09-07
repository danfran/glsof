package net.sourceforge.glsof.common.preferences;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class NumericExcludeCommandDialog extends TextExcludeCommandDialog {
	
    public NumericExcludeCommandDialog(LsofParameterType type) {
    	super(type);
    	
    	_text.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (!Character.isDigit(c) || c == '\b') ke.consume(); // prevent event propagation
            }

            @Override public void keyPressed(KeyEvent keyEvent) {}
            @Override public void keyReleased(KeyEvent keyEvent) {}
        });
    }
	
}
