import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

/**
 * Created by Mave on 2015/4/17 0010.
 * Extra question: Design a notepad with basic function like open, save, paste, cut, redo, undo and so on.
 * Java Form created by JFormDesigner on Fri Apr 17 15:21:02 CST 2015
 */


public class Ex51_Notepad {

    private UndoableEdit edit;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ex51_Notepad");
        frame.setContentPane(new Ex51_Notepad().thisPanel);
        frame.setTitle("MyNotepad");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icons\\notepad.gif"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(495, 360);
        frame.setLocationRelativeTo(null);

    }

    public Ex51_Notepad() {
        initComponents();
    }

    private void menuItemNewActionPerformed(ActionEvent e) {
        textAreaMain.setText("");
    }

    private void menuItemExitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void menuItemOpenActionPerformed(ActionEvent e) {
        String text = getFileText();
        if (text.length() != 0) {
            textAreaMain.setText(text);
        }
    }

    private void menuItemSaveActionPerformed(ActionEvent e) {
        saveFileText(textAreaMain.getText());
    }

    private void menuItemUndoActionPerformed(ActionEvent e) {
        if(edit.canUndo()){
            edit.undo();
        }
    }

    private void menuItemRedoActionPerformed(ActionEvent e) {
        if(edit.canRedo()){
            edit.redo();
        }
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
        scrollPane1 = new JScrollPane();
        textAreaMain = new JTextArea();
        menuBar1 = new JMenuBar();
        buttonSave = new JButton();
        buttonUndo = new JButton();
        buttonRedo = new JButton();

        //======== thisPanel ========
        {
            thisPanel.setLayout(new BorderLayout());

            //======== menuBarNavi ========
            {

                //======== menuOpen ========
                {
                    menuOpen.setText("File");
                    menuOpen.setMnemonic('F');

                    //---- menuItemNew ----
                    menuItemNew.setText("New");
                    menuItemNew.addActionListener(e -> menuItemNewActionPerformed(e));
                    menuOpen.add(menuItemNew);

                    //---- menuItemOpen ----
                    menuItemOpen.setText("Open");
                    menuItemOpen.addActionListener(e -> menuItemOpenActionPerformed(e));
                    menuOpen.add(menuItemOpen);

                    //---- menuItemSave ----
                    menuItemSave.setText("Save");
                    menuItemSave.addActionListener(e -> menuItemSaveActionPerformed(e));
                    menuOpen.add(menuItemSave);
                    menuOpen.addSeparator();

                    //---- menuItemExit ----
                    menuItemExit.setText("Exit");
                    menuItemExit.addActionListener(e -> menuItemExitActionPerformed(e));
                    menuOpen.add(menuItemExit);
                }
                menuBarNavi.add(menuOpen);

                //======== menuEdit ========
                {
                    menuEdit.setText("Edit");
                    menuEdit.setMnemonic('E');

                    //---- menuItemUndo ----
                    menuItemUndo.setText("Undo");
                    menuItemUndo.addActionListener(e -> menuItemUndoActionPerformed(e));
                    menuEdit.add(menuItemUndo);

                    //---- menuItemRedo ----
                    menuItemRedo.setText("Redo");
                    menuItemRedo.addActionListener(e -> menuItemRedoActionPerformed(e));
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
                    menuAbout.setMnemonic('A');

                    //---- menuItemAbout ----
                    menuItemAbout.setText("About this software");
                    menuAbout.add(menuItemAbout);
                }
                menuBarNavi.add(menuAbout);
            }
            thisPanel.add(menuBarNavi, BorderLayout.NORTH);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(textAreaMain);
            }
            thisPanel.add(scrollPane1, BorderLayout.CENTER);

            //======== menuBar1 ========
            {

                //---- buttonSave ----
                buttonSave.setText("Save");
                buttonSave.addActionListener(e -> menuItemSaveActionPerformed(e));
                menuBar1.add(buttonSave);

                //---- buttonUndo ----
                buttonUndo.setText("Undo");
                buttonUndo.addActionListener(e -> menuItemUndoActionPerformed(e));
                menuBar1.add(buttonUndo);

                //---- buttonRedo ----
                buttonRedo.setText("Redo");
                buttonRedo.addActionListener(e -> menuItemRedoActionPerformed(e));
                menuBar1.add(buttonRedo);
            }
            thisPanel.add(menuBar1, BorderLayout.SOUTH);
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
    private JScrollPane scrollPane1;
    private JTextArea textAreaMain;
    private JMenuBar menuBar1;
    private JButton buttonSave;
    private JButton buttonUndo;
    private JButton buttonRedo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //Other methods
    private static String getFileText() {
        String fileText = "";
        StringBuffer stringBuffer = new StringBuffer();
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            FileReader fileReader;
            try {
                fileReader = new FileReader(fileChooser.getSelectedFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((fileText = bufferedReader.readLine()) != null) {
                    stringBuffer.append(fileText + "\r\n");
                }
                bufferedReader.close();
                fileText = stringBuffer.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileText;
    }

    private static String saveFileText(String fileText){
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showSaveDialog(fileChooser)==JFileChooser.APPROVE_OPTION){
            FileWriter fileWriter;
            try{
                fileWriter = new FileWriter(fileChooser.getSelectedFile());
                BufferedWriter bufferedWritter = new BufferedWriter(fileWriter);
                bufferedWritter.write(fileText);
                bufferedWritter.flush();
                bufferedWritter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileText;
    }
}
