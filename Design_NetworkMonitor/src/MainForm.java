import jpcap.NetworkInterface;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

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
    private JPanel panelChart;
    private JLabel labelGuide4;

    final static int chartLength = 30;

    public static void main(String[] args) {
        //Auto generated
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panelBase);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        //Custom set
        frame.setTitle("Net Monitor");
        frame.setMinimumSize(new Dimension(725, 450));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        initDeviceList();
        ChartHandler.initSpeedArrayList(chartLength);
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

    protected void updateChart(double newTcpSpeed, double newUdpSpeed) {
        panelChart.removeAll();
        panelChart.revalidate();
        JFreeChart chart = ChartFactory.createXYAreaChart("", "Time(S)", "Speed(KiB/S)", ChartHandler.createDataset(newTcpSpeed, newUdpSpeed), PlotOrientation.VERTICAL, true, false, false);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setSize(panelChart.getWidth(), panelChart.getHeight());
        chartPanel.setVisible(true);
        panelChart.add(chartPanel, BorderLayout.CENTER);
        panelChart.repaint();
    }

    protected boolean isCheckBoxUDPSelected() {
        return checkBoxUDP.isSelected();
    }

    protected boolean isCheckBoxTCPSelected() {
        return checkBoxTCP.isSelected();
    }
}
