/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author matt
 */
public final class PixelsOnlyKeyListener extends KeyAdapter {

    public static final int MAX_DIGITS = 4;
    private static PixelsOnlyKeyListener singletonInstance;
    
    static {
        singletonInstance = new PixelsOnlyKeyListener();
    }
    
    private PixelsOnlyKeyListener() {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        try {
            JTextField textField = (JTextField) e.getComponent();
            if (textField.getText().length() >= MAX_DIGITS) {
                e.consume();
                return;
            }
            
            String keyString = String.valueOf(e.getKeyChar());
            if (!keyString.isEmpty()) {
                Integer.valueOf(keyString);
            }
        } catch (NumberFormatException | ClassCastException ex) {
            e.consume();
        }
    }
    
    public static PixelsOnlyKeyListener getInstance() {
        return singletonInstance;
    }

}
