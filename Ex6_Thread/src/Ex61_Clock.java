import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sharuru on 2015/4/24 0010.
 * Extra question 1: Design a clock with thread controlled.
 */

public class Ex61_Clock extends JFrame {
    private Thread myTimer;
    private int flag = 1;
    JButton btnNewButton = new JButton("Pause");
    JLabel lblNewLabel = new JLabel("New label");

    class MyTimer implements Runnable {

        @Override
        public void run() {
            while (true) {
                EventQueue.invokeLater(() -> lblNewLabel.setText(new SimpleDateFormat("HH:mm:ss:SS").format(new Date())
                ));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ex61_Clock frame = new Ex61_Clock();
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ex61_Clock() {
        myTimer = new Thread(new MyTimer());
        myTimer.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent arg0) {
                lblNewLabel.setText(
                        new SimpleDateFormat("hh:mm:ss").format(new Date())
                );

            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        lblNewLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 64));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(lblNewLabel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        btnNewButton.addActionListener(arg0 -> {
            if (flag == 1) {
                myTimer.interrupt();
                btnNewButton.setText("Restart");
                flag = 0;
            } else {
                myTimer = new Thread(new MyTimer());
                myTimer.start();
                btnNewButton.setText("Pause");
                flag = 1;
            }
        });
        panel.add(btnNewButton);
    }
}
