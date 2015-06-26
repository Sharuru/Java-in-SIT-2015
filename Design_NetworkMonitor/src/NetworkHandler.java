import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler {
    protected Thread captureThread;
    protected JpcapCaptor cap;
    protected MainForm frame;
    //Set counter
    double tS = 0;
    int tCount = 0;
    double uS = 0;
    int uCount = 0;
    //Set timer
    ScheduledExecutorService updateExec = newSingleThreadScheduledExecutor();

    protected void setMainForm(MainForm frame) {
        this.frame = frame;
    }

    protected PacketReceiver handler = new PacketReceiver() {
        public void receivePacket(Packet packet) {
            if (packet instanceof jpcap.packet.TCPPacket && frame.isCheckBoxTCPSelected()) {
                TCPPacket tp = (TCPPacket) packet;
                frame.dealPacket(String.format("[Get TCP] From:%s:%d -> To: %s:%d. Length = %d",
                        tp.src_ip.toString().substring(1, tp.src_ip.toString().length()), tp.src_port, tp.dst_ip.toString().substring(1, tp.dst_ip.toString().length()), tp.dst_port, tp.len));
                tS = tS + tp.len;
                tCount++;
            } else if (packet instanceof jpcap.packet.UDPPacket && frame.isCheckBoxUDPSelected()) {
                UDPPacket up = (UDPPacket) packet;
                frame.dealPacket(String.format("[Get UDP] From:%s:%d -> To: %s:%d. Length = %d",
                        up.src_ip.toString().substring(1, up.src_ip.toString().length()), up.src_port, up.dst_ip.toString().substring(1, up.dst_ip.toString().length()), up.dst_port, up.len));
                uS = uS + up.len;
                uCount++;
            }
        }
    };

    protected void capturePacketFromDevice(int deviceIndex) {
        if (cap != null) {
            cap.close();
        }
        DevicesHandler Dhandler = new DevicesHandler();
        NetworkInterface[] devices = Dhandler.listDevices();
        NetworkInterface ni = devices[deviceIndex];
        try {
            cap = JpcapCaptor.openDevice(ni, 65535, true, 20);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (cap != null) {
            startCaptureThread();
        }
    }

    protected void startCaptureThread() {
        if (captureThread != null) {
            return;
        }
        captureThread = new Thread(() -> {
            while (captureThread != null) {
                cap.processPacket(1, handler);
            }
        });
        captureThread.setPriority(Thread.MIN_PRIORITY);
        updateExec.scheduleAtFixedRate(() -> {
            //Ts = TrueSpeed(Calculated)
            double tcpTs = tS / 1000;
            double udpTs = uS / 1000;
            frame.updateLinkInfo(1, tcpTs, tCount);
            frame.updateLinkInfo(2, udpTs, uCount);
            frame.updateChart(tcpTs, udpTs);
            //Reset speed
            tS = 0;
            uS = 0;
        }, 0, 1, TimeUnit.SECONDS);
        captureThread.start();
    }

    protected void stopCaptureThread() {
        captureThread = null;
        tCount = 0;
        uCount = 0;
    }

    protected void stopCapture() {
        stopCaptureThread();
    }
}
