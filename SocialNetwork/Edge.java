
import java.util.Objects;
public class Edge {
    private Vertex vertex1;
    private Vertex vertex2;

    // Arguments: two vertices
    // Returns: none
    // Desc: constructor for an edge using two vertices.
    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    // Arguments: object
    // Returns: boolean
    // Desc: returns true if the specified object is the same as the edge.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        // Ensure that the edge is undirected, so v1-v2 is the same as v2-v1
        return (Objects.equals(vertex1, edge.vertex1) && Objects.equals(vertex2, edge.vertex2)) ||
                (Objects.equals(vertex1, edge.vertex2) && Objects.equals(vertex2, edge.vertex1));
    }

    // Arguments: none
    // Returns: int
    // Desc: provides hash code to compare edges.
    @Override
    public int hashCode() {
        return Objects.hash(vertex1, vertex2) + Objects.hash(vertex2, vertex1);
    }
}
