/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.lib;

/**
 *
 * @author matt
 */
public class ImageTooBigException extends Exception {

    private String filename;
    private long fileSize;
    
    public ImageTooBigException(String filename, long fileSize) {
        super("File size of '" + filename + "' " + fileSize + " exceeds maximum limit");
        
        this.filename = filename;
        this.fileSize = fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public long getFileSize() {
        return fileSize;
    }
    
}
