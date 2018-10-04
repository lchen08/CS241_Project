package GraphPackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates a vertex for a graph. Each vertex can be connected in a graph through an edge.
 * Original author is professor Johnathan Johannsen with edits by Lisa Chen to complete the 
 * missing code as required by the assignment.
 *
 * @author Lisa Chen
 * @since May 24, 2018
 */
public class Vertex<T> implements VertexInterface<T>
{
    private T label;
    private ListWithListIteratorInterface<Edge> edgeList; // list of edges that starting at this vertex
    private boolean visited;                              // True if visited
    private VertexInterface<T> previousVertex;            // For use when constructing a path
    private double cost;

    public Vertex(T vertexLabel)
    {
        label = vertexLabel;
        edgeList = new ArrayListWithListIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }

    public T getLabel() { return label; }

    public void visit() { visited = true; }

    public void unvisit() { visited = false; }

    public boolean isVisited() { return visited; }

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
    {
        boolean result = false;
        if (!this.equals(endVertex))
        {
            // 'this' and endVertex are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;

            while (!duplicateEdge && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            }

            if (!duplicateEdge)
            {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }

    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex, 0);
    }

    //my original way to address recording weights
//    public double getEdgeWeight(VertexInterface<T> endVertex)
//    {
//        double weight = -1;
//        boolean foundEdge = false;
//        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
//        while (neighbors.hasNext() && !foundEdge)
//        {
//            VertexInterface<T> nextNeighbor = neighbors.next();
//            if (endVertex.equals(nextNeighbor))
//            {
//                weight = ((NeighborIterator) neighbors).getNextEdgeWeight();
//                foundEdge = true;
//            }
//        }
//        return weight;
//    }

    public Iterator<VertexInterface<T>> getNeighborIterator() { return new NeighborIterator(); }

    public Iterator<Double> getWeightIterator() { return new WeightIterator(); }

    public boolean hasNeighbor() { return !edgeList.isEmpty(); }

    public VertexInterface<T> getUnvisitedNeighbor()
    {
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null))
        {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = nextNeighbor;
        }
        return result;
    }

    public void setPredecessor(VertexInterface<T> newPredecessor) { previousVertex = newPredecessor; }

    public VertexInterface<T> getPredecessor() { return previousVertex; }

    public boolean hasPredecessor() { return previousVertex != null; }

    public void setCost(double newCost) { cost = newCost; }

    public double getCost() { return cost; }

    public boolean equals(Object other)
    {
        boolean result;

        if((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {
            // The cast is safe within this else clause
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>) other;
            result = label.equals(otherVertex.label);
        }
        return result;
    }

    private class NeighborIterator implements Iterator<VertexInterface<T>>
    {
        private Iterator<Edge> edges;
        private boolean wasNextCalled;
//        private Edge edgeToNextNeighbor; //my original way to address recording weights

        private NeighborIterator()
        {
            edges = edgeList.getIterator();
            wasNextCalled = false;
//            edgeToNextNeighbor = null; //my original way to address recording weights
        }

        public boolean hasNext()
        {
            return edges.hasNext();
        }

        public VertexInterface<T> next()
        {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext())
            {
//                edgeToNextNeighbor = edges.next(); //my original way to address recording weights
                nextNeighbor = edges.next().getEndVertex();
                wasNextCalled = true;
            }
            else
            {
                wasNextCalled = false;
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        }

//        /**
//         * Retrieves the weight of the edge of the neighbor just passed through next();
//         * @return Weight of the edge
//         */
//        //my original way to address recording weights
//        public double getNextEdgeWeight() { return edgeToNextNeighbor.getWeight(); }

        /**
         * Removes a neighbor of a vertex.
         */
        public void remove()
        {
            if (wasNextCalled)
                edges.remove(); //removing an edge removes the neighbor
        }
    }

    /** This is a class for an iterator for the weights of the edges in relation to NeighborIterator. */
    private class WeightIterator implements Iterator<Double>
    {
        private Iterator<Edge> edges;

        private WeightIterator() { edges = edgeList.getIterator(); }

        public boolean hasNext() { return edges.hasNext(); }

        public Double next()
        {
            Double edgeWeight;
            if (edges.hasNext())
            {
                Edge edgeToNextNeighbor = edges.next();
                edgeWeight = edgeToNextNeighbor.getWeight();
            }
            else
                throw new NoSuchElementException();

            return edgeWeight;
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }

    protected class Edge
    {
        private VertexInterface<T> vertex; // Vertex at end of edge
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight)
        {
            vertex = endVertex;
            weight = edgeWeight;
        }

        protected Edge(VertexInterface<T> endVertex)
        {
            vertex = endVertex;
            weight = 0;
        }

        protected double getWeight() { return weight; }

        protected VertexInterface<T> getEndVertex() { return vertex; }
    }
}
