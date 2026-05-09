package finalproo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



public class Delete extends Main implements Manageable1 {

    Datab dt = new Datab();
    JFrame jf;

    Delete(JFrame jf) { this.jf = jf; }

    
    @Override
    public void showPanel() {
        deleteStudent();
    }

    @Override
    public String validate(String... fields) {
        if (fields[0] == null || fields[0].isBlank())
            return "Student ID is required to delete a record.";
        return null;
    }

    void deleteStudent() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Delete Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_RED);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel warn = new JLabel("⚠  This will also remove the student's fee records.");
        warn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        warn.setForeground(ACCENT_YLW);
        warn.setBounds(40, 68, 360, 20);
        form.add(warn);

        JLabel lId = styledLabel("Student ID");
        lId.setBounds(40, 150, 120, 24);
        form.add(lId);
        JTextField txtId = styledField();
        txtId.setBounds(170, 148, 200, 32);
        form.add(txtId);

        JButton del    = styledBtn("Delete", ACCENT_RED);
        JButton cancel = styledBtn("Cancel", TXT_MUTED);
        del.setBounds(230, 240, 110, 36);
        cancel.setBounds(110, 240, 110, 36);
        form.add(del); form.add(cancel);

        del.addActionListener(e -> {
          
            String err = validate(txtId.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                dt.deleteStude(txtId.getText().trim());
                setStatus("Deleted student ID: " + txtId.getText().trim());
            } catch (Exception ex) {
            
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
            vieall(jf);
        });

        cancel.addActionListener(e -> vieall(jf));
        showForm(form);
    }
}
