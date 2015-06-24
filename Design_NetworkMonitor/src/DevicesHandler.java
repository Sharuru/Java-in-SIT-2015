import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class DevicesHandler {
    public NetworkInterface[] listDevices() {
        try {
            final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            return devices;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new NetworkInterface[0];
    }
}
