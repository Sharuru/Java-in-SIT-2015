import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler{
    public void startTracking(int index) {
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
       // MainForm Mhandler = new MainForm();
        //Mhandler.updateLog("asdd");
        JOptionPane.showMessageDialog(null, "This is a simple notepad programmed by Java.");
        try {
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

    public static void stopCapThread(){
        //java.lang.Runnable runner = () -> captor.loopPacket(-1, new PacketReceivedHandler());
        //Thread(runner);

    }
}
