/*
    Author Name: Logan Dawes
    Email: logan.dawes@okstate.edu
    Date: 11/1/2024
    Program Description: models a heart transplant waiting list using a priority queue. The priority queue is in terms of binary heap using an array.
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Main class to run the waiting list menu program
public class Assignment04 {
    private static BinaryHeap priorityQueue = new BinaryHeap();
    private static String inputFile;

    // Arguments: command-line argument of type string for file name
    // Returns: none
    // Desc: main method executes on program ran. Gets inputFile then shows menu
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Assignment04 <inputFile>");
            return;
        }

        inputFile = args[0];  // Store the input file name in a static variable
        showMenu();           // Display the menu
    }

    // Arguments: none
    // Returns: none
    // Desc: Prints menu for different program options
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. insert");
            System.out.println("2. peek");
            System.out.println("3. nextPatient");
            System.out.println("4. removePatient");
            System.out.println("5. size");
            System.out.println("6. updatePriority");
            System.out.println("7. exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    insert();
                    break;
                case 2:
                    peek();
                    break;
                case 3:
                    nextPatient();
                    break;
                case 4:
                    removePatient();
                    break;
                case 5:
                    size();
                    break;
                case 6:
                    updatePriority();
                    break;
                case 7:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }

    // Arguments: none
    // Returns: none
    // Desc: Parses input file into records and inserts them into priority queue
    public static void insert() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");

                if (fields.length != 13) {
                    System.err.println("Invalid record format: " + line);
                    continue;
                }

                // Constructs record from line
                Record record = new Record(
                        fields[0], fields[1], fields[2], fields[3], fields[4],
                        fields[5], fields[6], fields[7], fields[8], fields[9],
                        fields[10], fields[11], fields[12]
                );

                // Inserts record into priority queue
                priorityQueue.insert(record);
            }
            System.out.println("Records successfully inserted into the priority queue.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        showMenu();
    }

    // Arguments: none
    // Returns: none
    // Desc: calls min() on priority queue to get highest priority record (root)
    public static void peek() {
        Record highestPriorityRecord = priorityQueue.min();

        if (highestPriorityRecord == null) {
            System.out.println("The priority queue is empty.");
        } else {
            System.out.println("The patient detail with the highest priority is as follows:");
            System.out.println(highestPriorityRecord);
        }
        showMenu();
    }

    // Arguments: none
    // Returns: none
    // Desc: calls removeMin() to remove and return highest priority record
    public static void nextPatient() {
        Record removedRecord = priorityQueue.removeMin();

        if (removedRecord == null) {
            System.out.println("The priority queue is empty. No patient to remove.");
        } else {
            System.out.println("The patient removed from the heap is as follows:");
            System.out.println(removedRecord);
        }

        showMenu();
    }

    // Arguments: none
    // Returns: Record
    // Desc: used to prompt user for record information, used for removePatient() and updatePriority()
    private static Record gatherPatientInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter patient information:");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Date of birth: ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("County: ");
        String county = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Zip code: ");
        String zip = scanner.nextLine();
        System.out.print("Phone number (1st Preference): ");
        String phone1 = scanner.nextLine();
        System.out.print("Phone number (2nd Preference): ");
        String phone2 = scanner.nextLine();
        System.out.print("Email address: ");
        String email = scanner.nextLine();
        System.out.print("UNOS Status: ");
        String UNOSStatus = scanner.nextLine();

        // Create and return the Record object
        return new Record(firstName, lastName, address, city, county, state, zip, phone1, phone2, email, "", UNOSStatus, dateOfBirth);
    }


    // Arguments: none
    // Returns: none
    // Desc: calls remove() on priority queue using target record from user prompts
    public static void removePatient() {
        Record targetRecord = gatherPatientInfo();

        boolean found = priorityQueue.remove(targetRecord);

        if (found) {
            System.out.println("The requested patient’s record has been removed from the queue.");
        } else {
            System.out.println("The requested patient’s record is not found.");
        }

        showMenu();
    }

    // Arguments: none
    // Returns: none
    // Desc: calls size on priority queue
    public static void size() {
        int numberOfRecords = priorityQueue.size(); // Get the size from the priority queue
        System.out.println("Number of records in the database: " + numberOfRecords);

        showMenu();
    }

    // Arguments: none
    // Returns: none
    // Desc: updates specified record's UNOSStatus based on user input
    public static void updatePriority() {
        Record targetRecord = gatherPatientInfo(); // Gather patient information

        String newUNOSStatus = targetRecord.getUNOSStatus();

        // Try to update the record in the priority queue
        boolean updated = false;
        for (int i = 0; i < priorityQueue.size(); i++) {
            Record currentRecord = priorityQueue.getRecord(i);

            // Check if the current record matches the target record (excluding UNOS status)
            if (currentRecord.equals(targetRecord)) {
                currentRecord.setUNOSStatus(newUNOSStatus); // Update the UNOS status
                updated = true;
                priorityQueue.downheap(i); // Restore heap order if necessary
                System.out.println("Updated record:");
                System.out.println(currentRecord); // Print updated record
                break;
            }
        }

        if (!updated) {
            System.out.println("No matching patient record found to update.");
        }

        showMenu();
    }

    // Arguments: none
    // Returns: none
    // Desc: exits program
    private static void exit(){
        System.exit(0);
    }
}
