import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WinSocketUDP2Ex extends JFrame {

    private JPanel contentPane;
    DatagramSocket local;
    DatagramPacket rdp;
    DatagramPacket sdp;

    JTextArea textArea, textArea_1;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WinSocketUDP2Ex frame = new WinSocketUDP2Ex();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class recvFun2 implements Runnable {

        @Override
        public void run() {
            //涉及SWING的需要用
            //EventQueue.invokeLater(new Runnable() {
            //但.setText是特例。
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket rdp = new DatagramPacket(buffer,
                        buffer.length,
                        null,
                        0);
                while (true) {
                    local.receive(rdp);
                    String str = textArea.getText() + "\r\n";
                    str = str + "Remote:" + new String(rdp.getData(), 0, rdp.getLength());
                    textArea.setText(str);
                }
            } catch (SocketException | UnknownHostException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }

        }

    }

    /**
     * Create the frame.
     */
    public WinSocketUDP2Ex() {
        setTitle("End2");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    local = new DatagramSocket(8082, InetAddress.getByName("localhost"));

                    Thread recvTh2 = new Thread(new recvFun2());
                    recvTh2.start();


                } catch (SocketException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (UnknownHostException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                local.close();
                super.windowClosed(e);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 244, 365);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        textArea = new JTextArea();
        textArea.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5BF9\u8BDD\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        textArea.setPreferredSize(new Dimension(4, 200));
        contentPane.add(textArea, BorderLayout.NORTH);

        textArea_1 = new JTextArea();
        textArea_1.setBorder(new TitledBorder(null, "\u8F93\u5165\u6D88\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(textArea_1, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton btnNewButton = new JButton("Send");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str1 = textArea_1.getText();
                String str = textArea.getText() + "\r\n";
                str = str + "Local:" + str1;
                textArea.setText(str);
                textArea_1.setText("");

                byte[] sendb = str1.getBytes();
                try {
                    sdp = new DatagramPacket(sendb,
                            sendb.length,
                            InetAddress.getByName("localhost"),
                            8081);
                    local.send(sdp);
                } catch (UnknownHostException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }


            }
        });
        panel.add(btnNewButton);
    }

}
