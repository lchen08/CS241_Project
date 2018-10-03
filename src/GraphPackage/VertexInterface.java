package GraphPackage;

import java.util.Iterator;

public interface VertexInterface<T>
{

    /** Gets this vertex's label
     * @return The object that labels the vertex. */
    T getLabel();

    /** Marks this vertex as visited */
    void visit();

    /** Marks this vertex as unvisited */
    void unvisit();

    /** Sees whether this vertex is marked as visited
     * @return True if the vertex has been visited. */
    boolean isVisited();

    /** Connects this vertex and a given vertex with a weighted edge. The
     * two vertices cannot be the same, and must not already have this edge
     * between them. In a directed graph, the edge points toward the given vertex
     * @param endVertex A vertex in the graph that ends the edge
     * @param edgeWeight A real-valued edge weight, if any
     * @return True if the edge is added, false if not. */
    boolean connect(VertexInterface<T> endVertex, double edgeWeight);

    /** Connects this vertex and a given vertex with a weighted edge. The
     * two vertices cannot be the same, and must not already have this edge
     * between them. In a directed graph, the edge points toward the given vertex
     * @param endVertex A vertex in the graph that ends the edge
     * @return True if the edge is added, false if not. */
    boolean connect(VertexInterface<T> endVertex);

    /** Creates an iterator of this vertex's neighbors by following
     * all edges that begin at this vertex
     * @return An iterator of the neighboring vertices of this vertex */
    Iterator<VertexInterface<T>> getNeighborIterator();


    /** Creates an iterator of the weights of the edges to this vertex's neighbors
     * @return An iterator of the neighboring vertices of this vertex */
    Iterator<Double> getWeightIterator();

    /** Sees whether this vertex has at least one neighbor
     * @return True if the vertex has a neighbor */
    boolean hasNeighbor();

    /** Gets an unvisited neighbor, if any, of this vertex
     * @return Either a vertex that is an unvisited neighbor or null of none exists */
    VertexInterface<T> getUnvisitedNeighbor();

    /** Gets the recorded predecessor of this vertex
     * @return Either this vertex's predecessor or null of none is recorded */
    VertexInterface<T> getPredecessor();

    /** Sees whether a predecessor was recorded for this vertex
     * @return True if a predecessor was recorded. */
    boolean hasPredecessor();

    /** Records the cost of a path to this vertex
     * @param newCost The cost of the path */
    public void setCost(double newCost);

    void setPredecessor(VertexInterface<T> newPredecessor);

    /** Gets the recorded cost of the path to this vertex
     * @return The cost of the path */
    double getCost();
}
