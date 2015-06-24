import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class DevicesHandler {
    public NetworkInterface[] listDevices() {
        try {
            final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            return devices;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No NIC found on your computer :(");
            ex.printStackTrace();
        }
        return new NetworkInterface[0];
    }
}
