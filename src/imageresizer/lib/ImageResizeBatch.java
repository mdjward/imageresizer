/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.lib;

import imageresizer.tasks.ImageResizeTask;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author matt
 */
public class ImageResizeBatch extends Observable implements Runnable {

    public static final int DEFAULT_THREAD_COUNT = 10;
    
    private File basePath;
    private File saveToPath;
    private File failureCopyPath;
    private ExecutorService threadPool;
    private List<ImageResizeTask> taskList;
    private int maxSize;
    private int minSize;
    
    public ImageResizeBatch(String basePath, String saveToPath, String failureCopyPath, int maxThreads, int maxSize, int minSize) throws IOException {

        // Set up base path, save-to path and failure-copy path
        this.basePath = validateDirectoryPath(basePath);
        this.saveToPath = validateDirectoryPath(saveToPath);
        this.failureCopyPath = validateDirectoryPath(failureCopyPath);

        // Initialise the thread pool and the task list
        this.threadPool = Executors.newFixedThreadPool(maxThreads);
        this.taskList = new ArrayList<>();
        
        // Save maximum and minimum size
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public ImageResizeBatch(String basePath, String saveToPath, String failureCopyPath, int maxSize, int minSize) throws IOException {
        this(basePath, saveToPath, failureCopyPath, DEFAULT_THREAD_COUNT, maxSize, minSize);        
    }

    @Override
    public synchronized void run() {
        
        FileFilter filter = new ImageFileFilter();
        
        for (File imageFile : this.basePath.listFiles(filter)) {
            this.taskList.add(new ImageResizeTask(this, imageFile));
        }
        
        this.setChanged();
        this.notifyObservers("Processing " + this.taskList.size() + " files...");
        
        for (ImageResizeTask task : this.taskList) {
            this.threadPool.submit(task);
        }
        
        try {
            this.wait();

            setChanged();
            notifyObservers("Processing complete.");
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            
            setChanged();
            notifyObservers("Multi-threading failure (interruption): " + ex.getMessage());
        }
    }
    
    public synchronized void markTaskComplete(ImageResizeTask task) {
        this.taskList.remove(task);
        setChanged();
        notifyObservers("Successfully resized " + task.getImageFile().getName());

        if (this.taskList.isEmpty()) {
            this.notifyAll();
            
        }
    }

    public File getBasePath() {
        return basePath;
    }

    public File getSaveToPath() {
        return saveToPath;
    }

    public File getFailureCopyPath() {
        return failureCopyPath;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMinSize() {
        return minSize;
    }
    
    private static File validateDirectoryPath(String path) throws IOException {
        File f = new File(path);

        if (f.isDirectory()) {
            return f;
        }

        throw new IOException("Unable to find a directory at path: " + path);
    }
    
}

