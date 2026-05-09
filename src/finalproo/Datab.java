package finalproo;

import java.sql.*;

public class Datab extends Main {

 
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/finalPro", "root", "12345");
    }

    
    public void addu(String ID, String NAME, String DEP) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO Students VALUES (?, ?, ?)")) {
            ps.setString(1, ID);
            ps.setString(2, NAME);
            ps.setString(3, DEP);
            ps.executeUpdate();
        }
       
    }

   
    public String viewall() throws Exception {
        StringBuilder data = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Students s LEFT JOIN StudentFee f ON s.ID = f.ID " +
                 "UNION " +
                 "SELECT * FROM Students s RIGHT JOIN StudentFee f ON s.ID = f.ID");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.append("ID: ").append(rs.getInt("ID")).append(" | ");
                data.append("Name: ").append(rs.getString("NAME")).append(" | ");
                data.append("Dept: ").append(rs.getString("DEPARTMENT")).append(" | ");
                data.append("FeePaid: ").append(rs.getString("feePaid")).append(" | ");
                data.append("FeeRemaing: ").append(rs.getString("feeRem")).append(" | ");
                data.append("------------------------------------------------\n");
            }
        }
        return data.toString();
    }

    // ── Search by ID ──────────────────────────────────────────────────────────
    public String searchID(String ID) throws Exception {
        StringBuilder data = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Students WHERE ID LIKE ?")) {
            ps.setString(1, "%" + ID + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.append("ID   ").append(rs.getString(1)).append("    |    ");
                    data.append("NAME   ").append(rs.getString(2)).append("    |   ");
                    data.append("Department   ").append(rs.getString(3)).append("\n");
                }
            }
        }
        return data.toString();
    }

    public String searchNAME(String name) throws Exception {
        StringBuilder data = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Students WHERE NAME LIKE ?")) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.append("ID   ").append(rs.getString(1)).append("    |    ");
                    data.append("NAME   ").append(rs.getString(2)).append("    |   ");
                    data.append("Department   ").append(rs.getString(3)).append("\n");
                }
            }
        }
        return data.toString();
    }


    public String searchDEP(String dep) throws Exception {
        StringBuilder data = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Students WHERE DEPARTMENT LIKE ?")) {
            ps.setString(1, "%" + dep + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.append("ID   ").append(rs.getString(1)).append("    |    ");
                    data.append("NAME   ").append(rs.getString(2)).append("    |   ");
                    data.append("Department   ").append(rs.getString(3)).append("\n");
                }
            }
        }
        return data.toString();
    }

    
    public void updateNAME(String NAME, String nID, String ID) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE Students SET NAME = ?, ID = ? WHERE ID = ?")) {
            ps.setString(1, NAME);
            ps.setString(2, nID);
            ps.setString(3, ID);
            ps.executeUpdate();
        }
    }

 
    public void updateDEP(String DEP, String ID) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE Students SET DEPARTMENT = ? WHERE ID = ?")) {
            ps.setString(1, DEP);
            ps.setString(2, ID);
            ps.executeUpdate();
        }
    }


    public void deleteStude(String ID) throws Exception {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps1 = con.prepareStatement(
                     "DELETE FROM StudentFee WHERE ID = ?");
                 PreparedStatement ps2 = con.prepareStatement(
                     "DELETE FROM Students WHERE ID = ?")) {
                ps1.setString(1, ID);
                ps1.executeUpdate();
                ps2.setString(1, ID);
                ps2.executeUpdate();
            }
        }
    }

    public void addfee(String ID, String feePaid, String feeRem) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO StudentFee VALUES (?, ?, ?)")) {
            ps.setString(1, ID);
            ps.setString(2, feePaid);
            ps.setString(3, feeRem);
            ps.executeUpdate();
        }
    }


    public void updateFee(String ID, String feePaid) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE StudentFee SET feePaid = feePaid + ?, feeRem = feeRem - ? WHERE ID = ?")) {
            ps.setString(1, feePaid);
            ps.setString(2, feePaid);
            ps.setString(3, ID);
            ps.executeUpdate();
        }
    }

    @Override
    public void showPanel() {
        
    }
}
