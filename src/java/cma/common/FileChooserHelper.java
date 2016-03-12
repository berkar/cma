package cma.common;

import cma.CmaFileFilter;
import cma.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 17, 2003
 * Time: 10:21:40 PM
 * To change this template use Options | File Templates.
 */
public class FileChooserHelper
{
    private static JFileChooser openChooser;
    private static JFileChooser saveChooser;

    static
    {
        openChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        saveChooser = new JFileChooser(new File(System.getProperty("user.dir")));
    }

    public static String getFileName(Component parent)
    {
        return getOpenFileName(parent, new String[]{"dog", "html", "txt", "csv"});
    }

    public static String getOpenFileName(Component parent, String[] postfix)
    {
        // Get file name to store competition in
        //JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        CmaFileFilter filter = new CmaFileFilter(
            postfix, Constants.APPLICATION_NAME + " filer"
        );
        openChooser.setFileFilter(filter);
        int returnVal = openChooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return openChooser.getCurrentDirectory() + System.getProperty("file.separator") + openChooser.getSelectedFile().getName();
        }
        else {
            return null;
        }
    }

    public static String getSaveFileName(Component parent, String[] postfix)
    {
        // Get file name to store competition in
        //JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        CmaFileFilter filter = new CmaFileFilter(
            postfix, Constants.APPLICATION_NAME + " filer"
        );
        FileFilter[] filters = saveChooser.getChoosableFileFilters();
        for (int i = 0; i < filters.length; i++) {
            FileFilter tmp = filters[i];
            saveChooser.removeChoosableFileFilter(tmp);
        }
        saveChooser.setFileFilter(filter);
        int returnVal = saveChooser.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return saveChooser.getCurrentDirectory() + System.getProperty("file.separator") + saveChooser.getSelectedFile().getName();
        }
        else {
            return null;
        }
    }
}
