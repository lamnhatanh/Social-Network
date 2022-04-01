package Main;

import Graph.QueueInterface;
import Graph.UndirectedGraph;
import java.util.ArrayList;
import java.util.List;

public class Connection {

    private UndirectedGraph<Profile> graph;

    public Connection() {
        graph = new UndirectedGraph<>();
    }

    public boolean join(Profile person) {
        return graph.addVertex(person);
    }

    public void leave(Profile person) {
        unFriendAll(person);
        graph.removeVertex(person);
    }

    public Profile search(String name) {
        for (Profile person : graph.getAllVertexes()) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    public List<Profile> searchAll(String name) {
        List<Profile> result = new ArrayList<>();
        for (Profile person : graph.getAllVertexes()) {
            if (person.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(person);
            }
        }
        return result;
    }

    public List<Profile> getPeople() {
        return graph.getAllVertexes();
    }

    public boolean isFriend(Profile person, Profile friend) {
        return graph.hasEdge(person, friend);
    }

    public void addFriendship(Profile person, Profile search) {
        graph.addEdge(person, search);
    }

    public ListInterface<Profile> getFriendsFriend(Profile person) {
        ListInterface<Profile> friendsFriendList = new AList<>();
        if (graph.getAllVertexes().contains(person)) {
            QueueInterface<Profile> breadthFirstTraversal = graph.getBreadthFirstTraversal(person);
            while (!breadthFirstTraversal.isEmpty()) {
                Profile dequeue = breadthFirstTraversal.dequeue();
                if (!dequeue.equals(person) && !getNeighbors(person).contains(dequeue)) {
                    friendsFriendList.add(dequeue);
                }
            }

        }

        return friendsFriendList;
    }

    public ListInterface<Profile> getNeighbors(Profile person) {
        return graph.getNeighbors(person);
    }

    public void unFriendAll(Profile person) {
        ListInterface<Profile> neighbors = graph.getNeighbors(person);
        for (int i = 1; i <= neighbors.getLength(); i++) {
            unFriend(neighbors.getEntry(i), person);
        }
    }

    public void unFriend(Profile person, Profile friend) {
        graph.removeEdge(person, friend);
    }

}
