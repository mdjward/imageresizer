/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.ui;

import imageresizer.lib.ImageFileFilter;
import imageresizer.lib.ImageResizeBatch;
import imageresizer.lib.ImageResizer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

/**
 *
 * @author matt
 */
public class ImageResizerPanel extends JPanel implements Observer, ActionListener {
    
    private static final int COMPONENT_PADDING = 10;
    private static final int SEPARATOR_HEIGHT = 5;
    private static final int TEXT_FIELD_LABEL_WIDTH = 200;
    private static final int START_BUTTON_WIDTH = 150;
    private static final int IMAGE_SIZE_TEXT_FIELD_WIDTH = 100;
    private static final String DIRECTORY_BUTTON_TEXT = "...";

    private JTextField txtImageSourcePath;
    private JTextField txtImageSuccessPath;
    private JTextField txtImageFailurePath;
    private JTextField txtMaxImageSize;
    private JTextField txtMinImageSize;
    private JSpinner spnThreads;
    private JTextArea txtOutputLog;
    private JButton btnSelectImageSourcePath;
    private JButton btnSelectImageSuccessPath;
    private JButton btnSelectImageFailurePath;
    private JButton btnStart;
    private JFileChooser chooser;

    
    
    public ImageResizerPanel() {
        
        super();
        
        
        
        // Image source path
        JLabel lblImageSourcePath = new JLabel("Source directory:");
        
        this.txtImageSourcePath = new JTextField();
        this.txtImageSourcePath.setEditable(false);
        
        this.btnSelectImageSourcePath = new JButton(DIRECTORY_BUTTON_TEXT);
        this.btnSelectImageSourcePath.addActionListener(new DirectorySelectorActionListener(this, this.txtImageSourcePath));
        
        
        
        // Image success path
        JLabel lblImageSuccessPath = new JLabel("Target directory:");
        
        this.txtImageSuccessPath = new JTextField();
        this.txtImageSuccessPath.setEditable(false);
        
        this.btnSelectImageSuccessPath = new JButton(DIRECTORY_BUTTON_TEXT);
        this.btnSelectImageSuccessPath.addActionListener(new DirectorySelectorActionListener(this, this.txtImageSuccessPath));

        
        
        // Image failure path
        JLabel lblImageFailurePath = new JLabel("Copy failed files to:");
        
        this.txtImageFailurePath = new JTextField();
        this.txtImageFailurePath.setEditable(false);
        
        this.btnSelectImageFailurePath = new JButton(DIRECTORY_BUTTON_TEXT);
        this.btnSelectImageFailurePath.addActionListener(new DirectorySelectorActionListener(this, this.txtImageFailurePath));
        
        
        
        // Maximum image size
        JLabel lblMaxImageSize = new JLabel("Maximum size (pixels):");
        
        this.txtMaxImageSize = new JTextField(String.valueOf(ImageResizer.DEFAULT_MAX_IMAGE_SIZE));
        this.txtMaxImageSize.addKeyListener(PixelsOnlyKeyListener.getInstance());
        
        
        
        // Minimum image size
        JLabel lblMinImageSize = new JLabel("Minimum size (pixels):");
        
        this.txtMinImageSize = new JTextField(String.valueOf(ImageResizer.DEFAULT_MIN_IMAGE_SIZE));
        this.txtMinImageSize.addKeyListener(PixelsOnlyKeyListener.getInstance());
        
        
        
        // Top separator
        JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        
        
        
        // Number of threads
        JLabel lblThreads = new JLabel("Number of images that may be processed simultaneously:");
        
        this.spnThreads = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        
        
        
        // Bottom separator
        JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        
        
        
        // Output log
        JLabel lblOutputLog = new JLabel("Output log:");
        
        this.txtOutputLog = new JTextArea();
        this.txtOutputLog.setEditable(false);
        
        JScrollPane scrOutputLogWrapper = new JScrollPane(
            this.txtOutputLog,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrOutputLogWrapper.setAutoscrolls(true);
        
        
        
        // Start button
        this.btnStart = new JButton("Process");
        this.btnStart.addActionListener(this);
        
        
        
        // 
        this.chooser = new JFileChooser();
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        
        
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGap(COMPONENT_PADDING)
                .addGroup(
                    layout.createParallelGroup()
                        .addGroup(
                            layout.createSequentialGroup()
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblImageSourcePath, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH)
                                        .addComponent(lblImageSuccessPath, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH)
                                        .addComponent(lblImageFailurePath, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH)
                                )
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING, true)
                                        .addComponent(this.txtImageSourcePath)
                                        .addComponent(this.txtImageSuccessPath)
                                        .addComponent(this.txtImageFailurePath)
                                )
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(this.btnSelectImageSourcePath)
                                        .addComponent(this.btnSelectImageSuccessPath)
                                        .addComponent(this.btnSelectImageFailurePath)
                                )
                        )
                        .addGroup(
                            layout.createSequentialGroup()
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblMaxImageSize, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH)
                                        .addComponent(lblMinImageSize, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH, TEXT_FIELD_LABEL_WIDTH)
                                )
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                        .addComponent(this.txtMaxImageSize, IMAGE_SIZE_TEXT_FIELD_WIDTH, IMAGE_SIZE_TEXT_FIELD_WIDTH, IMAGE_SIZE_TEXT_FIELD_WIDTH)
                                        .addComponent(this.txtMinImageSize, IMAGE_SIZE_TEXT_FIELD_WIDTH, IMAGE_SIZE_TEXT_FIELD_WIDTH, IMAGE_SIZE_TEXT_FIELD_WIDTH)
                                )
                        )
                        .addComponent(topSeparator)
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(lblThreads)
                                .addComponent(this.spnThreads)
                        )
                        .addComponent(bottomSeparator)
                        .addComponent(lblOutputLog)
                        .addComponent(scrOutputLogWrapper)
                        .addComponent(btnStart, GroupLayout.Alignment.TRAILING, START_BUTTON_WIDTH, START_BUTTON_WIDTH, START_BUTTON_WIDTH)
                )
                .addGap(COMPONENT_PADDING)
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(COMPONENT_PADDING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblImageSourcePath)
                                .addComponent(this.txtImageSourcePath)
                                .addComponent(this.btnSelectImageSourcePath)
                        )
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblImageSuccessPath)
                                .addComponent(this.txtImageSuccessPath)
                                .addComponent(this.btnSelectImageSuccessPath)
                        )
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblImageFailurePath)
                                .addComponent(this.txtImageFailurePath)
                                .addComponent(this.btnSelectImageFailurePath)
                        )
                )
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMaxImageSize)
                        .addComponent(this.txtMaxImageSize)
                )
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMinImageSize)
                        .addComponent(this.txtMinImageSize)
                )
                .addComponent(topSeparator, SEPARATOR_HEIGHT, SEPARATOR_HEIGHT, SEPARATOR_HEIGHT)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(lblThreads)
                        .addComponent(this.spnThreads)
                )
                .addComponent(bottomSeparator, SEPARATOR_HEIGHT, SEPARATOR_HEIGHT, SEPARATOR_HEIGHT)
                .addComponent(lblOutputLog)
                .addComponent(scrOutputLogWrapper)
                .addComponent(this.btnStart)
                .addGap(COMPONENT_PADDING)
        );
        
    }
    
    private void setComponentLock(boolean lock) {
        boolean enable = !lock;
        
        for (Component c : getComponents()) {
            c.setEnabled(enable);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            logOutput((String) arg);
        }
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnStart) {
            try {
                String sourcePath = this.txtImageSourcePath.getText();
                String successPath = this.txtImageSuccessPath.getText();
                String failurePath = this.txtImageFailurePath.getText();
                String givenMaxSize = this.txtMaxImageSize.getText();
                String givenMinSize = this.txtMinImageSize.getText();
                
                if (sourcePath.isEmpty() || successPath.isEmpty() || failurePath.isEmpty()) {
                    throw new Exception("you must specify a valid source directory, save-to directory and failure-copy-to directory.");
                }
                
                if (sourcePath.equals(successPath) || sourcePath.equals(failurePath)) {
                    throw new Exception("neither save-to directory nor failure-copy-to directory may be the same as the source directory.");
                }
                
                if (givenMaxSize.isEmpty() || givenMinSize.isEmpty()) {
                    throw new Exception("Both minimum and maximum size must be given.");
                }
                
                int maxSize = Integer.valueOf(this.txtMaxImageSize.getText());
                int minSize = Integer.valueOf(this.txtMinImageSize.getText());
                int threads = Integer.valueOf(((SpinnerNumberModel) this.spnThreads.getModel()).getNumber().intValue());
                
                final ImageResizeBatch batch = new ImageResizeBatch(sourcePath, successPath, failurePath, threads, maxSize, minSize);
                batch.addObserver(this);
                
                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        setComponentLock(true);
                        batch.run();
                        setComponentLock(false);
                        
                        return null;
                    }
                    
                }.execute();
                
            } catch (Exception ex) {
                logOutput("Error: " + ex.getMessage());
            }
            
        }
    }
    
    protected synchronized void logOutput(String message) {
        this.txtOutputLog.append(message + "\n");
    }
    
    private class DirectorySelectorActionListener implements ActionListener {

        private Component parentComponent;
        private JTextComponent targetComponent;
        
        
        public DirectorySelectorActionListener(Component parentComponent, JTextComponent targetComponent) {
            this.parentComponent = parentComponent;
            this.targetComponent = targetComponent;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.showDialog(this.parentComponent, "Select directory");
            
            File selection = chooser.getSelectedFile();
            if (selection != null) {
                logDirectorySelection(this.targetComponent, selection);
            }
        }
        
        private void logDirectorySelection(Component trigger, File path) {
            String selectedPath = path.getAbsolutePath();
            String logMessage = null;
            
            this.targetComponent.setText(selectedPath);
            
            if (trigger == txtImageSourcePath) {
                logOutput("Image source directory set to: " + selectedPath);
                logOutput(this.getImagesInDirectory(path) + " allowed image files found\n");
            } else if (trigger == txtImageSuccessPath) {
                logOutput("Resized image save-to directory set to: " + selectedPath + "\n");
            } else if (trigger == txtImageFailurePath) {
                logOutput("Failed image copy-to directory set to: " + selectedPath + "\n");
            }
        }
        
        private int getImagesInDirectory(File path) {
            return path.listFiles(new ImageFileFilter()).length;
        }

    }
    
}

