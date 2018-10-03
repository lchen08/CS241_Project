package GraphPackage;

import QueuePackage.LinkedPriorityQueue;
import QueuePackage.LinkedQueue;
import QueuePackage.QueueInterface;
import StackPackage.ArrayStack;
import StackPackage.StackInterface;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates a directed graph where the vertices in the graph are connected by
 * directed edges.
 * Original author is professor Johnathan Johannsen with edits by Lisa Chen to complete missing
 * code as required per assignments.
 *
 * @author Lisa Chen
 * @since May 29, 2018
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
    protected HashMap<T, VertexInterface<T>> vertices;
    private int edgeCount;

    /**
     * Constructs an empty directed graph.
     */
    public DirectedGraph()
    {
        vertices = new HashMap<>();
        edgeCount = 0;
    }

    /**
     * Gets a queue of vertices' data where the vertices are traversed using breadth-first
     * from an origin vertex.
     * @param origin The origin vertex's data
     * @return Queue of vertices' data
     */
    public QueueInterface<T> getBreadthFirstTraversal(T origin)
    {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

        VertexInterface<T> originVertex = vertices.get(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originVertex);

        while (!vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while(neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                }
            }
        }
        return traversalOrder;
    }

    /**
     * Gets a queue of vertices' data where the vertices are traversed using depth-first
     * from an origin vertex.
     * @param origin The origin vertex's data
     * @return Queue of vertices' data
     */
    public QueueInterface<T> getDepthFirstTraversal(T origin)
    {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        StackInterface<VertexInterface<T>> vertexStack = new ArrayStack<>();

        VertexInterface<T> originVertex = vertices.get(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexStack.push(originVertex);
        while(!vertexStack.isEmpty())
        {
            VertexInterface<T> topVertex = vertexStack.peek();
            VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

            if (nextNeighbor != null)
            {
                nextNeighbor.visit();
                traversalOrder.enqueue(nextNeighbor.getLabel());
                vertexStack.push(nextNeighbor);
            }
            else
                vertexStack.pop();
        }
        return traversalOrder;
    }

    public StackInterface<T> getTopologicalOrder() { throw new UnsupportedOperationException(); }

    /**
     *
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @param path Stack to store path from beginning vertex to ending vertex for shortest path
     * @return The shortest, unweighted path length from beginning vertex to ending vertex
     */
    public int getShortestPath(T begin, T end, StackInterface<T> path)
    {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        originVertex.visit();
        vertexQueue.enqueue(originVertex);

        while (!done && !vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();

            while (!done && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                 if (!nextNeighbor.isVisited())
                 {
                     nextNeighbor.visit();
                     nextNeighbor.setCost(1 + frontVertex.getCost());
                     nextNeighbor.setPredecessor(frontVertex);
                     vertexQueue.enqueue(nextNeighbor);
                 }
                 if (nextNeighbor.equals(endVertex))
                 {
                     done = true;
                 }
            }
        }

        // Traversal ends; construct shortest path
        int pathLength = (int) endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathLength;
    }


    public boolean addVertex(T vertexLabel)
    {
        VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null;
    }

    /**
     * Finds the cheapest (least weights) path from a origin vertex to a ending vertex.
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @param path Stack to store path from beginning vertex to ending vertex for cheapest path
     * @return The cheapest (lowest weight) path length from beginning vertex to ending vertex
     */
    public int getCheapestPath(T begin, T end, StackInterface<T> path)
    {
        resetVertices();
        boolean done = false;
        LinkedPriorityQueue<EntryPQ> vertexQueue = new LinkedPriorityQueue<>();
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        vertexQueue.enqueue(new EntryPQ(originVertex,0, null));

        while (!(done || vertexQueue.isEmpty()))
        {
            EntryPQ frontEntry = vertexQueue.dequeue();
            VertexInterface<T> frontVertex = frontEntry.vertex;
            if (!frontVertex.isVisited())
            {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.weight);
                frontVertex.setPredecessor(frontEntry.prevEntry);
                if (frontVertex.equals(endVertex))
                    done = true;
                else
                {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> weights = frontVertex.getWeightIterator();

                    while (neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        double weightOfEdgeToNeighbor = weights.next();

                        if (!nextNeighbor.isVisited())
                        {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            vertexQueue.enqueue(new EntryPQ(nextNeighbor,nextCost,frontVertex));
                        }
                    }
                }
            }
        }

        // Traversal ends; construct shortest path
        int pathLength = (int) endVertex.getCost();
        if (pathLength >= 0)
            path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathLength;
    }

    /**
     * Adds a directed, weighted edge from the origin vertex and ending at the ending vertex.
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @param edgeWeight The real value of the edge's weight
     * @return True if an edge was successfully added; false otherwise.
     */
    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex, edgeWeight);
        if (result)
            edgeCount++;
        return result;
    }

    /**
     * Adds a directed, unweighted edge from the origin vertex and ending at the ending vertex.
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @return True if an edge was successfully added; false otherwise.
     */
    public boolean addEdge(T begin, T end) { return addEdge(begin, end, 0); }

    public boolean removeEdge(T begin, T end)
    {
        if (hasEdge(begin, end))
        {
            VertexInterface<T> beginVertex = vertices.get(begin);
            VertexInterface<T> endVertex = vertices.get(end);
            Iterator<VertexInterface<T>> beginNeighbors = beginVertex.getNeighborIterator();

            while (!beginNeighbors.next().equals(endVertex)) { } //loops to find right neighbor
            beginNeighbors.remove();
            return true;
        }
        else
            return false;
    }

    /**
     * Checks if there is an edge between the origin vertex and the ending vertex.
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object that labels the end vertex of the edge
     * @return True if the edge exists; false otherwise.
     */
    public boolean hasEdge(T begin, T end)
    {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if((beginVertex != null) && (endVertex != null))
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while(!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }
        }
        return found;
    }

    /**
     * Checks if the graph is empty (has any vertices/edges).
     * @return If the graph has any vertices/edges
     */
    public boolean isEmpty() { return vertices.isEmpty(); }

    /**
     * Retrieves the number of vertices in the graph.
     * @return Number of vertices in the graph
     */
    public int getNumberOfVertices() { return vertices.size(); }

    /**
     * Retrieves the number of edges in the graph.
     * @return Number of edges in the graph
     */
    public int getNumberOfEdges() { return edgeCount; }

    /** Clears the whole graph of all vertices and edges. */
    public void clear()
    {
        vertices = new HashMap<>();
        edgeCount = 0;
    }

    /** Resets the vertices' attributes to default state. */
    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.values().iterator();
        while(vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(-1);
            nextVertex.setPredecessor(null);
        }
    }

    /**
     * Gets a neighbor iterator of a vertex in the graph.
     * @param key The key to find the vertex in hashmap
     * @return The neighbor iterator of the requested vertex
     */
    public Iterator<VertexInterface<T>> getNeighborIterator(T key)
    {
        Vertex<T> vertex = (Vertex<T>) vertices.get(key);
        return vertex.getNeighborIterator();
    }

    /**
     * Gets a weight iterator of a vertex in the graph.
     * @param key The key to find the vertex in hash map
     * @return The weight iterator of the requested vertex
     */
    public Iterator<Double> getWeightIterator(T key)
    {
        Vertex<T> vertex = (Vertex<T>) vertices.get(key);
        return vertex.getWeightIterator();
    }

    /**
     * This is a class for a vertex entry in a vertex priority queue. It contains the vertex
     * object, the vertex that connects to it through a directed edge, and the weight of the edge.
     */
    private class EntryPQ implements Comparable<EntryPQ>
    {
        VertexInterface<T> vertex;
        double weight;
        VertexInterface<T> prevEntry;

        /**
         * Constructs a vertex entry for a vertex priority queue.
         * @param vertex The vertex object from a graph
         * @param weight The weight of the edge between the vertex and the previous vertex
         * @param prevEntry The previous vertex that connects to the vertex with a directed edge
         */
        private EntryPQ(VertexInterface vertex, double weight, VertexInterface prevEntry)
        {
            this.vertex = vertex;
            this.weight = weight;
            this.prevEntry = prevEntry;
        }

        /**
         * Compares this object to another object to see which object is greater.
         * @param otherEntry the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         */
        @Override
        public int compareTo(EntryPQ otherEntry)
        {
            return (int) (this.weight - otherEntry.weight);
        }
    }
}
