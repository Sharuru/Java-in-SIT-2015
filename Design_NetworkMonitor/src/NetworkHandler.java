import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import java.io.IOException;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class NetworkHandler {
    JpcapCaptor cap = null;
    MainForm frame;

    protected void setMainForm(MainForm frame) {
        this.frame = frame;
    }

    protected void capturePacketFromDevice(int deviceIndex) {
        if (cap != null){
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

    protected Thread captureThread;

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
        captureThread.start();
    }

    protected void stopCaptureThread() {
        captureThread = null;
    }

    protected void stopCapture() {
        stopCaptureThread();
    }

    protected PacketReceiver handler = new PacketReceiver() {
        public void receivePacket(Packet packet) {
            if (packet instanceof jpcap.packet.TCPPacket) {
                TCPPacket tp = (TCPPacket) packet;
                frame.dealPacket("[Get TCP] From:" + tp.src_ip.toString().substring(1, tp.src_ip.toString().length() - 1) + ":" + tp.src_port
                        + " -> To: " + tp.dst_ip.toString().substring(1, tp.dst_ip.toString().length() - 1) + ":" + tp.dst_port + ". Length = " + tp.len);
            } else if (packet instanceof jpcap.packet.UDPPacket) {
                UDPPacket up = (UDPPacket) packet;
                frame.dealPacket("[Get UDP] From:" + up.src_ip.toString().substring(1, up.src_ip.toString().length() - 1) + ":" + up.src_port
                        + " -> To: " + up.dst_ip.toString().substring(1, up.dst_ip.toString().length() - 1) + ":" + up.dst_port + ". Length = " + up.len);
            }
        }
    };
}
