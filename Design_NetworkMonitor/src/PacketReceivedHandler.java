import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * Created by Sharuru on 2015/6/24 0024.
 */
public class PacketReceivedHandler implements PacketReceiver{

    public void receivePacket(Packet packet) {
        //MainForm Mhandler = new MainForm();
        if(packet instanceof jpcap.packet.TCPPacket){
            TCPPacket tp = (TCPPacket)packet;
            //Mhandler.updateLog("[Get TCP] From:" + tp.src_ip + ":" + tp.src_port + " To: " + tp.dst_ip + ":" + tp.dst_port + ". Length = " + tp.len);
            System.out.println("tcp");
        }
        else if(packet instanceof jpcap.packet.UDPPacket){
            UDPPacket tp = (UDPPacket)packet;
            //Mhandler.updateLog("[Get UDP] From:" + tp.src_ip + ":" + tp.src_port + " To: " + tp.dst_ip + ":" + tp.dst_port + ". Length = " + tp.len);
            System.out.println("udp");
        }
    }
}
