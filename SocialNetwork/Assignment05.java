/*
    Author Name: Logan Dawes
    Email: logan.dawes@okstate.edu
    Date: 11/16/2024
    Program Description: Implements a University Social platform using adjacency list.
*/

import java.io.*;
import java.util.*;

public class Assignment05 {

    // Arguments: string args (unused)
    // Returns: none
    // Desc: main method asks for input file, parses it into the adjacency list, then opens menu.
    public static void main(String[] args) {
        Graph graph = new Graph();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the input file: ");
        String fileName = scanner.nextLine().trim();

        try {
            // Read the input file
            File file = new File(fileName);

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                if (line.startsWith("id")) {
                    continue;  // Skip the header line
                }

                if (!line.isEmpty()) {
                    // Parse the line to create a vertex and its edges
                    String[] parts = line.split("\t");
                    String id = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String college = parts[3];
                    String department = parts[4];
                    String email = parts[5];
                    String friendCount = parts[6];

                    Vertex vertex = new Vertex(id, firstName, lastName, college, department, email);
                    graph.insertVertex(vertex);

                    // Add edges for the friends if both exist
                    for (int i = 7; i < parts.length; i++) {
                        String friendId = parts[i];
                        Vertex friend = graph.findVertex(friendId);
                        if (friend != null) {
                            graph.insertEdge(vertex, friend);
                        }
                    }
                }
            }

            fileScanner.close();

            // Print success message and graph details
            System.out.println("Input file is read successfully..");
            System.out.println("Total number of vertices in the graph: " + graph.numVertices());
            System.out.println("Total number of edges in the graph: " + graph.numEdges());

            // Display menu for further operations
            displayMenu(graph);

        }
        catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please ensure the file exists in the same directory.");
        }
        catch (Exception e) {
            System.out.println("Error: An unexpected error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: displays menu options to execute methods on the graph.
    public static void displayMenu(Graph graph) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display menu options
            System.out.println("\n1. Remove friendship");
            System.out.println("2. Delete account");
            System.out.println("3. Count friends");
            System.out.println("4. Friends Circle");
            System.out.println("5. Closeness centrality");
            System.out.println("6. Exit");

            // Read user input
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (option) {
                case 1:
                    removeFriendship(graph);
                    break;
                case 2:
                    deleteAccount(graph);
                    break;
                case 3:
                    countFriends(graph);
                    break;
                case 4:
                    friendsCircle(graph);
                    break;
                case 5:
                    closenessCentrality(graph);
                    break;
                case 6:
                    scanner.close();
                    return; // Exit the loop and program
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: asks for two vertices as user input, removes edge between them.
    public static void removeFriendship(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first student ID or name: ");
        String input1 = scanner.nextLine().trim();
        System.out.print("Enter the second student ID or name: ");
        String input2 = scanner.nextLine().trim();

        // Find the vertices by ID or name
        Vertex v1 = graph.findVertex(input1);
        Vertex v2 = graph.findVertex(input2);

        // Detect missing vertices / edge
        if (v1 == null) {
            System.out.println("Sorry.. \n" + input1 + " not found!");
        }
        else if (v2 == null) {
            System.out.println("Sorry.. \n" + input2 + " not found!");
        }
        else if (!graph.getEdge(v1, v2)) {
            System.out.println("Sorry.. There is no edge between the vertices " + v1 + " and " + v2 + ".");
        }
        else {
            boolean removed = graph.removeEdge(v1, v2);
            if (removed) {
                System.out.println("The edge between the students " + v1 + " and " + v2 + " has been successfully removed.");
                System.out.println("Total number of students in the graph: " + graph.numVertices());
                System.out.println("Total number of edges in the graph: " + graph.numEdges());
            } else {
                // Debug message if removeEdge returns false
                System.out.println("DEBUG: Failed to remove the edge between " + v1 + " and " + v2 + ". The edge might not exist.");
            }
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: asks for vertex as user input, removes vertex from graph and connections.
    public static void deleteAccount(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the student's ID or first name
        System.out.print("Enter the student's ID or name: ");
        String input = scanner.nextLine().trim();

        Vertex vertexToRemove = graph.findVertex(input);

        if (vertexToRemove != null) {
            // Remove the vertex and all associated edges
            boolean vertexRemoved = graph.removeVertex(vertexToRemove);

            if (vertexRemoved) {
                System.out.println("The student " + vertexToRemove + " has been successfully removed..");
                System.out.println("Total number of vertices in the graph: " + graph.numVertices());
                System.out.println("Total number of edges in the graph: " + graph.numEdges());
            }
        }
        else {
            System.out.println("Sorry..");
            System.out.println(input + " not found!");
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: asks for vertex as user input, displays connections to that vertex.
    public static void countFriends(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the student's ID or first name
        System.out.print("Enter the student's ID or name: ");
        String studentInput = scanner.nextLine().trim();

        // Try to find the vertex in the graph
        Vertex vertex = graph.findVertex(studentInput);

        if (vertex != null) {
            // Get the friends of the vertex
            Set<Vertex> friends = graph.getAdjacentVertices(vertex); // Get the adjacent vertices

            // Display the friend count and the friends
            System.out.println("Friend count for " + vertex + ": " + friends.size());
            System.out.println("Friends of " + vertex + " are:");
            for (Vertex friend : friends) {
                System.out.println(friend); // Print each friend
            }
        }
        else {
            System.out.println("Sorry..");
            System.out.println(studentInput + " not found!");
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: asks for college as user input, displays vertices from that college and their connections using BFS.
    public static void friendsCircle(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the college name
        System.out.print("Enter the college name: ");
        String college = scanner.nextLine().trim();

        // Filter vertices by the given college
        Set<Vertex> allVertices = graph.vertices();
        Set<Vertex> collegeVertices = new HashSet<>();

        // Create a subset of vertices for that college
        for (Vertex vertex : allVertices) {
            if (vertex.getCollege().equalsIgnoreCase(college)) {
                collegeVertices.add(vertex);
            }
        }

        // Check for empty set
        if (collegeVertices.isEmpty()) {
            System.out.println("No students found from the college: " + college);
            return;
        }

        // Find connected components using BFS
        Set<Vertex> visited = new HashSet<>(); // track visited vertices
        List<List<Vertex>> connectedComponents = new ArrayList<>(); // List to store connected components

        for (Vertex vertex : collegeVertices) {
            if (!visited.contains(vertex)) {
                // Perform BFS for each unvisited vertex to find its connected component
                List<Vertex> component = graph.bfs(vertex, visited, college);
                connectedComponents.add(component);
            }
        }

        // Print the connected components
        System.out.println("Following are the friend circles in the College of " + college + ":");
        for (List<Vertex> component : connectedComponents) {
            for (int i = 0; i < component.size(); i++) {
                System.out.print(component.get(i)); // Print each vertex in the component
                if (i < component.size() - 1) {
                    System.out.print(" - "); // Add separator if this is not the last vertex in the component
                }
            }
            System.out.println();
        }
    }

    // Arguments: graph
    // Returns: none
    // Desc: asks for vertex as user input, calculate closeness centrality and normalized CC for that vertex.
    public static void closenessCentrality(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the vertex
        System.out.print("Enter the name or id of the student: ");
        String studentName = scanner.nextLine().trim();

        // Get vertex in graph by first name
        Vertex vertex = null;
        vertex = graph.findVertex(studentName);

        // Check for non-existent vertex
        if (vertex == null) {
            System.out.println("Sorry..");
            System.out.println(studentName + " not found!");
            return;
        }

        // Run Dijkstra's algorithm
        Map<Vertex, Integer> distances = graph.dijkstraShortestPaths(vertex);

        // Compute Closeness Centrality
        double totalDistance = 0;

        // Sum of 1/dij, or number of edges between two vertices
        for (Map.Entry<Vertex, Integer> entry : distances.entrySet()) {
            int dist = entry.getValue();
            // skips if distance is unreachable or equal
            if (dist != Integer.MAX_VALUE && !entry.getKey().equals(vertex)) {
                totalDistance += (1.0 / dist);
            }
        }

        // Normalized Closeness Centrality
        int totalVertices = graph.numVertices();
        double normalizedCC = totalDistance / (totalVertices - 1);

        // Output results
        System.out.printf("The Closeness Centrality for %s: %.2f\n", studentName, totalDistance);
        System.out.printf("The Normalized Closeness Centrality for %s: %.2f\n", studentName, normalizedCC);
    }

}
