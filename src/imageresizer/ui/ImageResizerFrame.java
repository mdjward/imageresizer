package imageresizer.ui;

import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Provides a JFrame window encapsulating the main UI of the application
 * 
 * @author M.D.Ward
 */
public class ImageResizerFrame extends JFrame {
    
    /**
     * JPanel implementation that serves as the frame's content pane
     */
    private ImageResizerPanel mainPanel;
    
    /**
     * Constructor - creates a new instance of ImageResizerFrame and configures
     * the base frame parameters
     * 
     * @throws HeadlessException 
     *      If GraphicsEnvironment.isHeadless() returns true, as per the JFrame
     *      superclass constructor
     */
    public ImageResizerFrame() throws HeadlessException {
        
        // Invoke the superclass constructor to set up the base frame
        super();
        
        // Set default close operation to dispose this object upon closure
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Set the title
        this.setTitle("Image Resizer v1.0");
        
        // Initialise the content pane and set to the frame
        this.mainPanel = new ImageResizerPanel();
        this.setContentPane(mainPanel);
        
        // Set the size (as both the default and the minimum), but allow for resizing
        this.setSize(640, 480);
        this.setMinimumSize(this.getSize());
        this.setResizable(true);
        
        // Make the frame visible
        this.setVisible(true);
    }
    
    /**
     * Main method - entry point application logic
     * 
     * @param args 
     */
    public static void main(String[] args) {
        try {
            
            // Set the "system" look-and-feel to all UI components
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
            
            // Initialise a new image resizer frame (retaining a reference to the object is useless here)
            new ImageResizerFrame();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // Any of the caught exceptions should be handled in the same way
            Logger.getLogger(ImageResizerFrame.class.getName()).log(Level.SEVERE, "Unable to initialise ", ex);
        }
        
    }
    
}
