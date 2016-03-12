/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import org.jdom.Element;
import cma.ConsoleInterface;
import cma.view.CompetitionPropertiesDialog;
import cma.vo.CompetitionPropertiesVO;

import java.awt.event.ActionEvent;

public class CompetitionPropertiesCmd extends CommandAdapter
{
    public boolean preProcess(ActionEvent e, ConsoleInterface parent)
    {
        if (ConsoleInterface.getCompetition() == null) {
            // No competition opened
            javax.swing.JOptionPane.showMessageDialog(parent, "Ingen tävling öppnad");
            return false;
        }
        // Competition opened
        return true;
    }

    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        if (parent.getCompetition() == null) {
            // No competition opened
            javax.swing.JOptionPane.showMessageDialog(parent, "Ingen tävling öppnad");
            return;
        }

        CompetitionPropertiesVO vo = null;
        Element root = parent.getCompetition().getRootElement();
        Element common = root.getChild("common");

        if (common != null) {
            vo = CompetitionPropertiesDialog.showDialog(CompetitionPropertiesVO.parse(common));
            if (vo != null) {
                common.detach();
                root.addContent(CompetitionPropertiesVO.describe(vo));
                parent.setModified(true);
            }
        }
        else {
            // No earlier properies found
            vo = CompetitionPropertiesDialog.showDialog();
            if (vo != null) {
                root.addContent(CompetitionPropertiesVO.describe(vo));
                parent.setModified(true);
            }
        }
    }
}
