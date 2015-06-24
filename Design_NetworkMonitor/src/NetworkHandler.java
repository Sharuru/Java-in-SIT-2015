import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;


/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler {
    public void startTracking(int index){
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
        //MainForm Mhandler = new MainForm();
        try{
            System.out.println(index);
            NetworkInterface nc = devices[index];
            JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 65535, true, 20);
            startCapThread(jpcap);
            //System.out.println("I am trying...");
           // JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[0], 2000, true, 20);
            //startCapThread(jpcap);
            //jpcap.loopPacket(-1, new PacketReceivedHandler());
            //Mhandler.updateLog("Start tracking on NIC:" + devices[deviceIndex].description);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void startCapThread(final JpcapCaptor jpcap){
        java.lang.Runnable runner = () -> {
            jpcap.loopPacket(-1, new PacketReceivedHandler());
        };
        new  Thread(runner).start();
    }
}
