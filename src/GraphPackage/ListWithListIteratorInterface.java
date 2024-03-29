package GraphPackage;
import java.util.ListIterator;
/**
 An interface for the ADT list that has an iterator implementing
 the interface ListIterator.

 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.0
 */
public interface ListWithListIteratorInterface<T> extends Iterable<T>, ListInterface<T>
{
    public ListIterator<T> getIterator();
} // end ListWithListIteratorInterface
