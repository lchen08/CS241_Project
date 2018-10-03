package UnitTests;

import GraphPackage.DirectedGraph;
import GraphPackage.GraphInterface;
import QueuePackage.QueueInterface;
import StackPackage.ArrayStack;
import StackPackage.StackInterface;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is a unit test to check the functionality of the methods of Vertex.java and
 * DirectedGraph.java. The
 * graph used is the example shown in class for directed, weighted graphs.
 *
 * @author Lisa Chen
 * @since May 20, 2018
 */
public class InClassExTest
{
    public static void main(String[] args)
    {
        GraphInterface<String> map = new DirectedGraph<>();
        map.addVertex("A");
        map.addVertex("B");
        map.addVertex("C");
        map.addVertex("D");
        map.addVertex("E");
        map.addVertex("F");
        map.addVertex("G");
        map.addVertex("H");
        map.addVertex("I");
        map.addEdge("A", "B", 2);
        map.addEdge("A", "D", 5);
        map.addEdge("A", "E", 4);
        map.addEdge("B", "E", 1);
        map.addEdge("C", "B", 3);
        map.addEdge("D", "G", 2);
        map.addEdge("E", "F", 3);
        map.addEdge("E", "H", 6);
        map.addEdge("F", "C", 4);
        map.addEdge("F", "H", 3);
        map.addEdge("G", "H", 1);
        map.addEdge("H", "I", 1);
        map.addEdge("I", "F",1);

        QueueInterface<String> queue1 = map.getBreadthFirstTraversal("A");
        while(!queue1.isEmpty())
        {
            System.out.print(queue1.dequeue() + " ");
        }
        System.out.println("\n");

        QueueInterface<String> queue2 = map.getDepthFirstTraversal("A");
        while(!queue2.isEmpty())
            System.out.print(queue2.dequeue() + " ");
        System.out.println("\n");

        StackInterface<String> unweightedPath = new ArrayStack<>();
        String beginVertex = "A";
        String endVertex = "H";
        int pathLength = map.getShortestPath(beginVertex, endVertex, unweightedPath);

        System.out.println("Unweighted Path length from " + beginVertex + " to " +
                endVertex + ": " + pathLength);
        System.out.print("Unweighted shortest path: ");
        while (!unweightedPath.isEmpty())
            System.out.print(unweightedPath.pop() + " ");
        System.out.println("\n");

        StackInterface<String> weightedPath = new ArrayStack<>();
        int weightedPathLength = map.getCheapestPath(beginVertex, endVertex, weightedPath);

        System.out.println("Weighted Path length from " + beginVertex + " to " +
                endVertex + ": " + weightedPathLength);
        System.out.print("Weighted shortest path: ");
        while (!weightedPath.isEmpty())
            System.out.print(weightedPath.pop() + " ");
        System.out.println("\n");
    }
}
