package jpcamara.da.jaxen;

import java.util.Iterator;

public class DataAreaElementIterator implements Iterator<DataAreaElement> {
    private DataAreaElement parent;
    private Iterator<DataAreaElement> iterator;
    
    public DataAreaElementIterator(DataAreaElement parent, Iterator<DataAreaElement> iterator) {
        this.parent = parent;
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public DataAreaElement next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
