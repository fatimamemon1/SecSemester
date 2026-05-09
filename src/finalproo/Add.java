package finalproo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class Add extends Main implements Manageable1 {

    Datab dt = new Datab();
    JFrame jf;

    Add(JFrame jf) { this.jf = jf; }

   
    @Override
    public void showPanel() {
        addStudent();
    }

    @Override
    public String validate(String... fields) {
        // fields[0]=id, fields[1]=name, fields[2]=department
        for (String f : fields) {
            if (f == null || f.isBlank())
                return "All fields are required. Please fill in every box.";
        }
        return null;
    }

   
    void addStudent() {
        JPanel form = buildForm();

        JLabel title = new JLabel("Add Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_GRN);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lId = styledLabel("Student ID");   lId.setBounds(40, 100, 120, 24); form.add(lId);
        JTextField txtId = styledField();          txtId.setBounds(170, 98, 200, 32); form.add(txtId);

        JLabel lNm = styledLabel("Full Name");    lNm.setBounds(40, 155, 120, 24); form.add(lNm);
        JTextField txtNm = styledField();          txtNm.setBounds(170, 153, 200, 32); form.add(txtNm);

        JLabel lDp = styledLabel("Department");   lDp.setBounds(40, 210, 120, 24); form.add(lDp);
        JTextField txtDp = styledField();          txtDp.setBounds(170, 208, 200, 32); form.add(txtDp);

        JButton save   = styledBtn("Save",   ACCENT_GRN);
        JButton cancel = styledBtn("Cancel", ACCENT_RED);
        save.setBounds(230, 300, 110, 36);
        cancel.setBounds(110, 300, 110, 36);
        form.add(save); form.add(cancel);

        save.addActionListener(e -> {
         
            String err = validate(txtId.getText(), txtNm.getText(), txtDp.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
               
                Student1 s = new Student1(
                    txtId.getText(), txtNm.getText(), txtDp.getText(), null, null);
                dt.addu(s.getId(), s.getName(), s.getDepartment());
                setStatus("Student added: " + s.getName() + " (ID: " + s.getId() + ")");
            } catch (IllegalArgumentException iae) {
               
                setStatus("Input error: " + iae.getMessage());
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
            vieall(jf);
        });

        cancel.addActionListener(e -> vieall(jf));
        showForm(form);
    }

 
    void addFee() {
        JPanel form = buildForm();

        JLabel title = new JLabel("Add Fee Record");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_GRN);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lId  = styledLabel("Student ID");     lId.setBounds(40, 100, 140, 24); form.add(lId);
        JTextField txtId = styledField();              txtId.setBounds(190, 98, 190, 32); form.add(txtId);

        JLabel lPd  = styledLabel("Fee Paid");        lPd.setBounds(40, 155, 140, 24); form.add(lPd);
        JTextField txtPd = styledField();              txtPd.setBounds(190, 153, 190, 32); form.add(txtPd);

        JLabel lRm  = styledLabel("Fee Remaining");   lRm.setBounds(40, 210, 140, 24); form.add(lRm);
        JTextField txtRm = styledField();              txtRm.setBounds(190, 208, 190, 32); form.add(txtRm);

        JButton save   = styledBtn("Save",   ACCENT_GRN);
        JButton cancel = styledBtn("Cancel", ACCENT_RED);
        save.setBounds(230, 300, 110, 36);
        cancel.setBounds(110, 300, 110, 36);
        form.add(save); form.add(cancel);

        save.addActionListener(e -> {
            String err = validate(txtId.getText(), txtPd.getText(), txtRm.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                dt.addfee(txtId.getText().trim(),
                          txtPd.getText().trim(),
                          txtRm.getText().trim());
                setStatus("Fee record added for ID: " + txtId.getText().trim());
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
            vieall(jf);
        });

        cancel.addActionListener(e -> vieall(jf));
        showForm(form);
    }

    private JPanel buildForm() {
        JPanel p = new JPanel(null);
        p.setBackground(BG_PANEL);
        return p;
    }
}
