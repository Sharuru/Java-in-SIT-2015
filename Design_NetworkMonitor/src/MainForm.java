import javax.swing.*;
import java.awt.*;

/**
 * Created by Sharuru on 2015/6/23 0023.
 */
public class MainForm {
    private JComboBox comboBoxNICs;
    private JButton buttonAction;
    private JLabel labelGuide1;
    private JPanel panelBase;
    private JLabel labelGuide2;
    private JLabel labelGuide3;
    private JLabel labelUploadSpeed;
    private JLabel labelDownloadSpeed;
    private JScrollPane scrollPanelLogs;
    private JTextArea textAreaLogs;

    public MainForm() {
    }

    public static void main(String[] args) {
        //Auto generated
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panelBase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //Custom set
        frame.setTitle("Net Monitor");
        frame.setMinimumSize(new Dimension(545, 245));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


