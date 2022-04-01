package Graph;

import java.util.Iterator;
import Main.ListInterface;

public interface ListWithIteratorInterface<T> extends ListInterface<T>,
        Iterable<T> {

    public Iterator<T> getIterator();
} // end ListWithIteratorInterface
