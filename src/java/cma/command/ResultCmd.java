/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import cma.ConsoleInterface;
import cma.common.CalculateResultHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResultCmd extends RequiredCommand
{
    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        int option = JOptionPane.showConfirmDialog(
            parent,
            "Vill du att resultat ska beräknas för hela tävlingen?",
            "Resultat beräknas för hela tävlingen!",
            JOptionPane.YES_NO_OPTION
        );
        if(option == JOptionPane.YES_OPTION) {
            CalculateResultHelper.execute(parent);
        }
    }
}
