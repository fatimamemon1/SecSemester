package finalproo;

public class Student1 {


    private String id;
    private String name;
    private String department;
    private String feePaid;
    private String feeRemaining;

    public Student1(String id, String name, String department,
                   String feePaid, String feeRemaining) {
        setId(id);
        setName(name);
        setDepartment(department);
        this.feePaid      = (feePaid      != null) ? feePaid      : "N/A";
        this.feeRemaining = (feeRemaining != null) ? feeRemaining : "N/A";
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public String getDepartment()   { return department; }
    public String getFeePaid()      { return feePaid; }
    public String getFeeRemaining() { return feeRemaining; }

    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Student ID cannot be empty.");
        this.id = id.trim();
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Student name cannot be empty.");
        this.name = name.trim();
    }

    public void setDepartment(String department) {
        if (department == null || department.isBlank())
            throw new IllegalArgumentException("Department cannot be empty.");
        this.department = department.trim();
    }

    public void setFeePaid(String feePaid)           { this.feePaid      = feePaid; }
    public void setFeeRemaining(String feeRemaining) { this.feeRemaining = feeRemaining; }

    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name +
               "', dept='" + department + "'}";
    }
}
