import jpcap.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private JLabel labelTCPSpeed;
    private JLabel labelUDPSpeed;
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
        frame.setMinimumSize(new Dimension(650, 445));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        initDeviceList();
        NetworkHandler captor = new NetworkHandler();
        comboBoxNICs.addActionListener(e -> selectedNICUpdate());
        buttonAction.addActionListener(e -> {
            if (buttonAction.getText() == "Start tracking") {
                updateLog("Starting tracking thread...");
                captor.capturePacketFromDevice(comboBoxNICs.getSelectedIndex());
                captor.setMainForm(this);
                buttonAction.setText("Stop tracking");
            } else if (buttonAction.getText() == "Stop tracking") {
                updateLog("Stopping tracking thread...");
                buttonAction.setText("Start tracking");
                captor.stopCapture();
                updateLog("Tracking stopped.");
            }
        });
    }

    protected void initDeviceList() {
        DevicesHandler deviceshandler = new DevicesHandler();
        NetworkInterface[] devices = deviceshandler.listDevices();
        for (int i = 0; i < devices.length; i++) {
            comboBoxNICs.addItem(i + 1 + ". " + devices[i].description);
        }
        selectedNICUpdate();
    }

    protected String getTime() {
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        return time.format(nowTime);
    }

    protected void selectedNICUpdate() {
        textAreaLogs.append(getTime() + " - Current selected NIC is: " + comboBoxNICs.getSelectedItem() + "\n");
    }

    protected void updateLog(String log) {
        textAreaLogs.append(getTime() + " - " + log + "\n");
        textAreaLogs.setCaretPosition(textAreaLogs.getText().length());
    }

    protected void dealPacket(String s) {
        updateLog(s);
    }

    protected void updateTSpeed(String s) {
        labelTCPSpeed.setText("TCP speed: " + s + " KB/S");
    }

    protected void updateUSpeed(String s) {
        labelUDPSpeed.setText("UDP speed: " + s + " KB/S");
    }
}
