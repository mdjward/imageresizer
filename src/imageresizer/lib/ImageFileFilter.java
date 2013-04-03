/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresizer.lib;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author matt
 */
public class ImageFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()
                /*&& !(new File(getSaveToPath().getAbsolutePath() + "/resized/" + pathname.getName()).exists())
                && !(new File(getFailureCopyPath().getAbsolutePath() + "/resize-failed/" + pathname.getName()).exists())*/) {

            Pattern p = Pattern.compile("^.*(\\.jp)(e)?g$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(pathname.getName());

            return m.matches();
        } else {
            System.out.println("Rejecting " + pathname.getName() + " (" + pathname.length() + ")");
        }

        return false;
    }
}
