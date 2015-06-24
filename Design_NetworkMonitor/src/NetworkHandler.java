import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler {
    public void startTracking(int index) {
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
        //MainForm Mhandler = new MainForm();
        try {
            System.out.println(index);
            NetworkInterface nc = devices[index];
            JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 65535, true, 20);
            startCapThread(jpcap);
            //Mhandler.updateLog("Start tracking on NIC:" + devices[deviceIndex].description);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void startCapThread(final JpcapCaptor captor) {
        java.lang.Runnable runner = () -> captor.loopPacket(-1, new PacketReceivedHandler());
        new Thread(runner).start();
    }
}
