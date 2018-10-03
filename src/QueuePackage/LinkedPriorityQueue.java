package QueuePackage;

import java.util.EmptyStackException;

public class LinkedPriorityQueue<T extends Comparable<T>> implements PriorityQueueInterface<T>
{
    private int numEntries;
    private Node firstNode;
    private Node lastNode;

    public LinkedPriorityQueue()
    {
        numEntries = 0;
        firstNode = null;
        lastNode = null;
    }
    /**
     * Queues the entries in order of priority. Highest priority has the smallest weight.
     * @param newEntry Entry to add
     */
    public void enqueue(T newEntry)
    {
        if (isEmpty())
        {
            firstNode = new Node(newEntry, null);
            lastNode = firstNode;
        }
        else if(newEntry.compareTo(firstNode.data) < 0)
        {
            Node newNode = new Node(newEntry,firstNode);
            firstNode = newNode;
        }
        else
        {
            Node ptrNode = firstNode;
            while (ptrNode.next != null && newEntry.compareTo(ptrNode.next.data) >= 0)
            {
                ptrNode = ptrNode.next;
            }
            Node newNode = new Node(newEntry, ptrNode.next);
            ptrNode.next = newNode;
        }
        numEntries++;
    }

    public T dequeue()
    {
        T result = getFront();
        if (result == null)
            throw new EmptyStackException();
        firstNode.data = null;
        firstNode = firstNode.next;
        if (firstNode == null)
            lastNode = null;
        numEntries--;

        return result;
    }

    public T getFront()
    {
        if(isEmpty())
            throw new EmptyStackException();
        return firstNode.data;
    }

    public T getBack()
    {
        if(isEmpty())
            throw new EmptyStackException();
        return lastNode.data;
    }

    public boolean isEmpty() { return numEntries == 0; }

    public void clear()
    {
        firstNode = null;
        lastNode = null;
        numEntries = 0;

    }

    public int getNumEntries() { return numEntries; }

    private class Node
    {
        private T data;
        private Node next;

        private Node(T data, Node next)
        {
            this.next = next;
            this.data = data;
        }
    }
}
