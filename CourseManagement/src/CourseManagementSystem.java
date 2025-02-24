import java.util.Scanner;
import java.io.*;

/*
    Author Name: Logan Dawes
    Email: logan.dawes@okstate.edu
    Date: 9/25/2024
    Program Description: Manages courses through a doubly-linked list, and manages students as a single-linked list attached to each CourseNode.
    Has functionality to insert and delete both students and courses, tranfer students, and display both courses and students as a formatted output.
 */

// Main class for the menu-driven program
public class CourseManagementSystem {
    private static CourseList courseList = new CourseList();
    private static Scanner scanner = new Scanner(System.in);

    // Main method that is ran first
    public static void main(String[] args) {
        int option;
        do {
            // Displays the number options
            displayMenu();
            // Scanner reads next integer input to decide the option
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            // Switch to execute method based on picked option
            switch (option) {
                case 1:
                    readInputFile();
                    break;
                case 2:
                    deleteCourse();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    insertNewStudent();
                    break;
                case 6:
                    transferStudent();
                    break;
                case 7:
                    displayCourseList();
                    break;
                case 8:
                    displayStudentList();
                    break;
                case 9:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        } while (option != 9);
    }

    // Method to display the menu that provides all the options for the program.
    // Arguments: none, Return: void
    private static void displayMenu(){
        System.out.println("\n1. Read the input data");
        System.out.println("2. Delete a course");
        System.out.println("3. Insert a new course");
        System.out.println("4. Delete a student");
        System.out.println("5. Insert a new student");
        System.out.println("6. Transfer a student from one course to another");
        System.out.println("7. Display the course list");
        System.out.println("8. Display the student list");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to output the Summary of record at the end of several options
    // Arguments: none, Return: void
    private static void recordSummary(){
        System.out.println("Summary of the record:");
        System.out.println("Number of courses registered: " + courseList.getCourseCount());
        System.out.println("Number of total students: " + courseList.getTotalStudentCount());
    }

    // Read Input file method (1)
    // Arguments: none, Return: void
    private static void readInputFile() {
        // Use getResourceAsStream to load the file from the src directory (classpath)
        InputStream inputFileStream = CourseManagementSystem.class.getClassLoader().getResourceAsStream("inputFile.txt");

        // Check if the file is found in the classpath
        if (inputFileStream == null) {
            System.out.println("Error: inputFile.txt not found in src folder.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream))) {
            // Read and ignore the first line
            String headerLine = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by tabs
                String[] fields = line.split("\t");
                    String courseNumber = fields[0];
                    String courseName = fields[1];
                    String studentName = fields[2];
                    String studentID = fields[3];
                    String email = fields[4];
                    String address = fields[5];

                    // Reformat student name from "Middle Last, First" to "First Middle Last"
                    String[] nameParts = studentName.split(", ");
                        String middleLast = nameParts[0].trim(); // Middle Last
                        String firstName = nameParts[1].trim(); // First

                        // Construct the new name format
                        String formattedName = firstName + " " + middleLast;

                        // Check if the course already exists
                        CourseNode existingCourse = courseList.findCourse(courseNumber);
                        if (existingCourse == null) {
                            // If the course does not exist, add it
                            courseList.addCourse(courseName, courseNumber);
                        }
                // Enroll the student
                courseList.enrollStudent(courseNumber, formattedName, studentID, email, address);
            }
            System.out.println("Input file is read successfully..");
            // Record summary
            recordSummary();
        }
        // Method catches IOException
        catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    // Method to delete a course (2)
    // Arguments: none, Return: void
    private static void deleteCourse() {
        System.out.print("Enter the course number to delete: ");
        String courseNumber = scanner.nextLine().trim();

        // checks if deleteCourse is successful
        boolean deleted = courseList.deleteCourse(courseNumber);
        if (deleted) {
            System.out.println("Course " + courseNumber + " has been deleted successfully.");
        } else {
            System.out.println("Course " + courseNumber + " not found.");
        }

        // Display the summary information after deletion
        recordSummary();
    }

    // Method to insert a new course (3)
    // Arguments: none, Return: void
    private static void addCourse() {
        System.out.print("Enter the new course number to add: ");
        String courseNumber = scanner.nextLine().trim();

        System.out.print("Enter the new course name for " + courseNumber + ": ");
        String courseName = scanner.nextLine().trim();

        // Add the course using the CourseList's method
        courseList.addCourse(courseName, courseNumber);

        // Display the summary information after adding the course
        recordSummary();
    }

    // Method to delete a student from a course (4)
    // Arguments: none, Return: void
    private static void deleteStudent() {
        System.out.print("Enter the student ID number to delete: ");
        String studentID = scanner.nextLine().trim();

        System.out.print("Enter the course number from which the student is to be dropped from: ");
        String courseNumber = scanner.nextLine().trim();

        // Find the course first
        CourseNode course = courseList.findCourse(courseNumber);
        // checks if findCourse was successful
        if (course != null) {
            // Delete the student from the course
            boolean deleted = course.deleteStudent(studentID);
            // checks if deleteStudent was successful
            if (deleted) {
                courseList.studentRemoved();
                System.out.println("Student " + studentID + " has been deleted successfully from course " + courseNumber + ".");
            }
            else {
                System.out.println("Student " + studentID + " not found in course " + courseNumber + ".");
            }
        }
        else {
            System.out.println("Course " + courseNumber + " not found.");
        }

        // Display the summary information after deletion
        recordSummary();
    }

    // Method to insert a new student (5)
    // Arguments: none, Return: void
    private static void insertNewStudent(){
        System.out.print("Enter the course number the student wants to enroll to: ");
        String courseNumber = scanner.nextLine().trim();

        // Save the course
        CourseNode course = courseList.findCourse(courseNumber);

        if (course != null) {
            // Collect student information
            System.out.print("Enter the student’s name: ");
            String studentName = scanner.nextLine().trim();

            System.out.print("Enter the student’s ID: ");
            String studentID = scanner.nextLine().trim();

            System.out.print("Enter the student’s email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter the student’s emergency contact address: ");
            String address = scanner.nextLine().trim();

            // Add the student to the course
            courseList.enrollStudent(course.courseNumber, studentName, studentID, email, address);

            // Display the updated summary
            recordSummary();
        }
    }

    // Method to transfer a student from one course to another (6)
    // Arguments: none, Return: void
    private static void transferStudent(){
        // Collecting input
        System.out.print("Enter the student’s name: ");
        String studentName = scanner.nextLine().trim();

        System.out.print("Enter the course number the student wants to drop from: ");
        String dropCourseNumber = scanner.nextLine().trim();

        System.out.print("Enter the course number the student wants to enroll to: ");
        String enrollCourseNumber = scanner.nextLine().trim();

        // Find the courses
        CourseNode dropCourse = courseList.findCourse(dropCourseNumber);
        CourseNode enrollCourse = courseList.findCourse(enrollCourseNumber);

        // Check if both courses exist
        if (dropCourse != null && enrollCourse != null) {
            // Find the student in the drop course and save in student
            StudentNode student = dropCourse.findStudentByName(studentName);
            if (student != null) {
                // Remove the student from the drop course and check if successful
                if (dropCourse.deleteStudent(student.studentID)) {
                    // Add the student to the new course
                    courseList.enrollStudent(enrollCourseNumber, studentName, student.studentID, student.email, student.address);
                    courseList.studentRemoved();
                }
                else {
                    System.out.println("Failed to remove student " + studentName + " from course " + dropCourseNumber + ".");
                }
            }
            else {
                System.out.println("Student " + studentName + " not found in course " + dropCourseNumber + ".");
            }
        }
        else {
            if (dropCourse == null) {
                System.out.println("Course " + dropCourseNumber + " not found.");
            }
            if (enrollCourse == null) {
                System.out.println("Course " + enrollCourseNumber + " not found.");
            }
        }

        // Display updated summary
        recordSummary();
    }

    // Method to display all the courses in the course list (7)
    // Arguments: none, Return: void
    private static void displayCourseList(){
        // Call display courses in CourseList
        courseList.displayCourses();
    }

    // Method to display all the students in a course (8)
    // Arguments: none, Return: void
    private static void displayStudentList(){
        // Prompt user for the course number
        System.out.print("Enter the course number: ");
        String courseNumber = scanner.nextLine();

        // Find the course in the course list
        CourseNode course = courseList.findCourse(courseNumber);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        // Call display students method in CourseNode
        course.displayStudents(courseNumber);
    }


}