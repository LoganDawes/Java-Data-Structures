public class CourseNode {
    String courseName;
    String courseNumber;
    int studentCount;
    CourseNode previous;
    CourseNode next;
    StudentNode studentList;

    // Default constructor for a CourseNode, using course Name and number.
    // Arguments: Strings for courseName and courseNumber, Return: none
    public CourseNode(String courseName, String courseNumber){
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.studentCount = 0;
        this.previous = null;
        this.next = null;
        this.studentList = null;
    }

    // Method adds a student to the studentList
    // Arguments: Strings for student details, Return: void
    public void addStudent(String studentName, String studentID, String email, String address){
        // Create the new student node
        StudentNode newStudent = new StudentNode(studentName, studentID, email, address);

        // If the student list is empty, set the new student as the head
        if (studentList == null) {
            studentList = newStudent;
        }
        else {
            StudentNode current = studentList;
            StudentNode previous = null;

            // Iterate through the list to find the correct position (alphabetical)
            while (current != null && current.studentName.compareTo(studentName) < 0) {
                previous = current;
                current = current.next;
            }

            // Insert at the beginning
            if (previous == null) {
                newStudent.next = studentList; // Link new student to the current head
                studentList = newStudent; // Update head to new student
            }
            // Insert in the middle or end
            else {
                newStudent.next = current; // Link new student to the current node
                previous.next = newStudent; // Link previous node to the new student
            }
        }

        // Increase studentCount
        studentCount++;
    }

    // Method deletes a student from studentList
    // Arguments: String studentID, Return: boolean value to check success
    public boolean deleteStudent(String studentID) {
        // Special case for the head of the student list
        if (studentList != null && studentList.studentID.equals(studentID)) {
            studentList = studentList.next; // Move head to next node
            studentCount--; // Decrease student count
            return true; // Student deleted successfully
        }

        StudentNode current = studentList;

        while (current != null && current.next != null) {
            // Check if the next student matches the studentID
            if (current.next.studentID.equals(studentID)) {
                current.next = current.next.next;
                studentCount--; // Decrease student count
                return true; // Student deleted successfully
            }
            current = current.next;
        }
        return false; // Student not found
    }

    // Method to return a student searching by name
    // Arguments: String studentName, Return: StudentNode
    public StudentNode findStudentByName(String studentName){
        StudentNode current = studentList;

        // Iterate through studentList and check names
        while (current != null) {
            if (current.studentName.equals(studentName)) {
                return current; // Student found
            }
            current = current.next;
        }

        return null; // Student not found
    }

    // Method to display all students in the course
    // Arguments: String courseNumber, Return: void
    public void displayStudents(String courseNumber) {
        // Handle case of no students in course
        if (studentCount == 0) {
            System.out.println("No students enrolled in the course " + courseNumber);
            return;
        }

        System.out.println("The list of students enrolled in the course " + courseNumber + " are as follows:");
        System.out.println("Student’s ID \t Students Name \t Email \t Address");

        // Traverse the student list and display student details
        StudentNode currentStudent = studentList;
        while (currentStudent != null) {
            System.out.println(currentStudent.studentID + " \t " +
                    currentStudent.studentName + " \t " +
                    currentStudent.email + " \t " +
                    currentStudent.address);
            currentStudent = currentStudent.next;
        }
    }
}
