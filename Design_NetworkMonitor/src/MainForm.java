import jpcap.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static java.lang.String.format;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by Sharuru on 2015/6/23 0023.
 */
public class MainForm {
    private JComboBox<String> comboBoxNICs;
    private JButton buttonAction;
    private JLabel labelGuide1;
    private JPanel panelBase;
    private JLabel labelGuide2;
    private JLabel labelGuide3;
    private JLabel labelTCPStatus;
    private JLabel labelUDPStatus;
    private JScrollPane scrollPanelLogs;
    private JTextArea textAreaLogs;
    private JCheckBox checkBoxTCP;
    private JCheckBox checkBoxUDP;

    public static void main(String[] args) {
        //Auto generated
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panelBase);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        //Custom set
        frame.setTitle("Net Monitor");
        frame.setMinimumSize(new Dimension(650, 445));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon\\icon.png"));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        initDeviceList();
        NetworkHandler captor = new NetworkHandler();
        comboBoxNICs.addActionListener(e -> selectedNICUpdate());
        buttonAction.addActionListener(e -> {
            if (Objects.equals(buttonAction.getText(), "Start tracking")) {
                updateLog("Starting tracking thread...");
                captor.capturePacketFromDevice(comboBoxNICs.getSelectedIndex());
                captor.setMainForm(this);
                buttonAction.setText("Stop tracking");
            } else if (Objects.equals(buttonAction.getText(), "Stop tracking")) {
                updateLog("Stopping tracking thread...");
                buttonAction.setText("Start tracking");
                captor.stopCapture();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
        textAreaLogs.append(format("%s - Current selected NIC is: %s\n", getTime(), comboBoxNICs.getSelectedItem()));
    }

    protected void updateLog(String log) {
        textAreaLogs.append(getTime() + " - " + log + "\n");
        textAreaLogs.setCaretPosition(textAreaLogs.getText().length());
    }

    protected void dealPacket(String s) {
        updateLog(s);
    }

    protected void updateLinkInfo(int protocol, double speed, int packetCount) {
        DecimalFormat df = new DecimalFormat("0.00");
        switch (protocol) {
            case 1: {
                labelTCPStatus.setText(format("TCP: %s KiB/S %d packets total.", String.valueOf(df.format(speed)), packetCount));
            }
            break;
            case 2: {
                labelUDPStatus.setText(format("UDP: %s KiB/S %d packets total.", String.valueOf(df.format(speed)), packetCount));
            }
            break;
        }
    }

    protected boolean isCheckBoxUDPSelected() {
        return checkBoxUDP.isSelected();
    }

    protected boolean isCheckBoxTCPSelected() {
        return checkBoxTCP.isSelected();
    }
}
