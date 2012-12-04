package backend;
import java.awt.event.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class EditQueue implements BlockingQueue{
    /**
     * The EditQueue implements blocking queue, and is used to queue the edit messages
     * being sent by different clients.
     * 
     * Processes the messages sequentially.
     * Used such that no two messages will be processed at the same time and multiple versions
     * of the document would not be created. 
     * 
     * >>Testing Strategy<<
     *  Send multiple messages from multiple clients in a specified order, and see if the messages
     *  are processed in order. 
     *  Send many many many messages and see if the queue ever gets full.  
     *
     */

    /**
     * Constructor for EditQueue
     * @param length
     */
    public EditQueue(int length) {
        // TODO: finish this constructor so it makes a queue of 
        // length 100 like the BlockingQueue constructor
        
    }
    
    @Override
    public Object element() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object peek() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object poll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object remove() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addAll(Collection arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean containsAll(Collection arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray(Object[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int drainTo(Collection arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int drainTo(Collection arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean offer(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean offer(Object arg0, long arg1, TimeUnit arg2)
            throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object poll(long arg0, TimeUnit arg1) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void put(Object arg0) throws InterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int remainingCapacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean remove(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object take() throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

}
