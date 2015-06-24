import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler implements Runnable{
    static public boolean sniffing = true;
    static public JpcapCaptor jappp = null;
    public Thread sniffingThread = new Thread(this);

    public void stop() {
        System.out.println("Stopping.....");
        sniffing = false;
        jappp.breakLoop();
    }

    public void run() {
        while (sniffing) {
            try {
                jappp.loopPacket(-1, new PacketReceivedHandler());
            } catch (Exception ex) {
                System.out.println("Restart");
            }
        }
    }

    public void stopT(){
        System.out.println("Stopping thread.");
        sniffingThread.stop();
    }
    public void trackingControl(int index, boolean flag) {
        System.out.println("I got flag is:" + flag);
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
        //MainForm Mhandler = new MainForm();
        //Mhandler.updateLog("asdd");
        //JOptionPane.showMessageDialog(null, "This is a simple notepad programmed by Java.");
        try {
            NetworkInterface nc = devices[index];
            JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 65535, true, 20);
            jappp = jpcap;
            sniffingThread.start();
            //capThreadControl(jpcap, flag);
            //Mhandler.updateLog("Start tracking on NIC:" + devices[deviceIndex].description);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

/*    public static void capThreadControl(final JpcapCaptor captor, boolean flag) {
        java.lang.Runnable runner = () -> captor.loopPacket(-1, new PacketReceivedHandler());
        if (!flag) {
            System.out.println("Try break");
            runner = () -> captor.breakLoop();
        }
        new Thread(runner).start();
    }

    public class ThreadControl implements Runnable {
        public void stop() {
            System.out.println("Stopping.....");
            sniffing = false;
            jappp.breakLoop();
        }

        public void run() {
            while (sniffing) {
                try {
                    jappp.loopPacket(-1, new PacketReceivedHandler());
                } catch (Exception ex) {
                    System.out.println("Restart");
                }
            }
        }
    }*/
}



