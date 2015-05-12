import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Server {

    private JFrame frame;
    private JTextArea contentArea;
    private JTextField txt_message;
    private JTextField txt_max;
    private JTextField txt_port;
    private JButton btn_start;
    private JButton btn_stop;
    private JButton btn_send;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane rightPanel;
    private JScrollPane leftPanel;
    private JSplitPane centerSplit;
    private JList<String> userList;
    private DefaultListModel<String> listModel;

    private ServerSocket serverSocket;
    private Thread serverThread;
    private ArrayList<ClientThread> clients;
    private ArrayList<User> Users = new ArrayList<User>();

    private boolean isStart = false;

    // ������,����ִ�����  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ex74_TCPConnServer();
            }
        });
    }

    // ִ����Ϣ����  
    public void send() {
        if (!isStart) {
            JOptionPane.showMessageDialog(frame, "��������δ����,���ܷ�����Ϣ��", "����",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Users.size() == 0) {
            JOptionPane.showMessageDialog(frame, "û���û�����,���ܷ�����Ϣ��", "����",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = txt_message.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "��Ϣ����Ϊ�գ�", "����",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        sendServerMessage(message);// Ⱥ����������Ϣ  
        contentArea.append("������˵��" + txt_message.getText() + "\r\n");
        txt_message.setText(null);
    }

    // ����ŷ�  
    public Ex74_TCPConnServer() {
        frame = new JFrame("������");
        // ����JFrame��ͼ�꣺  
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setForeground(Color.blue);
        txt_message = new JTextField();
        txt_max = new JTextField("30");
        txt_port = new JTextField("6666");
        btn_start = new JButton("����");
        btn_stop = new JButton("ֹͣ");
        btn_send = new JButton("����");
        btn_stop.setEnabled(false);
        listModel = new DefaultListModel<String>();
        userList = new JList<String>(listModel);

        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new TitledBorder("д��Ϣ"));
        southPanel.add(txt_message, "Center");
        southPanel.add(btn_send, "East");
        leftPanel = new JScrollPane(userList);
        leftPanel.setBorder(new TitledBorder("�����û�"));

        rightPanel = new JScrollPane(contentArea);
        rightPanel.setBorder(new TitledBorder("��Ϣ��ʾ��"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
                rightPanel);
        centerSplit.setDividerLocation(100);
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 6));
        northPanel.add(new JLabel("��������"));
        northPanel.add(txt_max);
        northPanel.add(new JLabel("�˿�"));
        northPanel.add(txt_port);
        northPanel.add(btn_start);
        northPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("������Ϣ"));

        frame.setLayout(new BorderLayout());
        frame.add(northPanel, "North");
        frame.add(centerSplit, "Center");
        frame.add(southPanel, "South");
        frame.setSize(600, 400);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2,
                (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        // �رմ���ʱ�¼�  
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isStart) {
                    closeServer();// �رշ�����  
                }
                System.exit(0);// �˳�����  
            }
        });

        // �ı��򰴻س���ʱ�¼�  
        txt_message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });

        // �������Ͱ�ťʱ�¼�  
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                send();
            }
        });

        // ����������������ťʱ�¼�  
        btn_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isStart) {
                    JOptionPane.showMessageDialog(frame, "�������Ѵ�������״̬����Ҫ�ظ�������",
                            "����", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int max;
                int port;
                try {
                    try {
                        max = Integer.parseInt(txt_max.getText());
                    } catch (Exception e1) {
                        throw new Exception("��������Ϊ��������");
                    }
                    if (max <= 0) {
                        throw new Exception("��������Ϊ��������");
                    }
                    try {
                        port = Integer.parseInt(txt_port.getText());
                    } catch (Exception e1) {
                        throw new Exception("�˿ں�Ϊ��������");
                    }
                    if (port <= 0) {
                        throw new Exception("�˿ں� Ϊ��������");
                    }
                    serverStart(max, port);
                    contentArea.append("�������ѳɹ�����!�������ޣ�" + max + ",�˿ڣ�" + port
                            + "\r\n");
                    JOptionPane.showMessageDialog(frame, "�������ɹ�����!");
                    btn_start.setEnabled(false);
                    txt_max.setEnabled(false);
                    txt_port.setEnabled(false);
                    btn_stop.setEnabled(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(),
                            "����", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ����ֹͣ��������ťʱ�¼�  
        btn_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isStart) {
                    JOptionPane.showMessageDialog(frame, "��������δ����������ֹͣ��", "����",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    closeServer();
                    btn_start.setEnabled(true);
                    txt_max.setEnabled(true);
                    txt_port.setEnabled(true);
                    btn_stop.setEnabled(false);
                    contentArea.append("�������ɹ�ֹͣ!\r\n");
                    JOptionPane.showMessageDialog(frame, "�������ɹ�ֹͣ��");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, "ֹͣ�����������쳣��", "����",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // ����������  
    public void serverStart(int max, int port) throws java.net.BindException {
        try {
            serverSocket = new ServerSocket(port);
            serverThread = new Thread(new ServerThread(serverSocket, max));
            serverThread.setDaemon(true);
            serverThread.start();
            isStart = true;
        } catch (BindException e) {
            isStart = false;
            throw new BindException("�˿ں��ѱ�ռ�ã��뻻һ����");
        } catch (Exception e1) {
            e1.printStackTrace();
            isStart = false;
            throw new BindException("�����������쳣��");
        }
    }

    // �رշ�����  
    public void closeServer() {
        try {
            //if (serverThread != null)  
            ///    serverThread.stop();// ֹͣ�������߳�  

            for (int i = Users.size() - 1; i >= 0; i--) {
                // �����������û����͹ر�����  
                Users.get(i).getWriter().println("CLOSE");
                Users.get(i).getWriter().flush();
                // �ͷ���Դ  
                Users.get(i).getSocket().close();// ֹͣ����Ϊ�ͻ��˷�����߳�  
                Users.get(i).getReader().close();
                Users.get(i).getWriter().close();
                Users.get(i).getSocket().close();
                Users.remove(i);
            }
            if (serverSocket != null) {
                serverSocket.close();// �رշ�����������  
            }
            listModel.removeAllElements();// ����û��б�  
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
            isStart = true;
        }
    }

    // Ⱥ����������Ϣ  
    public void sendServerMessage(String message) {
        for (int i = Users.size() - 1; i >= 0; i--) {
            Users.get(i).getWriter().println("��������" + message + "(���˷���)");
            Users.get(i).getWriter().flush();
        }
    }

    // �������߳�  
    class ServerThread implements Runnable {
        private ServerSocket serverSocket;
        private int max;// ��������  

        // �������̵߳Ĺ��췽��  
        public ServerThread(ServerSocket serverSocket, int max) {
            this.serverSocket = serverSocket;
            this.max = max;
        }

        public void run() {
            while (true) {// ��ͣ�ĵȴ��ͻ��˵�����  
                try {
                    Socket socket = serverSocket.accept();
                    if (Users.size() == max) {// ����Ѵ���������  
                        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter w = new PrintWriter(socket.getOutputStream());
                        // ���տͻ��˵Ļ����û���Ϣ  
                        String inf = r.readLine();
                        StringTokenizer st = new StringTokenizer(inf, "r#$@%^q");
                        User user = new User(st.nextToken(), st.nextToken());
                        // �������ӳɹ���Ϣ  
                        w.println("MAXr#$@%^q���������Բ���" + user.getName()
                                + user.getIp() + "�����������������Ѵ����ޣ����Ժ������ӣ�");
                        w.flush();
                        // �ͷ���Դ  
                        r.close();
                        w.close();
                        socket.close();
                        continue;
                    }

                    // δ����������
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());

                    //������Ϣ������ʼ�������û�
                    String inf = reader.readLine();

                    StringTokenizer st = new StringTokenizer(inf, "r#$@%^q");
                    User user = new User(st.nextToken(), st.nextToken());

                    user.setReader(reader);
                    user.setWriter(writer);
                    user.setSocket(socket);

                    Users.add(user);

                    listModel.addElement(user.getName());// ���������б�  
                    contentArea.append(user.getName() + user.getIp() + "����![" + Users.size() + "]\r\n");


                    // �������ӳɹ���Ϣ  
                    writer.println(user.getName() + user.getIp() + "����������ӳɹ�!");
                    writer.flush();

                    // ���͵�ǰ�û��б�
                    if (Users.size() > 0) {
                        String temp = "";
                        for (int i = Users.size() - 1; i >= 0; i--) {
                            if (i == 0)
                                temp += (Users.get(0).getName() + "r#$@%^q" + Users.get(0).getIp());
                            else
                                temp += (Users.get(i).getName() + "r#$@%^q" + Users.get(i).getIp()) + "r#$@%^q";
                        }
                        user.getWriter().println("USERLISTr#$@%^q" + Users.size() + "r#$@%^q" + temp);
                        user.getWriter().flush();
                    }

                    // �����������û����͸��û���������  
                    for (int i = Users.size() - 2; i >= 0; i--) {
                        Users.get(i).getWriter().println("ADDr#$@%^q" + user.getName() + "r#$@%^q" + user.getIp());
                        Users.get(i).getWriter().flush();
                    }


                    // �����Դ˿ͻ��˺���������߳�
                    Thread client = new Thread(new ClientThread(user, socket));
                    client.setDaemon(true);
                    client.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    // Ϊһ���ͻ��˷�����߳�  
    class ClientThread extends Thread {
        private User user;

        // �ͻ����̵߳Ĺ��췽��  
        public ClientThread(User user, Socket socket) {
            this.user = user;
        }

        public void run() {// ���Ͻ��տͻ��˵���Ϣ�����д���  
            String message = null;
            while (true) {
                try {
                    message = user.getReader().readLine();// ���տͻ�����Ϣ  
                    if (message.equals("CLOSE"))// ��������  
                    {
                        contentArea.append(user.getName() + user.getIp() + "����!\r\n");
                        // �Ͽ������ͷ���Դ  
                        user.getReader().close();
                        user.getWriter().close();
                        user.getSocket().close();

                        // �����������û����͸��û�����������  
                        for (int i = Users.size() - 1; i >= 0; i--) {
                            if (Users.get(i).equals(user)) continue;
                            Users.get(i).getWriter().println("DELETEr#$@%^q" + user.getName());
                            Users.get(i).getWriter().flush();
                        }

                        listModel.removeElement(user.getName());// ���������б�  

                        // ɾ�������ͻ��˷����߳�  
                        Users.remove(user);
                        break;

                    } else {
                        dispatcherMessage(message);// ת����Ϣ
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                    break;
                }
            }
        }

        // ת����Ϣ  
        public void dispatcherMessage(String message) {
            StringTokenizer stringTokenizer = new StringTokenizer(message, "r#$@%^q");
            String source = stringTokenizer.nextToken();
            String owner = stringTokenizer.nextToken();
            String content = stringTokenizer.nextToken();
            message = source + "˵��" + content;
            contentArea.append(message + "\r\n");
            if (owner.equals("ALL")) {// Ⱥ��  
                for (int i = Users.size() - 1; i >= 0; i--) {
                    Users.get(i).getWriter().println(message + "(���˷���)");
                    Users.get(i).getWriter().flush();
                }
            }
        }
    }
}  
