// Defines an individual record in the queue
public class Record {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String phone1;
    private String phone2;
    private String email;
    private String dateListed;
    private String UNOSStatus;    // Primary priority
    private String dateOfBirth;   // Secondary priority

    // Arguments: none
    // Returns: none
    // Desc: Constructor for a record using all specified columns
    public Record(String firstName, String lastName, String address, String city, String county,
                  String state, String zip, String phone1, String phone2, String email,
                  String dateListed, String UNOSStatus, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zip = zip;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.dateListed = dateListed;
        this.UNOSStatus = UNOSStatus;
        this.dateOfBirth = dateOfBirth;
    }

    // Arguments: none
    // Returns: String
    // Desc: getter method for record's UNOSStatus
    public String getUNOSStatus() { return UNOSStatus; }
    // Arguments: String
    // Returns: none
    // Desc: setter method for record's UNOSStatus
    public void setUNOSStatus(String UNOSStatus) { this.UNOSStatus = UNOSStatus; }

    // Arguments: none
    // Returns: none
    // Desc: Comparison method based on primary and secondary priorities
    public int compareTo(Record other) {
        int thisPriority = getUNOSPriority(this.UNOSStatus);
        int otherPriority = getUNOSPriority(other.UNOSStatus);

        int primaryComparison = Integer.compare(thisPriority, otherPriority);
        if (primaryComparison != 0) {
            return primaryComparison; // Higher priority UNOSStatus first
        }
        // Secondary priority: compare age based on dateOfBirth
        return compareDates(other.dateOfBirth, this.dateOfBirth);
    }

    // Arguments: String
    // Returns: int
    // Desc: Method to map UNOS status to priority using switch case
    private int getUNOSPriority(String status) {
        switch (status) {
            case "Status 1A": return 1; // Highest priority
            case "Status 1B": return 2;
            case "Status 2": return 3;
            case "Status 7": return 4; // Lowest priority
            default: return Integer.MAX_VALUE; // Any unknown status will have the lowest priority
        }
    }

    // Arguments: two Strings
    // Returns: int
    // Desc: Method to compare two dates in MM/dd/yyyy format
    private int compareDates(String date1, String date2) {
        String[] parts1 = date1.split("/");
        String[] parts2 = date2.split("/");

        int month1 = Integer.parseInt(parts1[0]);
        int day1 = Integer.parseInt(parts1[1]);
        int year1 = Integer.parseInt(parts1[2]);

        int month2 = Integer.parseInt(parts2[0]);
        int day2 = Integer.parseInt(parts2[1]);
        int year2 = Integer.parseInt(parts2[2]);

        // Compare years first
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }
        // Compare months if years are equal
        if (month1 != month2) {
            return Integer.compare(month1, month2);
        }
        // Compare days if years and months are equal
        return Integer.compare(day1, day2);
    }

    // Arguments: Record
    // Returns: boolean
    // Desc: equals method to see if two records are equal
    public boolean equals(Record other) {
        if (other == null) return false; // Check for null
        // Compare names
        return (firstName.equals(other.firstName)) && (lastName.equals(other.lastName));
    }

    // Arguments: none
    // Returns: String
    // Desc: Overrides java string method toString(), so that records are printed in this format by default
    @Override
    public String toString() {
        return "Patient’s first name: " + firstName + "\n" +
                "Patient’s last name: " + lastName + "\n" +
                "Date of birth of the patient: " + dateOfBirth + "\n" +
                "Address: " + address + "\n" +
                "City: " + city + "\n" +
                "County: " + county + "\n" +
                "State: " + state + "\n" +
                "Zip code: " + zip + "\n" +
                "Phone Number (1st Preference): " + phone1 + "\n" +
                "Phone Number (2nd Preference): " + phone2 + "\n" +
                "Email address: " + email + "\n" +
                "UNOS Status: " + UNOSStatus + "\n" +
                "Date listed on " + UNOSStatus + ": " + dateListed;
    }
}

