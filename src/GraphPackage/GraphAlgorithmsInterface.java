package GraphPackage;
import QueuePackage.*;
import StackPackage.*;

public interface GraphAlgorithmsInterface<T>
{
    QueueInterface<T> getBreadthFirstTraversal(T origin);
    QueueInterface<T> getDepthFirstTraversal(T origin);
    StackInterface<T> getTopologicalOrder();
    int getShortestPath(T begin, T end, StackInterface<T> path);
    int getCheapestPath(T begin, T end, StackInterface<T> path);
}
