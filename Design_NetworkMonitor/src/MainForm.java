import javafx.scene.input.DataFormat;
import jpcap.NetworkInterface;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        frame.setMinimumSize(new Dimension(1024, 245));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        initDeviceList();
        comboBoxNICs.addActionListener(e -> selectedNICUpdate());
    }

    private void initDeviceList(){
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
        for(int i =0;i<devices.length;i++){
            comboBoxNICs.addItem(devices[i].description);
        }
        selectedNICUpdate();
    }

    private String getTime(){
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        return time.format(nowTime);
    }

    private void selectedNICUpdate(){
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        textAreaLogs.append(getTime() + " - Current selected NIC is: " + comboBoxNICs.getSelectedItem() + "\n");
    }

    public void updateLog(String log){
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        textAreaLogs.append(getTime() + " - " + log + "\n");
    }
}


