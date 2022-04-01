package Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Main.AList;
import Main.ListInterface;

public class DirectedGraph<T> implements GraphInterface<T> {

    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new LinkedDictionary<T, VertexInterface<T>>();
        edgeCount = 0;
    } // end default constructor

    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> isDuplicate
                = vertices.add(vertexLabel, new Vertex(vertexLabel));
        return isDuplicate == null; // was add to dictionary successful?
    }

    public void removeVertex(T vertexLabel) {
        vertices.remove(vertexLabel);
    }

    public List<T> getAllVertexes() {
        List<T> keys = new ArrayList<>();
        Iterator<T> keyIterator = vertices.getKeyIterator();
        while (keyIterator.hasNext()) {
            T next = keyIterator.next();
            keys.add(next);
        }
        return keys;
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        }
        if (result) {
            edgeCount++;
        }
        return result;
    }

    @Override
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }

    public void removeEdge(T begin, T end) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            beginVertex.disconnect(endVertex);
        }
        if (result) {
            edgeCount--;
        }
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors
                    = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    found = true;
                }
            } // end while
        } // end if
        return found;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.getSize();
    }

    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    } // end clear

    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<T>();
        QueueInterface<VertexInterface<T>> vertexQueue
                = new LinkedQueue<VertexInterface<T>>();
        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin); // enqueue vertex label
        vertexQueue.enqueue(originVertex); // enqueue vertex
        while (!vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors
                    = frontVertex.getNeighborIterator();
            while (neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
            } // end while
        } // end while
        return traversalOrder;
    }

    @Override
    public QueueInterface<T> getDepthFirstTraversal(T origin) {
        return null;
    }

    @Override
    public StackInterface<T> getTopologicalOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue
                = new LinkedQueue<VertexInterface<T>>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();
// Assertion: resetVertices() has executed setCost(0)
// and setPredecessor(null) for originVertex
        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors
                    = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
                if (nextNeighbor.equals(endVertex)) {
                    done = true;
                }
            } // end while
        } // end while
// traversal ends; construct shortest path
        int pathLength = (int) endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while
        return pathLength;
    } // end getShortestPath

    @Override
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        return 0;
    }

    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    public ListInterface<T> getNeighbors(T t) {
        VertexInterface<T> value = vertices.getValue(t);
        ListInterface<T> list = new AList<>();
        if (value != null) {
            Iterator<VertexInterface<T>> neighborIterator = value.getNeighborIterator();
            while (neighborIterator.hasNext()) {
                VertexInterface<T> next = neighborIterator.next();
                list.add(next.getLabel());
            }
        }

        return list;
    }
} // end DirectedGraph
