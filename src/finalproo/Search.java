package finalproo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class Search extends Main implements Manageable1 {

    Datab dt = new Datab();
    JFrame jf;

    Search(JFrame jf) { this.jf = jf; }

  
    @Override
    public void showPanel() {
        searchStudent();
    }

    
    @Override
    public String validate(String... fields) {
        if (fields[0] == null || fields[0].isBlank())
            return "Please enter a search term.";
        return null;
    }

    void searchStudent() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Search Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JButton btnId   = styledBtn("SEARCH BY ID",         ACCENT);
        JButton btnName = styledBtn("SEARCH BY Name",       ACCENT);
        JButton btnDep  = styledBtn("SEARCH BY Department", ACCENT);
        btnId.setBounds(65, 110, 280, 46);
        btnName.setBounds(65, 178, 280, 46);
        btnDep.setBounds(65, 246, 280, 46);
        form.add(btnId); form.add(btnName); form.add(btnDep);

        btnId.addActionListener(e   -> searchId());
        btnName.addActionListener(e -> searchName());
        btnDep.addActionListener(e  -> searchDep());

        showForm(form);
    }

    void searchId() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Search by ID");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lbl = styledLabel("Student ID");
        lbl.setBounds(40, 150, 130, 24);
        form.add(lbl);
        JTextField tf = styledField();
        tf.setBounds(180, 148, 200, 32);
        form.add(tf);

        JButton search  = styledBtn("Search",   ACCENT);
        JButton back    = styledBtn("Back",      TXT_MUTED);
        JButton viewAll = styledBtn("View All",  ACCENT_GRN);
        search.setBounds(230, 220, 110, 36);
        back.setBounds(110, 220, 110, 36);
        viewAll.setBounds(110, 270, 230, 36);
        form.add(search); form.add(back); form.add(viewAll);

        search.addActionListener(e -> {
            // POLYMORPHISM — validate() is this class's version
            String err = validate(tf.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                String result = dt.searchID(tf.getText());
                displayInTable(result);
                setStatus("Search by ID: " + tableModel.getRowCount() + " result(s) shown.");
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> searchStudent());
        viewAll.addActionListener(e -> vieall(jf));
        showForm(form);
    }

    void searchName() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Search by Name");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lbl = styledLabel("Student Name");
        lbl.setBounds(40, 150, 130, 24);
        form.add(lbl);
        JTextField tf = styledField();
        tf.setBounds(185, 148, 190, 32);
        form.add(tf);

        JButton search  = styledBtn("Search",   ACCENT);
        JButton back    = styledBtn("Back",      TXT_MUTED);
        JButton viewAll = styledBtn("View All",  ACCENT_GRN);
        search.setBounds(230, 220, 110, 36);
        back.setBounds(110, 220, 110, 36);
        viewAll.setBounds(110, 270, 230, 36);
        form.add(search); form.add(back); form.add(viewAll);

        search.addActionListener(e -> {
            String err = validate(tf.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                String result = dt.searchNAME(tf.getText());
                displayInTable(result);
                setStatus("Search by Name: " + tableModel.getRowCount() + " result(s) shown.");
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> searchStudent());
        viewAll.addActionListener(e -> vieall(jf));
        showForm(form);
    }

    void searchDep() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_PANEL);

        JLabel title = new JLabel("Search by Department");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(ACCENT);
        title.setBounds(40, 30, 300, 30);
        form.add(title);

        JLabel lbl = styledLabel("Department");
        lbl.setBounds(40, 150, 130, 24);
        form.add(lbl);
        JTextField tf = styledField();
        tf.setBounds(185, 148, 190, 32);
        form.add(tf);

        JButton search  = styledBtn("Search",   ACCENT);
        JButton back    = styledBtn("Back",      TXT_MUTED);
        JButton viewAll = styledBtn("View All",  ACCENT_GRN);
        search.setBounds(230, 220, 110, 36);
        back.setBounds(110, 220, 110, 36);
        viewAll.setBounds(110, 270, 230, 36);
        form.add(search); form.add(back); form.add(viewAll);

        search.addActionListener(e -> {
            String err = validate(tf.getText());
            if (err != null) { setStatus("Validation: " + err); return; }

            try {
                String result = dt.searchDEP(tf.getText());
                displayInTable(result);
                setStatus("Search by Dept: " + tableModel.getRowCount() + " result(s) shown.");
            } catch (Exception ex) {
                ex.printStackTrace();
                setStatus("DB error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> searchStudent());
        viewAll.addActionListener(e -> vieall(jf));
        showForm(form);
    }

    private void displayInTable(String raw) {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        for (String line : raw.split("\n")) {
            if (line.isBlank()) continue;
            String[] parts = line.split("\\|");
            if (parts.length >= 3) {
                String id   = parts[0].replaceAll("ID", "").trim();
                String name = parts[1].replaceAll("NAME", "").trim();
                String dep  = parts[2].replaceAll("Department", "").trim();
                tableModel.addRow(new Object[]{id, name, dep, "—", "—"});
            }
        }
    }
}
