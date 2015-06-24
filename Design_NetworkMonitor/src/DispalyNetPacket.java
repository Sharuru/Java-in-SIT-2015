import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.*;
/**
 * ʹ��jpcap��ʾ�����ϵĸ������ݰ�
 * @author www.NetJava.cn
 */
public class DispalyNetPacket {

    //��������������
    public static void main(String args[]){
        try{
            //��ȡ�����ϵ�����ӿڶ�������
            final	NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            for(int i=0;i<devices.length;i++){
                NetworkInterface nc=devices[i];
                //����ĳ�������ϵ�ץȡ����,���Ϊ2000��
                JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);
                NetworkHandler abc = new NetworkHandler();
                abc.startCapThread(jpcap);
                //startCapThread(jpcap);
                System.out.println("��ʼץȡ��"+i+"�������ϵ�����");
            }
        }catch(Exception ef){
            ef.printStackTrace();
            System.out.println("����ʧ��:  "+ef);
        }

    }
    //��ÿ��Captor�ŵ������߳�������
    public static void startCapThread(final JpcapCaptor jpcap ){
        JpcapCaptor jp=jpcap;
        java.lang.Runnable rnner=new Runnable(){
            public void run(){
                //ʹ�ýӰ�������ѭ��ץ��
                jpcap.loopPacket(-1, new PacketReceivedHandler());
            }
        };
        new Thread(rnner).start();//����ץ���߳�
    }
}

/**
 * ץ��������,ʵ��PacketReceiver�еķ���:��ӡ�����ݰ�˵��
 * @author www.NetJava.cn
 */
class TestPacketReceiver  implements PacketReceiver {
    /**
     * ʵ�ֵĽӰ�����:
     */
    public void receivePacket(Packet packet) {
        //Tcp��,��java Socket��ֻ�ܵõ���������
        if(packet instanceof jpcap.packet.TCPPacket){
            TCPPacket p=(TCPPacket)packet;
            String s="TCPPacket:| dst_ip "+p.dst_ip+":"+p.dst_port
                    +"|src_ip "+p.src_ip+":"+p.src_port
                    +" |len: "+p.len;
            System.out.println(s);
        }
        //UDP��,����QQ,��ͻῴ��:����tcp+udp
        else if(packet instanceof jpcap.packet.UDPPacket){
            UDPPacket p=(UDPPacket)packet;
            String s="UDPPacket:| dst_ip "+p.dst_ip+":"+p.dst_port
                    +"|src_ip "+p.src_ip+":"+p.src_port
                    +" |len: "+p.len;
            System.out.println(s);
        }
        //�����Ҫ�ڳ����й���һ��ping����,��Ҫ����ICMPPacket��
        else if(packet instanceof jpcap.packet.ICMPPacket){
            ICMPPacket p=(ICMPPacket)packet;
            //ICMP����·����
            String router_ip="";
            for(int i=0;i<p.router_ip.length;i++){
                router_ip+=" "+p.router_ip[i].getHostAddress();
            }
            String s="@ @ @ ICMPPacket:| router_ip "+router_ip
                    +" |redir_ip: "+p.redir_ip
                    +" |mtu: "+p.mtu
                    +" |length: "+p.len;
            System.out.println(s);
        }
        //�Ƿ��ַת��Э�������
        else if(packet instanceof jpcap.packet.ARPPacket){
            ARPPacket p=(ARPPacket)packet;
            //Returns the hardware address (MAC address) of the sender
            Object  saa=   p.getSenderHardwareAddress();
            Object  taa=p.getTargetHardwareAddress();
            String s="* * * ARPPacket:| SenderHardwareAddress "+saa
                    +"|TargetHardwareAddress "+taa
                    +" |len: "+p.len;
            System.out.println(s);

        }
        //ȡ����·������ͷ :����������ץ����α�����ݰ����ٺ�
        DatalinkPacket datalink  =packet.datalink;
        //�������̫����
        if(datalink instanceof jpcap.packet.EthernetPacket){
            EthernetPacket ep=(EthernetPacket)datalink;
            String s="  datalink layer packet: "
                    +"|DestinationAddress: "+ep.getDestinationAddress()
                    +"|SourceAddress: "+ep.getSourceAddress();
            System.out.println(s);
        }
    }

} 
