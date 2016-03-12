/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.common;

import cma.ConsoleInterface;
import cma.view.ImportRegistrationDialog;
import cma.vo.ImportRegistrationVO;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class ImportRegistrationHelper
{
    public static Collection execute()
    {
        // Get import file name and separator (if text file)
        ImportRegistrationVO vo = ImportRegistrationDialog.showDialog();
        if (vo != null) {
            System.out.println("Importera anmälningar enligt:");
            System.out.println("->" + vo);
            // read import file according to supplied paremeters and handle result
            try {
                InputStream is = new FileInputStream(vo.getFileName());
                System.out.println("Importfile selected: " + vo.getFileName());
                // För varje rad i filen så skapa en nu anmälan
                return ImportHelper.parseRegistrationCsvFile(is, vo.getSeparator());
            }
            catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(ConsoleInterface.getInstance(), "Kunde inte öppna vald importfil: " + vo.getFileName());
                e1.printStackTrace();
            }
            catch (IOException e1) {
                JOptionPane.showMessageDialog(ConsoleInterface.getInstance(), "Fel vid låsning av vald importfil: " + vo.getFileName());
                e1.printStackTrace();
            }
        }
        return new ArrayList();
    }
}
