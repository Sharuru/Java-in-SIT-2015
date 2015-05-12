import java.sql.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Ex82_DBInsert extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    final Connection conn;

	/*public static void main(String[] args) {
        try {
			Ex82_DBInsert dialog = new Ex82_DBInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/

    /**
     * Create the dialog.
     */
    public Ex82_DBInsert(RowBean rb, final Connection conn) {
        this.conn = conn;
        setBounds(100, 100, 378, 257);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        this.setModal(true);
        JLabel lblNewLabel = new JLabel("ID:");
        lblNewLabel.setBounds(59, 28, 72, 18);
        contentPanel.add(lblNewLabel);

        textField = new JTextField();
        textField.setEnabled(false);
        textField.setBounds(149, 25, 148, 24);
        contentPanel.add(textField);
        textField.setColumns(10);
        textField.setText(rb.id + "");

        JLabel lblSname = new JLabel("SNAME:");
        lblSname.setBounds(59, 67, 72, 18);
        contentPanel.add(lblSname);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(149, 64, 148, 24);
        textField_1.setText(rb.name);
        contentPanel.add(textField_1);

        JLabel lblScore = new JLabel("SCORE:");
        lblScore.setBounds(59, 111, 72, 18);
        contentPanel.add(lblScore);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(149, 108, 148, 24);
        textField_2.setText(rb.score + "");
        contentPanel.add(textField_2);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement stmt1 = conn.createStatement();

                    if (textField.getText().equals("-1")) {
                        stmt1.executeUpdate("insert into student (sname,score) values (\""
                                        + textField_1.getText() + "\", "
                                        + textField_2.getText() + ")"
                        );
                    } else {
                        stmt1.executeUpdate("update student set sname =\""
                                        + textField_1.getText() + "\" , score ="
                                        + textField_2.getText() + " where id="
                                        + textField.getText()
                        );

                    }
                    stmt1.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                ((Ex82_DBInsert) ((JComponent) e.getSource()).getTopLevelAncestor()).dispose();
            }

        });

        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((Ex82_DBInsert) ((JComponent) e.getSource()).getTopLevelAncestor()).dispose();

            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

    }
}
