import java.sql.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Ex82_GuiDB extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel dtm = null;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String sql;
    int rowNo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ex82_GuiDB frame = new Ex82_GuiDB();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Ex82_GuiDB() throws ClassNotFoundException, SQLException {

        // Access
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        conn = DriverManager.getConnection("jdbc:ucanaccess://d:/1.mdb");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 685, 501);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new GridLayout(0, 5, 10, 0));

        JButton btnNewButton_2 = new JButton("New");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBInsert dialog = new DBInsert(new RowBean(-1, "", 0d), conn);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);

                readRec();
                table.setModel(dtm);

            }
        });

        panel.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Update");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rowNo == 0) {
                    JOptionPane.showMessageDialog(null, "No Rows Exist, New");
                }

                int rowid = table.getSelectedRow();
                if (rowid == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a Row!");
                    return;
                } else {
                    DBInsert dialog = new DBInsert(new RowBean(
                            Integer.valueOf(table.getValueAt(rowid, 0).toString()),
                            (table.getValueAt(rowid, 1).toString()),
                            Double.valueOf(table.getValueAt(rowid, 2).toString())
                    ), conn);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);

                    readRec();
                    table.setModel(dtm);

                }
            }
        });
        panel.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Delete");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rowNo == 0) {
                    JOptionPane.showMessageDialog(null, "No Rows Exist, New");
                }


                int rowid = table.getSelectedRow();
                if (rowid == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a Row!");
                    return;
                }

                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("Delete  from student  where id ="
                            + table.getValueAt(rowid, 0).toString());


                    readRec();
                    table.setModel(dtm);

                    stmt.close();
                } catch (Exception e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
            }
        });
        panel.add(btnNewButton_4);

        JLabel lblNewLabel = new JLabel("");
        panel.add(lblNewLabel);

        JButton btnNewButton = new JButton("Close");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        panel.add(btnNewButton);

        readRec();
        table = new JTable(dtm);
        JScrollPane jsp = new JScrollPane(table);
        contentPane.add(jsp, BorderLayout.CENTER);

    }

    int readRec() {
        rowNo = 0;
        int colNo = 0;
        String columName[];
        String rows[][];
        sql = "select * from student";


        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);

            ResultSetMetaData dbmd = rs.getMetaData();

            colNo = dbmd.getColumnCount();
            if (colNo == 0) return rowNo;

            columName = new String[colNo];
            for (int x = 1; x <= dbmd.getColumnCount(); x++) {
                columName[x - 1] = dbmd.getColumnName(x);
            }

            //求长度
            if (rs.last()) {
                rowNo = rs.getRow();
                rows = new String[rowNo][colNo];
                rs.beforeFirst();

                int i = 0, j = 0;
                while (rs.next()) {
                    for (j = 0; j < colNo; j++) {
                        rows[i][j] = rs.getObject(j + 1).toString();
                    }
                    i++;
                }
            } else {
                rows = new String[1][colNo];
                for (int j = 0; j < colNo; j++) {
                    rows[0][j] = "";
                }
            }


            dtm = new DefaultTableModel(rows, columName);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();

        }


        return rowNo;

    }

}
