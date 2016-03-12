package cma.menu;

import cma.command.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import org.jdom.Element;

public class MenuManager implements ActionListener
{
    private static MenuManager ourInstance;

    public static synchronized MenuManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new MenuManager();
        }
        return ourInstance;
    }

    private JMenuBar menuBar;
    private Map commands;
    private MenuManager() {}

    public JMenuBar createMenuBar(Element menubar) throws Exception
    {
        menuBar = new JMenuBar();
        commands = new HashMap();
        // Take care of menubar element
        List children = menubar.getChildren();
        for (Object aChildren : children) {
            add((Element) aChildren, menuBar);
        }
        return menuBar;
    }

    private void add(Element e, JComponent parent) throws Exception
    {
        if ("menu".equals(e.getName())) {
            Action action = new Action(e.getChild("action"));
            JMenu newMenu = createMenu(e.getAttributeValue("name"), action.enabled);
            parent.add(newMenu);
            // Add menu item to command engine
            commands.put(action.name, new CommandWrapper(newMenu));

            Collection children = e.getChildren();
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                Element tmp = (Element) iterator.next();
                add(tmp, newMenu);
            }
        }
        if ("menuitem".equals(e.getName())) {
            Action action = new Action(e.getChild("action"));

            // Add menu item to menubar
            JMenuItem item =createMenuItem(e.getAttributeValue("name"), action.enabled, action.name);
            parent.add(item);

            // Add menu item to command engine
            Command cmd = (Command) Class.forName(e.getChildTextTrim("class")).newInstance();
            Collection<String> enableItems = new ArrayList<String>();
            Collection<String> disableItems = new ArrayList<String>();
            if(e.getChild("postAction") != null) {
                // Register post actions
                List children = e.getChild("postAction").getChildren();
                for (Object aChild : children) {
                    Action tmp = new Action((Element)aChild);
                    if(tmp.enabled) {
                        enableItems.add(tmp.name);
                    }
                    else {
                        disableItems.add(tmp.name);
                    }
                }
            }
            CommandWrapper commandWrapper = new CommandWrapper(cmd, item, enableItems, disableItems);
            commands.put(e.getChildTextTrim("action"), commandWrapper);
        }
        if ("separator".equals(e.getName())) {
            parent.add(new JSeparator());
        }
    }

    private JMenu createMenu(String text, boolean enabled)
    {
        JMenu item = new JMenu();
        item.setText(text);
        item.setEnabled(enabled);
        return item;
    }

    private JMenuItem createMenuItem(String text, boolean enabled, String actionCmd)
    {
        JMenuItem item = new JMenuItem();
        item.setText(text);
        item.setEnabled(enabled);
        item.setActionCommand(actionCmd);
        item.addActionListener(this);
        return item;
    }

    public void actionPerformed(ActionEvent e)
    {
        CommandWrapper wrapper = (CommandWrapper) commands.get(e.getActionCommand());
        if (wrapper != null) {
            Command cmd = wrapper.cmd;
            if(cmd.preProcess(e)) {
                // All's well! Let's execute
                cmd.execute(e);
                if(cmd.postProcess(e)) {
                    // Execute any post actions to be done ...
                    executePostAction(wrapper);
                }
                else {
                    System.out.println("Warning! postProcess failed!");
                }
            }
            else {
                System.out.println("Warning! preProcess failed!");
            }
        }
        else
            JOptionPane.showMessageDialog(menuBar, "Could not find command for: " + e.getActionCommand());
    }

    public void executePostAction(String actionCommand)
    {
        CommandWrapper wrapper = (CommandWrapper) commands.get(actionCommand);
        if (wrapper != null) {
            executePostAction(wrapper);
        }
        else
            JOptionPane.showMessageDialog(menuBar, "Could not find command for: " + actionCommand);
    }

    private void executePostAction(CommandWrapper wrapper)
    {
        if(wrapper.enableItems.size() > 0) {
            // Enable menuitems
            setMenuItemStatus(wrapper.enableItems, true);
        }
        if(wrapper.disableItems.size() > 0) {
            // Disable menuitems
            setMenuItemStatus(wrapper.disableItems, false);
        }
    }

    public void setMenuItemStatus(Collection<String> menuItems, boolean enable)
    {
        for (String actionCommand : menuItems) {
            setMenuItemStatus(actionCommand, enable);
        }
    }

    public void setMenuItemStatus(String actionCommand, boolean enable)
    {
        CommandWrapper commandWrapper = ((CommandWrapper) commands.get(actionCommand));
        if(commandWrapper != null) {
            commandWrapper.menuCmd.setEnabled(enable);
        }
        else {
            System.out.println("Could not find command for: " + actionCommand);
        }
    }

    private class Action
    {
        String name;
        boolean enabled;

        public Action(Element action)
        {
            enabled = true;
            if(action.getAttributeValue("enabled") != null) {
                enabled = "true".equals(action.getAttributeValue("enabled"));
            }
            this.name = action.getTextTrim();
        }
    }

    private class CommandWrapper
    {
        Command cmd;
        JMenuItem menuCmd;
        Collection<String> enableItems;
        Collection<String> disableItems;

        public CommandWrapper(JMenuItem menuCmd)
        {
            this.cmd = null;
            this.menuCmd = menuCmd;
            enableItems = new ArrayList<String>();
            disableItems = new ArrayList<String>();
        }

        public CommandWrapper(Command cmd, JMenuItem menuCmd)
        {
            this.cmd = cmd;
            this.menuCmd = menuCmd;
            enableItems = new ArrayList<String>();
            disableItems = new ArrayList<String>();
        }

        public CommandWrapper(Command cmd, JMenuItem menuCmd, Collection<String> enableItems, Collection<String> disableItems) {
            this.cmd = cmd;
            this.menuCmd = menuCmd;
            this.enableItems = enableItems;
            this.disableItems = disableItems;
        }
    }
}
