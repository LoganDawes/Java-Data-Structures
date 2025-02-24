
import java.util.Set;
public interface GraphADT {
    int numVertices();
    int numEdges();
    Set<Vertex> vertices();
    boolean getEdge(Vertex v1, Vertex v2);
    boolean insertVertex(Vertex V);
    boolean insertEdge(Vertex v1, Vertex v2);
    boolean removeVertex(Vertex v);
    boolean removeEdge(Vertex v1, Vertex v2);
}
