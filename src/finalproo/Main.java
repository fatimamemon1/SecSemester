package finalproo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public abstract class Main {

    // ── Design tokens ─────────────────────────────────────────────────────────
    static final Color BG_DARK    = new Color(13, 17, 23);
    static final Color BG_PANEL   = new Color(22, 27, 34);
    static final Color BG_CARD    = new Color(30, 37, 46);
    static final Color ACCENT     = new Color(88, 166, 255);
    static final Color ACCENT_GRN = new Color(63, 185, 80);
    static final Color ACCENT_RED = new Color(248, 81, 73);
    static final Color ACCENT_YLW = new Color(210, 153, 34);
    static final Color TXT        = new Color(230, 237, 243);
    static final Color TXT_MUTED  = new Color(139, 148, 158);
    static final Color BORDER     = new Color(48, 54, 61);
    static final Color BTN_TXT    = Color.BLACK;

    static final Font F_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    static final Font F_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    static final Font F_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font F_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font F_MONO  = new Font("Consolas",  Font.PLAIN, 12);

    // ── Shared state (inherited by all subclasses) ─────────────────────────────
    static DefaultTableModel tableModel;
    static JLabel            statusLbl;
    static JPanel            formPanel;

    // ── ABSTRACT METHOD — forces every subclass to provide its own panel ───────
    /**
     * POLYMORPHISM entry-point.
     * Add, Delete, Search, and Update each override this with a different form.
     * The sidebar wires buttons like: operationObject.showPanel() — the correct
     * form is loaded at runtime without the caller knowing which subclass it is.
     */
    public abstract void showPanel();

    // ─────────────────────────────────────────────────────────────────────────
    //  vieall() — reloads ALL rows from DB into the permanent right-panel table
    // ─────────────────────────────────────────────────────────────────────────
    static void vieall(JFrame jf) {
        Datab db = new Datab();
        try {
            String raw = db.viewall();
            tableModel.setRowCount(0);
            for (String line : raw.split("\n")) {
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;
                String id   = parts[0].replaceAll("ID:", "").trim();
                String name = parts[1].replaceAll("Name:", "").trim();
                String dept = parts[2].replaceAll("Dept:", "").trim();
                String paid = parts[3].replaceAll("FeePaid:", "").trim();
                String rem  = parts[4].replaceAll("FeeRemaing:", "").trim();
                tableModel.addRow(new Object[]{id, name, dept, paid, rem});
            }
            setStatus("Loaded " + tableModel.getRowCount() + " record(s).");
        } catch (Exception ex) {
            setStatus("DB error: " + ex.getMessage());
        }
    }

    static void setStatus(String msg) {
        if (statusLbl != null)
            SwingUtilities.invokeLater(() -> statusLbl.setText("  " + msg));
    }

    static void showForm(JPanel form) {
        formPanel.removeAll();
        formPanel.add(form, BorderLayout.CENTER);
        formPanel.revalidate();
        formPanel.repaint();
    }


    //  main() — application entry point
    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        JFrame jf = new JFrame("Student Management System");
        jf.setSize(1200, 700);
        jf.setMinimumSize(new Dimension(960, 560));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.getContentPane().setBackground(BG_DARK);
        jf.setLayout(new BorderLayout());

        // ── TOP HEADER 
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PANEL);
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
        header.setPreferredSize(new Dimension(0, 54));
        JLabel hTitle = new JLabel("  ◈  Student Management System");
        hTitle.setFont(F_TITLE);
        hTitle.setForeground(ACCENT);
        JLabel hSub = new JLabel("finalPro · MySQL  ");
        hSub.setFont(F_SMALL);
        hSub.setForeground(TXT_MUTED);
        header.add(hTitle, BorderLayout.WEST);
        header.add(hSub,   BorderLayout.EAST);
        jf.add(header, BorderLayout.NORTH);

        // ── SIDEBAR 
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_PANEL);
        sidebar.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 1, BORDER),
            new EmptyBorder(24, 12, 24, 12)));
        sidebar.setPreferredSize(new Dimension(200, 0));

        sidebar.add(sectionLbl("STUDENTS"));
        sidebar.add(Box.createVerticalStrut(8));
        JButton btnAddStu = sideBtn("＋  Add Student",    ACCENT_GRN);
        JButton btnSearch = sideBtn("🔍  Search Student", ACCENT);
        JButton btnUpdStu = sideBtn("✏  Update Student",  ACCENT_YLW);
        JButton btnDel    = sideBtn("✕  Delete Student",  ACCENT_RED);
        for (JButton b : new JButton[]{btnAddStu, btnSearch, btnUpdStu, btnDel}) {
            sidebar.add(b); sidebar.add(Box.createVerticalStrut(7));
        }
        sidebar.add(Box.createVerticalStrut(18));
        sidebar.add(sectionLbl("FEES"));
        sidebar.add(Box.createVerticalStrut(8));
        JButton btnAddFee = sideBtn("＋  Add Fee Record", ACCENT_GRN);
        JButton btnUpdFee = sideBtn("✏  Update Fee",      ACCENT_YLW);
        for (JButton b : new JButton[]{btnAddFee, btnUpdFee}) {
            sidebar.add(b); sidebar.add(Box.createVerticalStrut(7));
        }
        sidebar.add(Box.createVerticalStrut(18));
        sidebar.add(sectionLbl("DASHBOARD"));
        sidebar.add(Box.createVerticalStrut(8));
        JButton btnView = sideBtn("↻  Refresh / View All", ACCENT);
        sidebar.add(btnView);
        sidebar.add(Box.createVerticalGlue());

        // ── FORM PANEL (swappable left slot)
        formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(BG_PANEL);
        formPanel.setPreferredSize(new Dimension(430, 0));
        formPanel.setMinimumSize(new Dimension(430, 0));
        formPanel.setBorder(new MatteBorder(0, 0, 0, 1, BORDER));

        JPanel welcome = new JPanel(new GridBagLayout());
        welcome.setBackground(BG_PANEL);
        JLabel wlbl = new JLabel("← Select an action from the sidebar");
        wlbl.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        wlbl.setForeground(TXT_MUTED);
        welcome.add(wlbl);
        formPanel.add(welcome, BorderLayout.CENTER);

        // ── RIGHT PANEL — permanent table
        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(BG_DARK);

        JPanel tblHeader = new JPanel(new BorderLayout());
        tblHeader.setBackground(BG_CARD);
        tblHeader.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER),
            new EmptyBorder(10, 16, 10, 16)));
        JLabel tblTitle = new JLabel("Database — Students & Fees");
        tblTitle.setFont(F_LABEL);
        tblTitle.setForeground(ACCENT);
        tblHeader.add(tblTitle, BorderLayout.WEST);
        right.add(tblHeader, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Department", "Fee Paid", "Fee Remaining"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setBackground(BG_CARD);
        table.setForeground(TXT);
        table.setFont(F_BODY);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setBackground(BG_PANEL);
        table.getTableHeader().setForeground(TXT_MUTED);
        table.getTableHeader().setFont(F_LABEL);
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setFont(F_BODY);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (sel) { setBackground(ACCENT.darker().darker()); setForeground(TXT); }
                else     { setBackground(row % 2 == 0 ? BG_CARD : BG_PANEL); setForeground(TXT); }
                return this;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBorder(new EmptyBorder(8, 8, 8, 8));
        right.add(sp, BorderLayout.CENTER);

        // ── LAYOUT
        JSplitPane innerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, right);
        innerSplit.setDividerLocation(430);
        innerSplit.setDividerSize(3);
        innerSplit.setBorder(null);
        innerSplit.setResizeWeight(0.0);

        JSplitPane outerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, innerSplit);
        outerSplit.setDividerLocation(200);
        outerSplit.setDividerSize(3);
        outerSplit.setBorder(null);
        outerSplit.setResizeWeight(0.0);

        jf.add(outerSplit, BorderLayout.CENTER);

        // ── BOTTOM STATUS BAR 
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(BG_PANEL);
        statusBar.setBorder(new MatteBorder(1, 0, 0, 0, BORDER));
        statusBar.setPreferredSize(new Dimension(0, 26));
        statusLbl = new JLabel("  Ready — use the buttons on the left.");
        statusLbl.setFont(F_SMALL);
        statusLbl.setForeground(TXT_MUTED);
        JLabel dbLbl = new JLabel("MySQL · localhost:3306/finalPro  ");
        dbLbl.setFont(F_SMALL);
        dbLbl.setForeground(ACCENT_GRN);
        statusBar.add(statusLbl, BorderLayout.WEST);
        statusBar.add(dbLbl,     BorderLayout.EAST);
        jf.add(statusBar, BorderLayout.SOUTH);

        // ── MENU BAR 
        JMenuBar jb = new JMenuBar();
        jb.setBackground(BG_PANEL);
        jb.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        JMenu     ne  = styledMenu("New");
        JMenuItem jm  = styledItem("Add Student");
        JMenuItem jm9 = styledItem("Add Fee Record");
        ne.add(jm); ne.add(jm9);

        JMenu     se  = styledMenu("Search");
        JMenuItem jm1 = styledItem("Search Student");
        se.add(jm1);

        JMenu     up  = styledMenu("Update");
        JMenuItem jm3 = styledItem("Update Student Record");
        JMenuItem jm4 = styledItem("Update Fee Record");
        up.add(jm3); up.add(jm4);

        JMenu     del  = styledMenu("Delete");
        JMenuItem jm0  = styledItem("Delete Student Record");
        del.add(jm0);

        jb.add(ne); jb.add(se); jb.add(up); jb.add(del);
        jf.setJMenuBar(jb);

        // ── WIRE BUTTONS — polymorphism in action ─────────────────────────────
        // We call showPanel() on each object. Java decides AT RUNTIME which
        // subclass implementation to run — that IS runtime polymorphism.
        Add    addOp    = new Add(jf);
        Search searchOp = new Search(jf);
        Update updateOp = new Update(jf);
        Delete deleteOp = new Delete(jf);

        btnAddStu.addActionListener(e -> { addOp.showPanel();    vieall(jf); });
        btnSearch.addActionListener(e ->   searchOp.showPanel());
        btnUpdStu.addActionListener(e -> { updateOp.showPanel(); vieall(jf); });
        btnDel.addActionListener(e    -> { deleteOp.showPanel(); vieall(jf); });
        btnAddFee.addActionListener(e -> { new Add(jf).addFee(); vieall(jf); });
        btnUpdFee.addActionListener(e -> { new Update(jf).updateFee(); vieall(jf); });
        btnView.addActionListener(e   ->   vieall(jf));

        jm.addActionListener(e  -> { addOp.showPanel();    vieall(jf); });
        jm9.addActionListener(e -> { new Add(jf).addFee(); vieall(jf); });
        jm1.addActionListener(e ->   searchOp.showPanel());
        jm3.addActionListener(e -> { updateOp.showPanel(); vieall(jf); });
        jm4.addActionListener(e -> { new Update(jf).updateFee(); vieall(jf); });
        jm0.addActionListener(e -> { deleteOp.showPanel(); vieall(jf); });

        jf.setVisible(true);
        vieall(jf);
    }

    // ── UI helpers (inherited by all subclasses) 
    static JLabel sectionLbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(TXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    static JButton sideBtn(String text, Color accent) {
        JButton b = new JButton(text);
        b.setFont(F_LABEL);
        b.setForeground(BTN_TXT);
        b.setBackground(BG_CARD);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 1, true),
            BorderFactory.createEmptyBorder(0, 14, 0, 14)));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(accent.darker().darker()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(BG_CARD); }
        });
        return b;
    }

    static JMenu styledMenu(String text) {
        JMenu m = new JMenu(text);
        m.setForeground(Color.BLACK);
        m.setFont(F_LABEL);
        m.getPopupMenu().setBackground(BG_PANEL);
        m.getPopupMenu().setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return m;
    }

    static JMenuItem styledItem(String text) {
        JMenuItem i = new JMenuItem(text);
        i.setFont(F_BODY);
        i.setForeground(Color.BLACK);
        i.setBackground(BG_PANEL);
        i.setBorder(new EmptyBorder(6, 14, 6, 14));
        return i;
    }

    static JTextField styledField() {
        JTextField tf = new JTextField();
        tf.setBackground(BG_CARD);
        tf.setForeground(TXT);
        tf.setFont(F_BODY);
        tf.setCaretColor(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        return tf;
    }

    static JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(F_LABEL);
        l.setForeground(TXT_MUTED);
        return l;
    }

    static JButton styledBtn(String text, Color accent) {
        JButton b = new JButton(text);
        b.setFont(F_LABEL);
        b.setForeground(BTN_TXT);
        b.setBackground(BG_CARD);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 1, true),
            BorderFactory.createEmptyBorder(6, 20, 6, 20)));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(accent.darker().darker()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(BG_CARD); }
        });
        return b;
    }
}
