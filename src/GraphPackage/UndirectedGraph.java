package GraphPackage;

public class UndirectedGraph<T> implements BasicGraphInterface<T>
{
    @Override
    public boolean addVertex(T vertexLabel)
    {
        return false;
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        return false;
    }

    @Override
    public boolean addEdge(T begin, T end)
    {
        return false;
    }

    @Override
    public boolean hasEdge(T begin, T end)
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public int getNumberOfVertices()
    {
        return 0;
    }

    @Override
    public int getNumberOfEdges()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }
}
