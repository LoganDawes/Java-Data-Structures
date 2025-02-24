import java.util.*;

public class Graph implements GraphADT {
    private Map<Vertex, Set<Vertex>> adjacencyList;
    private Set<Edge> edgeSet;

    // Arguments: none
    // Returns: none
    // Desc: constructor for graph
    public Graph() {
        adjacencyList = new HashMap<>();
        edgeSet = new HashSet<>();
    }

    // Arguments: none
    // Returns: int
    // Desc: returns size of adjacency list
    @Override
    public int numVertices() {
        return adjacencyList.size();
    }

    // Arguments: none
    // Returns: int
    // Desc: returns number of edges in the graph
    @Override
    public int numEdges() {
        return edgeSet.size();
    }

    // Arguments: none
    // Returns: set of vertices
    // Desc: returns all of the vertices in the adjacency list
    @Override
    public Set<Vertex> vertices() {
        return adjacencyList.keySet();
    }

    // Arguments: string
    // Returns: vertex
    // Desc: returns a vertex with the given name (or ID) from the graph.
    public Vertex findVertex(String input) {
        for (Vertex v : vertices()) {
            // Checks vertex's ID or first name, ignores case
            if (v.getId().equalsIgnoreCase(input) ||
                    v.getStudentsFirstName().equalsIgnoreCase(input)) {
                return v;
            }
        }
        return null; // Vertex not found
    }

    // Arguments: vertex
    // Returns: set of vertices
    // Desc: returns all of the connected vertices to a specified vertex.
    public Set<Vertex> getAdjacentVertices(Vertex v) {
        if (adjacencyList.containsKey(v)) {
            return adjacencyList.get(v); // Return the set of adjacent vertices
        }
        return new HashSet<>(); // Return an empty set if the vertex does not exist
    }

    // Arguments: two vertices
    // Returns: boolean
    // Desc: returns true if there is an edge between the two specified vertices.
    @Override
    public boolean getEdge(Vertex v1, Vertex v2) {
        if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
            return false; // Both vertices don't exist
        }
        return adjacencyList.get(v1).contains(v2);  // Returns if vertex 1 has vertex 2 in connections set
    }

    // Arguments: vertex
    // Returns: boolean
    // Desc: adds a vertex to the graph, returns completion status.
    @Override
    public boolean insertVertex(Vertex v) {
        if (adjacencyList.containsKey(v)) {
            return false; // Vertex already exists
        }
        adjacencyList.put(v, new HashSet<>());  // puts a new vertex in adjacency list with empty connections set
        return true;
    }

    // Arguments: two vertices
    // Returns: boolean
    // Desc: establishes an edge between the two specified vertices, returns completion status.
    @Override
    public boolean insertEdge(Vertex v1, Vertex v2) {
        if (v1.equals(v2)) {
            return false; // No self-loops allowed
        }
        if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
            return false; // Both vertices must exist
        }
        Edge edge = new Edge(v1, v2);
        if (edgeSet.contains(edge)) {
            return false; // No duplicate edges
        }
        // Add vertex to each vertex's connected vertices list
        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1);

        // Add edge to set of edges
        edgeSet.add(edge);

        // Increment friend count for each
        v1.incrementFriendCount();
        v2.incrementFriendCount();

        return true;
    }

    // Arguments: vertex
    // Returns: boolean
    // Desc: removes a vertex from the graph and connections, returns completion status.
    @Override
    public boolean removeVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            return false;   // Vertex must exist
        }
        // Remove all edges involving the vertex
        Set<Vertex> adjacentVertices = adjacencyList.get(v);
        for (Vertex adjacent : adjacentVertices) {
            adjacencyList.get(adjacent).remove(v); // Remove vertex from adjacent's connections
            edgeSet.remove(new Edge(v, adjacent)); // Remove edge from edgeSet
            adjacent.decrementFriendCount(); // Decrement adjacent's friend count
        }
        adjacencyList.remove(v); // Remove vertex itself
        return true;
    }

    // Arguments: two vertices
    // Returns: boolean
    // Desc: removes an edge between two specified vertices, returns completion status.
    @Override
    public boolean removeEdge(Vertex v1, Vertex v2) {
        if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
            return false;
        }
        Edge edge = new Edge(v1, v2);
        if (!edgeSet.contains(edge)) {
            return false; // Edge doesn't exist
        }
        // Remove vertex from each vertex's connected vertices list
        adjacencyList.get(v1).remove(v2);
        adjacencyList.get(v2).remove(v1);

        // Remove edge from set of edges
        edgeSet.remove(edge);

        // Decrement friend count from each
        v1.decrementFriendCount();
        v2.decrementFriendCount();

        return true;
    }

    // Arguments: starting vertex, set of already visited vertices, string college
    // Returns: list of vertices
    // Desc: breath first search, keeps queue and propagates through the vertices in a graph and returns connected components.
    public List<Vertex> bfs(Vertex start, Set<Vertex> visited, String college) {
        List<Vertex> component = new ArrayList<>(); // List of vertices belonging to a connected component
        Queue<Vertex> queue = new LinkedList<>(); // Queue for traversal

        // Start at start vertex, mark as visited
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            component.add(current);

            // Add unvisited neighbors in the same college to the queue
            for (Vertex neighbor : getAdjacentVertices(current)) {
                if (!visited.contains(neighbor) && neighbor.getCollege().equalsIgnoreCase(college)) {
                    // marks as visited, enqueue for traversal
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Return the list of vertices in the connected component
        return component;
    }

    // Arguments: source vertex
    // Returns: map of vertices with distance int
    // Desc: Dijkstra's algorithm, calculates the shortest paths from the source vertex to all other vertices
    public Map<Vertex, Integer> dijkstraShortestPaths(Vertex source) {
        Map<Vertex, Integer> distances = new HashMap<>(); // shortest distances from source to each vertex
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // priority queue to select min distance, comparing for distances map
        Set<Vertex> visited = new HashSet<>(); // keep track of visited vertices

        // Initialize distances
        for (Vertex v : vertices()) {
            distances.put(v, Integer.MAX_VALUE);
        }
        distances.put(source, 0); // distance from source to source will be 0

        pq.add(source);

        // Process priority queue for distances map
        while (!pq.isEmpty()) {
            Vertex current = pq.poll();

            // Skip if already visited
            if (visited.contains(current)) {
                continue;
            }

            // Mark as visited
            visited.add(current);

            // Update distances for adjacent vertices
            for (Vertex neighbor : getAdjacentVertices(current)) {
                int newDist = distances.get(current) + 1;

                // Updates distance in distances map
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    // add neighboring vertex to process
                    pq.add(neighbor);
                }
            }
        }
        // Return the map of distances from the source vertex
        return distances;
    }
}
