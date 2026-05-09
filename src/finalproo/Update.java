package finalproo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * INHERITANCE   — extends Main.
 * INTERFACE     — implements Manageable.
 * POLYMORPHISM  — showPanel() shows Update-choice buttons (unique to this class).
 *                 validate() requires all supplied fields to be non-blank.
 */
public class Update extends Main implements Manageable1 {

    Datab dt = new Datab();
    JFrame jf;

    Update(JFrame jf) { this.jf = jf; }

    @Override
    public void showPanel() {
        updateStudent();
    }

    @Override
    public String validate(String... fields) {
        for (String f : fields) {
            if (f == null || f.isBlank())
                return "All fields are required for an update.";
        }
        return null;
    }

    void updateStudent() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Update Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_YLW);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JButton btnIdName = styledBtn("UPDATE ID & NAME",  ACCENT);
        JButton btnDep    = styledBtn("UPDATE DEPARTMENT", ACCENT_YLW);
        btnIdName.setBounds(65, 120, 280, 46);
        btnDep.setBounds(65, 190, 280, 46);
        form.add(btnIdName); form.add(btnDep);

        btnIdName.addActionListener(e -> updateID());
        btnDep.addActionListener(e    -> updateDep());

        showForm(form);
    }

    void updateID() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Update ID & Name");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_YLW);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lCur = styledLabel("Current ID");  lCur.setBounds(40, 100, 130, 24); form.add(lCur);
        JLabel lNew = styledLabel("New ID");       lNew.setBounds(40, 152, 130, 24); form.add(lNew);
        JLabel lNm  = styledLabel("New Name");     lNm.setBounds(40, 204, 130, 24);  form.add(lNm);

        JTextField tfCur = styledField(); tfCur.setBounds(180, 98,  190, 32); form.add(tfCur);
        JTextField tfNew = styledField(); tfNew.setBounds(180, 150, 190, 32); form.add(tfNew);
        JTextField tfNm  = styledField(); tfNm.setBounds(180, 202,  190, 32); form.add(tfNm);

        JButton save   = styledBtn("Save",   ACCENT_GRN);
        JButton cancel = styledBtn("Cancel", TXT_MUTED);
        save.setBounds(230, 295, 110, 36);
        cancel.setBounds(110, 295, 110, 36);
        form.add(save); form.add(cancel);

        save.addActionListener(e -> {
            // POLYMORPHISM — validate() dispatches to THIS class's version
            String err = validate(tfCur.getText(), tfNew.getText(), tfNm.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                dt.updateNAME(tfNm.getText().trim(),
                              tfNew.getText().trim(),
                              tfCur.getText().trim());
                setStatus("Updated: " + tfCur.getText() + " → " +
                          tfNew.getText() + " / " + tfNm.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
            vieall(jf);
        });

        cancel.addActionListener(e -> { updateStudent(); vieall(jf); });
        showForm(form);
    }

    void updateDep() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Update Department");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_YLW);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lId  = styledLabel("Student ID");      lId.setBounds(40, 120, 150, 24);  form.add(lId);
        JLabel lDep = styledLabel("New Department");  lDep.setBounds(40, 175, 150, 24); form.add(lDep);

        JTextField tfId  = styledField(); tfId.setBounds(200, 118, 180, 32);  form.add(tfId);
        JTextField tfDep = styledField(); tfDep.setBounds(200, 173, 180, 32); form.add(tfDep);

        JButton save   = styledBtn("Save",   ACCENT_GRN);
        JButton cancel = styledBtn("Cancel", TXT_MUTED);
        save.setBounds(230, 270, 110, 36);
        cancel.setBounds(110, 270, 110, 36);
        form.add(save); form.add(cancel);

        save.addActionListener(e -> {
            String err = validate(tfId.getText(), tfDep.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                dt.updateDEP(tfDep.getText().trim(), tfId.getText().trim());
                setStatus("Department updated for ID: " + tfId.getText().trim());
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
            vieall(jf);
        });

        cancel.addActionListener(e -> { updateStudent(); vieall(jf); });
        showForm(form);
    }

    void updateFee() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Update Fee Payment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT_YLW);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lId  = styledLabel("Student ID"); lId.setBounds(40, 110, 140, 24);  form.add(lId);
        JLabel lPd  = styledLabel("Fee Paid");   lPd.setBounds(40, 165, 140, 24);  form.add(lPd);

        JTextField tfId = styledField(); tfId.setBounds(190, 108, 190, 32); form.add(tfId);
        JTextField tfPd = styledField(); tfPd.setBounds(190, 163, 190, 32); form.add(tfPd);

        JLabel note = new JLabel("Amount added to paid & deducted from remaining.");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        note.setForeground(TXT_MUTED);
        note.setBounds(40, 205, 340, 18);
        form.add(note);

        JButton save   = styledBtn("Save",   ACCENT_GRN);
        JButton cancel = styledBtn("Cancel", TXT_MUTED);
        save.setBounds(230, 260, 110, 36);
        cancel.setBounds(110, 260, 110, 36);
        form.add(save); form.add(cancel);

        save.addActionListener(e -> {
            String err = validate(tfId.getText(), tfPd.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                dt.updateFee(tfId.getText().trim(), tfPd.getText().trim());
                setStatus("Fee updated for ID: " + tfId.getText().trim());
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
