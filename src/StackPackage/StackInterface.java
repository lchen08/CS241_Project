package StackPackage;

public interface StackInterface<T>
{
    void push(T newEntry);
    T pop();
    T peek();
    boolean isEmpty();
    void clear();
    char setResult(int result);
    int getResult();
}