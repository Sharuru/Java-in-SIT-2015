import jpcap.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

    public MainForm() {
        initDeviceList();
        comboBoxNICs.addActionListener(e -> selectedNICUpdate());
    }

    private void initDeviceList(){
        Models handler = new Models();
        NetworkInterface[] devices = handler.listDevices();
        for(int i =0;i<devices.length;i++){
            comboBoxNICs.addItem(devices[i].description);
        }
        selectedNICUpdate();
    }

    private void selectedNICUpdate(){
        textAreaLogs.append("Current selected NIC is: " + comboBoxNICs.getSelectedItem() + "\n");
    }
}


