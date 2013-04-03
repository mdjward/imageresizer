/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.lib;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author matt
 */
public class ImageResizer {
    
    public static final int MAX_IMAGE_FILE_SIZE = 15728640;
    public static final int DEFAULT_MAX_IMAGE_SIZE = 1500;
    public static final int DEFAULT_MIN_IMAGE_SIZE = 480;
    
    private ImageResizeDimensions newSize;
    private Image originalImage;
    
    public ImageResizer(File imageFile, int maxSize, int minSize) throws IOException {
        
        this.originalImage = ImageIO.read(imageFile);
        this.newSize = new ImageResizeDimensions(this.originalImage, maxSize, minSize);
    }
    
    public BufferedImage getResizedCopy() {
        int size = this.newSize.getNominalSize();

        BufferedImage scaledImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) scaledImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setComposite(AlphaComposite.Src);
        
        g.clearRect(0, 0, size, size);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(0d, 0d, (double) size, (double) size));
        
        g.drawImage(
                this.originalImage,
                this.newSize.getXStart(),
                this.newSize.getYStart(),
                this.newSize.width,
                this.newSize.height,
                null
        );
        g.dispose();
        
        return scaledImage;
        
    }
}
