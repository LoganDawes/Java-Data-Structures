public class StudentNode {
    String studentName;
    String studentID;
    String email;
    String address;
    StudentNode next;

    public StudentNode(String studentName, String studentID, String email, String address) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.email = email;
        this.address = address;
        this.next = null;
    }
}
