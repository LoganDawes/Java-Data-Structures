public class CourseList {
    private CourseNode head;
    private CourseNode tail;
    private int courseCount;
    private int totalStudentCount;

    // Default constructor for course list
    public CourseList(){
        this.head = null;
        this.tail = null;
        this.courseCount = 0;
        this.totalStudentCount = 0;
    }

    // Method to iterate through list and find an existing course
    // Arguments: String courseNumber, Return: CourseNode
    public CourseNode findCourse(String courseNumber){
        CourseNode current = head;
        while (current != null) {
            if (current.courseNumber.equals(courseNumber)) {
                return current; // Return the course node if found
            }
            current = current.next;
        }
        return null; // Return null if not found
    }

    // Method to add a new course to course list
    // Arguments: Strings for course details, Return: void
    public void addCourse(String courseName, String courseNumber){
        // Create a new course node
        CourseNode newCourse = new CourseNode(courseName, courseNumber);

        // If the list is empty, this node becomes head and tail
        if (head == null) {
            head = tail = newCourse;
        }
        else {
            CourseNode current = head;
            CourseNode previous = null;

            // Iterate through the list to find the correct position (alphabetical)
            while (current != null && current.courseNumber.compareTo(courseNumber) < 0) {
                previous = current;
                current = current.next;
            }

            // Insert at the beginning
            if (previous == null) {
                newCourse.next = head; // Link new node to the current head
                head = newCourse; // Update head to new node
            }
            // Insert in the middle or end
            else {
                newCourse.next = current; // Link new node to the current node
                previous.next = newCourse; // Link previous node to the new node

                // If we are inserting at the end, update the tail
                if (current == null) {
                    tail = newCourse; // Update tail to new node
                }
            }
        }

        // Increase course count
        courseCount++;
    }

    // Method to enroll a student into specified course
    // Arguments: Strings for courseNumber and student details, Return: void
    public void enrollStudent(String courseNumber, String studentName, String studentID, String email, String address) {
        CourseNode course = findCourse(courseNumber);
        if (course != null) {
            course.addStudent(studentName, studentID, email, address);
            course.studentCount++;
            totalStudentCount++;
        } else {
            System.out.println("Course not found for enrollment: " + courseNumber);
        }
    }


    // Method to delete a course by course number
    // Arguments: String courseNumber, Return: boolean to check success
    public boolean deleteCourse(String courseNumber) {
        // Needs current and previous to change tail node
        CourseNode current = head;
        CourseNode previous = null;

        // Iterate to courseNumber
        while (current != null) {
            // Check if the current course matches the courseNumber
            if (current.courseNumber.equals(courseNumber)) {
                // Update total student count
                totalStudentCount -= current.studentCount;

                // If it's the first node to be deleted
                if (previous == null) {
                    head = current.next; // Move head to next node
                } else {
                    previous.next = current.next; // Bypass the current node
                }

                // If it's the last node being deleted
                if (current.next == null) {
                    tail = previous; // Move tail to previous node
                }

                courseCount--; // Decrease course count
                return true; // Course deleted successfully
            }
            previous = current;
            current = current.next;
        }
        return false; // Course not found
    }

    // Method to display all courses and their details
    // Arguments: none, Return: void
    public void displayCourses() {
        // Check if any courses are registered
        if (courseCount == 0) {
            System.out.println("No courses registered.");
            return;
        }

        System.out.println("The list of courses registered are as follows:");

        // Traverse the course list and print course details
        CourseNode current = head;
        while (current != null) {
            System.out.println("Course Number: " + current.courseNumber);
            System.out.println("Course Name: " + current.courseName);
            System.out.println("Number of students enrolled: " + current.studentCount);
            System.out.println();

            // Move to the next course
            current = current.next;
        }
    }

    // Remove one totalStudentCount
    // Arguments: none, Return: void
    public void studentRemoved(){
        totalStudentCount--;
    }

    // Methods for summary record:

    // Getter Method for number of courses
    // Arguments: none, Return: int courseCount
    public int getCourseCount() {
        return courseCount;
    }

    // Getter Method for total student count
    // Arguments: none, Return: int totalStudentCount
    public int getTotalStudentCount() {
        return totalStudentCount;
    }
}
