/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.Iterator;
import Main.AList;
import Main.ListInterface;
import Main.Profile;


public class UndirectedGraph<T> extends DirectedGraph<T> {

    public boolean addEdge(T begin, T end, double edgeWeight) {
        return super.addEdge(begin, end, edgeWeight) && super.addEdge(end, begin, edgeWeight);

    }

    public void removeEdge(T begin, T end) {
        super.removeEdge(begin, end);
        super.removeEdge(end, begin);
    }
}
