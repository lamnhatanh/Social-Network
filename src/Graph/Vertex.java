package Graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Vertex<T> implements VertexInterface<T> {

    private T label;
    private ListWithIteratorInterface<Edge> edgeList; // edges to
// neighbors
    private boolean visited; // true if visited
    private VertexInterface<T> previousVertex; // on path to this vertex
    private double cost; // of path to this vertex

    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new LinkedListWithIterator<Edge>();
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {
        visited = true;
    }

    @Override
    public void unvisit() {
        visited = false;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) { // vertices are distinct
            Iterator<VertexInterface<T>> neighbors = this.getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    duplicateEdge = true;
                }
            } // end while
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if
        return result;
    }

    @Override
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }

    public void disconnect(VertexInterface<T> endVertex) {
        Iterator<VertexInterface<T>> neighbors = this.getNeighborIterator();
        int index = 0;
        while (neighbors.hasNext()) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (endVertex.equals(nextNeighbor)) {
                edgeList.remove(index + 1);
            }
            index++;
        } // end while
    }

    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new neighborIterator();
    }

    @Override
    public Iterator<Double> getWeightIterator() {
        return null;
    }

    @Override
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }

    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null)) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited()) {
                result = nextNeighbor;
            }
        } // end while
        return result;
    } // end getUnvisitedNeighbor

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {
        cost = newCost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    protected class Edge {

        private VertexInterface<T> vertex; // end vertex
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor

        protected VertexInterface<T> getEndVertex() {
            return vertex;
        } // end getEndVertex

        protected double getWeight() {
            return weight;
        } // end getWeight
    } // end Edge

    private class neighborIterator implements Iterator<VertexInterface<T>> {

        private Iterator<Edge> edges;

        private neighborIterator() {
            edges = edgeList.getIterator();
        } // end default constructor

        public boolean hasNext() {
            return edges.hasNext();
        } // end hasNext

        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            } else {
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end neighborIterator

    public boolean equals(Object other) {
        boolean result;
        if ((other == null) || (getClass() != other.getClass())) {
            result = false;
        } else {
            Vertex<T> otherVertex = (Vertex<T>) other;
            result = label.equals(otherVertex.label);
        } // end if
        return result;
    } // end equals
} // end Vertex
