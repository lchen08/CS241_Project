package UnitTests;

import CityMapPackage.CityGraph;
import QueuePackage.QueueInterface;
import StackPackage.ArrayStack;
import StackPackage.StackInterface;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is a unit test to check the functionality of the methods of CityGraph.java and
 * CityVertex.java.
 *
 * @author Lisa Chen
 * @since May 20, 2018
 */
public class CityMapTest
{
    private static final String CITY_FILE = "city.dat";
    private static final String ROAD_FILE = "road.dat";
    public static void main(String[] args) throws FileNotFoundException
    {
        File cityFile = new File(CITY_FILE);
        File roadFile = new File(ROAD_FILE);
        CityGraph creator = new CityGraph(cityFile,roadFile);

        QueueInterface<Integer> breadthQueue = creator.getBreadthFirstTraversal(1);
        System.out.println("Breadth First Traversal from 1:");
        while(!breadthQueue.isEmpty())
        {

            System.out.print(breadthQueue.dequeue() + " ");
        }
        System.out.println("\n");

        QueueInterface<Integer> depthQueue = creator.getDepthFirstTraversal(1);
        System.out.println("Depth First Traversal from 1:");
        while(!depthQueue.isEmpty())
        {
            System.out.print(depthQueue.dequeue() + " ");
        }
        System.out.println("\n");

        int beginVertex = 1;
        int endVertex = 2;
        StackInterface<Integer> path = new ArrayStack<>();
        int weightedPathLength = creator.getCheapestPath(beginVertex, endVertex, path);

        System.out.println("Weighted Path length from " + beginVertex + " to " +
                endVertex + ": " + weightedPathLength);
        System.out.print("Weighted shortest path: ");
        while (!path.isEmpty())
            System.out.print(path.pop() + " ");
        System.out.println("\n");

//        for (int i = 1; i < creator.getNumberOfVertices(); i++)
//        {
//            System.out.println("City Code: " + creator.getCityCode(i));
//            System.out.println("City Full Name: " + creator.getFullCityName(i));
//            System.out.printf("City Population: %.0f%n", creator.getPopulation(i));
//            System.out.println("City Elevation: " + creator.getElevation(i));
//            System.out.println();
//        }

        System.out.println("Able to add edge between 1 and 19? (false): " +
                creator.addEdge(1, 19));
        System.out.println("Able to remove edge between 20 and 1? (false): " +
                creator.removeEdge(20,1));
        System.out.println("Able to remove edge between 1 and 19? (true): " +
                creator.removeEdge(1,19));
        System.out.println("Able to remove edge between 2 and 19? (true): " +
                creator.removeEdge(2,19));
        System.out.println("Able to remove edge between 2 and 19? (false): " +
                creator.removeEdge(2,19));
        System.out.println("Able to remove edge between 12 and 19? (true): " +
                creator.removeEdge(12,19));

        StackInterface<Integer> path2 = new ArrayStack<>();
        int weightedPathLength2 = creator.getCheapestPath(1,19, path2);
        System.out.println("Weighted Path length from " + 1 + " to " + 19 + "(should be -1): " +
                weightedPathLength2);
        System.out.print("Weighted shortest path: ");
        while (!path2.isEmpty())
            System.out.print(path2.pop() + " ");
        System.out.println("\n");


        System.out.println("Able to add edge between 1 and 19? (true): " + creator.addEdge(1, 19));

        StackInterface<Integer> path3 = new ArrayStack<>();
        int weightedPathLength3 = creator.getCheapestPath(1,19, path3);
        System.out.println("Weighted Path length from " + 1 + " to " + 19 + "(should be 0): " +
                weightedPathLength3);
        System.out.print("Weighted shortest path: ");
        while (!path3.isEmpty())
            System.out.print(path3.pop() + " ");
        System.out.println("\n");
    }
}
