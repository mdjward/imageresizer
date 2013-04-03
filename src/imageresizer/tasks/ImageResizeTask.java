/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.tasks;

import imageresizer.lib.ImageResizeBatch;
import imageresizer.lib.ImageResizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Observable;
import javax.imageio.ImageIO;

/**
 *
 * @author matt
 */
public class ImageResizeTask implements Runnable {
    private ImageResizeBatch batch;
    private File imageFile;
    
    public ImageResizeTask(ImageResizeBatch batch, File imageFile) {
        this.batch = batch;
        this.imageFile = imageFile;
    }
    
    public File getImageFile() {
        return this.imageFile;
    }
    
    @Override
    public void run() {
        try {
            BufferedImage resizedImage = new ImageResizer(this.imageFile, this.batch.getMaxSize(), this.batch.getMinSize()).getResizedCopy();
            ImageIO.write(resizedImage, "jpeg", new File(this.batch.getSaveToPath(), this.imageFile.getName()));
            
            resizedImage.flush();
            
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            
            handleFailedFile();
        }
        
        this.batch.markTaskComplete(this);
    }
    
    private synchronized void handleFailedFile() {
        try {
            Files.copy(new FileInputStream(this.imageFile), FileSystems.getDefault().getPath(this.batch.getFailureCopyPath().getAbsolutePath(), this.imageFile.getName()));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
