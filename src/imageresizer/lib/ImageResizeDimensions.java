/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.lib;

import java.awt.Dimension;
import java.awt.Image;

/**
 *
 * @author matt
 */
public class ImageResizeDimensions extends Dimension {
    
    private int xStart;
    private int yStart;
    private int nominalSize;
    
    public ImageResizeDimensions(int width, int height, int maxSize, int minSize) {
        super();
        
        if (maxSize < minSize) {
            throw new IllegalArgumentException("Minimum size is greater than maximum size");
        }
        
        double biggestComponent = (double) (width > height ? width : height);
        double multiplier = (biggestComponent > maxSize ? biggestComponent / maxSize : 1);
        
        this.width = (int) (width / multiplier);
        this.height = (int) (height / multiplier);
        this.nominalSize = (int) (biggestComponent / multiplier);
        
        this.xStart = getStart(this.width, this.nominalSize, minSize);
        this.yStart = getStart(this.height, this.nominalSize, minSize);
        
        this.nominalSize = (nominalSize < minSize ? minSize : nominalSize);
    }

    public ImageResizeDimensions(Image originalImage, int maxSize, int minSize) {
        this(originalImage.getWidth(null), originalImage.getHeight(null), maxSize, minSize);
    }
    
    public int getXStart() {
        return xStart;
    }
    
    public int getYStart() {
        return yStart;
    }
    
    public int getNominalSize() {
        return nominalSize;
    }
    
    private static int getStart(int componentSize, int biggestSize, int minSize) {
        int diffToMin = (componentSize < minSize ? minSize - componentSize : 0);
        int diff = biggestSize + diffToMin - componentSize;
        
        return (diff > 0 ? diff / 2 : 0);
    }

}
