import jpcap.NetworkInterface;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/*    static ArrayList tcpA = new ArrayList();
    static ArrayList udpA = new ArrayList();*/

    public static void main(String[] args) {
        //Auto generated
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panelBase);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        //Custom set
        frame.setTitle("Net Monitor");
        frame.setMinimumSize(new Dimension(650, 455));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        initDeviceList();
        ChartHandler.initSpeedArrayList(10);
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

    public void updateG(double newTcpSpeed, double newUdpSpeed) {
        System.out.println("I am called");
        panelChart.removeAll();
        panelChart.revalidate();
        JFreeChart chart = ChartFactory.createXYAreaChart("Traffic history", "Time", "Speed", ChartHandler.createDataset(newTcpSpeed, newUdpSpeed), PlotOrientation.VERTICAL, true, true, false);
        System.out.println("From 2  back");
        ChartPanel localChartPanel = new ChartPanel(chart, false);
        localChartPanel.setSize(panelChart.getWidth(), panelChart.getHeight());
        localChartPanel.setVisible(true);
        panelChart.add(localChartPanel, BorderLayout.CENTER);
        panelChart.repaint();
        System.out.println("Rd");
    }

/*    private XYDataset createDataset(double newTcpSpeed, double newUdpSpeed) {
        System.out.println("I am called 2");
        XYSeries tcpDS = new XYSeries("TCP");
        XYSeries udpDS = new XYSeries("UDP");
        tcpA.set(4,newTcpSpeed);
        udpA.set(4,newUdpSpeed);
        for (int i = 0; i < 5; i++) {
            tcpDS.add(i, (Double) tcpA.get(i));
            udpDS.add(i, (Double) udpA.get(i));
        }
        for (int i = 0; i < 4;i++){
            tcpA.set(i,tcpA.get(i+1));
            udpA.set(i,udpA.get(i+1));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(tcpDS);
        dataset.addSeries(udpDS);
        return dataset;
    }*/

    protected boolean isCheckBoxUDPSelected() {
        return checkBoxUDP.isSelected();
    }

    protected boolean isCheckBoxTCPSelected() {
        return checkBoxTCP.isSelected();
    }
}
