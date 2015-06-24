import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import java.io.IOException;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler {
    public void startTracking(int deviceIndex){
        DevicesHandler Dhandler = new DevicesHandler();
        MainForm Mhandler = new MainForm();
        NetworkInterface[] devices = Dhandler.listDevices();
        try{
            JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[deviceIndex], 65535, true, 20);
            startCapThread(jpcap);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void startCapThread(final JpcapCaptor jpcap){
        JpcapCaptor jp = jpcap;
        java.lang.Runnable rnner = new Runnable() {
            @Override
            public void run() {
                jpcap.loopPacket(-1, new PacketReceivedHandler());
            }
        };
        new  Thread(rnner).start();
    }
}
