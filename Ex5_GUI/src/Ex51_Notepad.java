import java.awt.*;
import javax.swing.*;

/**
 * Created by Mave on 2015/4/17 0010.
 * Extra question: Design a notepad with basic function like open, save, paste, cut, redo, undo and so on.
 * Java Form created by JFormDesigner on Fri Apr 17 15:21:02 CST 2015
 */


public class Ex51_Notepad {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ex51_Notepad");
        frame.setContentPane(new Ex51_Notepad().thisPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(495, 360);
        frame.setLocationRelativeTo(null);

    }

    public Ex51_Notepad() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        thisPanel = new JPanel();
        menuBarNavi = new JMenuBar();
        menuOpen = new JMenu();
        menuItemNew = new JMenuItem();
        menuItemOpen = new JMenuItem();
        menuItemSave = new JMenuItem();
        menuItemExit = new JMenuItem();
        menuEdit = new JMenu();
        menuItemUndo = new JMenuItem();
        menuItemRedo = new JMenuItem();
        menuItemCut = new JMenuItem();
        menuItemCopy = new JMenuItem();
        menuItemPaste = new JMenuItem();
        menuAbout = new JMenu();
        menuItemAbout = new JMenuItem();
        textAreaMain = new JTextArea();

        //======== thisPanel ========
        {
            thisPanel.setLayout(new BorderLayout());

            //======== menuBarNavi ========
            {

                //======== menuOpen ========
                {
                    menuOpen.setText("File");

                    //---- menuItemNew ----
                    menuItemNew.setText("New");
                    menuOpen.add(menuItemNew);

                    //---- menuItemOpen ----
                    menuItemOpen.setText("Open");
                    menuOpen.add(menuItemOpen);

                    //---- menuItemSave ----
                    menuItemSave.setText("Save");
                    menuOpen.add(menuItemSave);
                    menuOpen.addSeparator();

                    //---- menuItemExit ----
                    menuItemExit.setText("Exit");
                    menuOpen.add(menuItemExit);
                }
                menuBarNavi.add(menuOpen);

                //======== menuEdit ========
                {
                    menuEdit.setText("Edit");

                    //---- menuItemUndo ----
                    menuItemUndo.setText("Undo");
                    menuEdit.add(menuItemUndo);

                    //---- menuItemRedo ----
                    menuItemRedo.setText("Redo");
                    menuEdit.add(menuItemRedo);
                    menuEdit.addSeparator();

                    //---- menuItemCut ----
                    menuItemCut.setText("Cut");
                    menuEdit.add(menuItemCut);

                    //---- menuItemCopy ----
                    menuItemCopy.setText("Copy");
                    menuEdit.add(menuItemCopy);

                    //---- menuItemPaste ----
                    menuItemPaste.setText("Paste");
                    menuEdit.add(menuItemPaste);
                }
                menuBarNavi.add(menuEdit);

                //======== menuAbout ========
                {
                    menuAbout.setText("About");

                    //---- menuItemAbout ----
                    menuItemAbout.setText("About this software");
                    menuAbout.add(menuItemAbout);
                }
                menuBarNavi.add(menuAbout);
            }
            thisPanel.add(menuBarNavi, BorderLayout.NORTH);
            thisPanel.add(textAreaMain, BorderLayout.CENTER);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel thisPanel;
    private JMenuBar menuBarNavi;
    private JMenu menuOpen;
    private JMenuItem menuItemNew;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemExit;
    private JMenu menuEdit;
    private JMenuItem menuItemUndo;
    private JMenuItem menuItemRedo;
    private JMenuItem menuItemCut;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenu menuAbout;
    private JMenuItem menuItemAbout;
    private JTextArea textAreaMain;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
