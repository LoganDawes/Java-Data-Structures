/*
    Author Name: Logan Dawes
    Email: logan.dawes@okstate.edu
    Date: 10/16/2024
    Program Description:
    Checks whether a given HTML file, given as .txt file, is created given the correct tag rules using the stack.
    If the file does have the correct tag rules, it will output confirmation.
    If the file does not have the correct tag rules, it will display which tag in which line is causing the problem.
*/
// Imported for BufferedReader and FileReader
import java.io.*;

public class Assignment03 {
    // Main method: accepts filename as command-line argument
    // Reads HTML file line by line
    // Uses Stack<Tag> to manage opening and closing tags
    public static void main(String[] args) {
        // Check if correct number of arguments provided
        if (args.length != 1) {
            System.out.println("Usage: java Assignment03 <filename>");
            return;
        }

        // input filename provided as argument
        String filename = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Stack to store opening tags
            Stack<Tag> tagStack = new Stack<>(100);
            // Stores each line of the HTML file
            String line;
            // Line counter to track line numbers
            int lineNumber = 0;

            // Reads file line by line
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim(); // Removes leading/trailing whitespace

                // Ignore comment lines
                if (line.startsWith("<!--") && line.endsWith("-->")) {
                    continue;
                }

                // Ignore <!DOCTYPE html> declaration
                if (line.startsWith("<!") && line.endsWith(">")) {
                    continue;
                }

                // Process the line for HTML tags
                processTagsInLine(line, lineNumber, tagStack);
            }

            // Check if any unmatched tags remain in the stack
            if (!tagStack.isEmptyStack()) {
                Tag unmatchedTag = tagStack.peek(); // Gets last unmatched tag
                System.out.println("Oops... There is a problem..");
                System.out.println("The <" + unmatchedTag.name + "> tag at line # " + unmatchedTag.lineNumber + " does not meet the tag rules..");
            }
            // No unmatched tags in stack, document meets tag rules
            else {
                System.out.println("Congratulations...");
                System.out.println("The given HTML file meets all the tag rules.");
            }
            // Exceptions caught by FileReader or Stack
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace for debugging purposes
        }
    }

    // Function to process HTML tags in a single line
    // Args: line, lineNumber, tagStack, Returns: none
    private static void processTagsInLine(String line, int lineNumber, Stack<Tag> tagStack) throws Exception {
        int index = 0;
        // Find all tags enclosed in < and >
        while (index < line.length()) {
            // Position of opening <
            int openTagStart = line.indexOf('<', index);
            if (openTagStart == -1) break;

            // Position of closing >
            int openTagEnd = line.indexOf('>', openTagStart);
            if (openTagEnd == -1) break;

            // Extracts tag content inside < and >
            String tagContent = line.substring(openTagStart + 1, openTagEnd).trim();
            boolean isClosingTag = tagContent.startsWith("/"); // Checks if this is a closing tag
            boolean isSelfClosing = tagContent.equals("br") || tagContent.equals("hr");

            if (isSelfClosing) {
                // Skip self-closing tags like <br> and <hr>
                index = openTagEnd + 1;
                continue;
            }

            // Process the closing tag
            if (isClosingTag) {
                String closingTagName = tagContent.substring(1).split(" ")[0]; // Tag name without '/'

                // If either the stack is empty or the tag on the top of the stack doesn't match, it is an error
                if (tagStack.isEmptyStack() || !tagStack.peek().name.equals(closingTagName)) {
                    System.out.println("Oops... There is a problem..");
                    System.out.println("The </" + closingTagName + "> tag at line # " + lineNumber + " does not meet the tag rules..");
                    System.exit(1);
                } else {
                    tagStack.pop();  // Closing tag matches the opening tag, so pop it from the stack
                }
            }
            // Process opening tag
            else {
                String openingTagName = tagContent.split(" ")[0]; // Tag name
                tagStack.push(new Tag(openingTagName, lineNumber)); // Push onto stack
            }

            // Next character after >
            index = openTagEnd + 1;
        }
    }
}

// Simple helper class to represent an HTML tag
// Used to manage tag names and line number where tag appears in the case of an error
class Tag {
    String name;
    int lineNumber;

    public Tag(String name, int lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }
}

