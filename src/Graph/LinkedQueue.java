package Graph;

public class LinkedQueue<T> implements QueueInterface<T> {

    private Node firstNode; // references node at front of queue
    private Node lastNode; // references node at back of queue

    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
    } // end default constructor

    @Override
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
    }

    @Override
    public T dequeue() {
        T front = null;
        if (!isEmpty()) {
            front = firstNode.getData();
            firstNode = firstNode.getNextNode();
            if (firstNode == null) {
                lastNode = null;
            }
        } // end if
        return front;
    } // end dequeue

    @Override
    public T getFront() {
        T front = null;
        if (!isEmpty()) {
            front = firstNode.getData();
        }
        return front;
    } // end getFront

    @Override
    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    } // end isEmpty

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
    } // end clear

    private class Node {

        private T data; // entry in queue
        private Node next; // link to next node

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNextNode() {
            return next;
        }

        public void setNextNode(Node next) {
            this.next = next;
        }

    } // end Node
} // end LinkedQueue
