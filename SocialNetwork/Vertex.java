public class Vertex {
    private String id;
    private String studentsFirstName;
    private String studentsLastName;
    private String college;
    private String department;
    private String email;
    private int friendCount = 0;

    // Arguments: six strings
    // Returns: none
    // Desc: constructor for a vertex using student data.
    public Vertex(String id, String firstName, String lastName, String college, String department, String email) {
        this.id = id;
        this.studentsFirstName = firstName;
        this.studentsLastName = lastName;
        this.college = college;
        this.department = department;
        this.email = email;
    }

    // Arguments: none
    // Returns: string
    // Desc: getter method for ID
    public String getId() {
        return id;
    }

    // Arguments: none
    // Returns: string
    // Desc: getter method for first name
    public String getStudentsFirstName() {
        return studentsFirstName;
    }

    // Arguments: none
    // Returns: string
    // Desc: getter method for college
    public String getCollege() {
        return college;
    }

    // Arguments: none
    // Returns: none
    // Desc: increment friend count by 1
    public void incrementFriendCount() {
        friendCount++;
    }

    // Arguments: none
    // Returns: none
    // Desc: decrements friend count by 1
    public void decrementFriendCount() {
        if (friendCount > 0) {
            friendCount--;
        }
    }

    // Arguments: none
    // Returns: string
    // Desc: to string makes sure vertices print as the student's first name.
    @Override
    public String toString() {
        return studentsFirstName;
    }
}

