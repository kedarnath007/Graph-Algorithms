
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Prims {

    static Map<Integer, Node> unvisitedmap;
    static Map<Integer, Integer> visitedmap;
    static HashMap<Integer, ArrayList<Edge>> input;
    static int parent;

    static void findPath() {

        unvisitedmap = new HashMap<Integer, Node>();// it will store min cost and neighbor
        visitedmap = new HashMap<Integer, Integer>();//it will store the neighbor having shortest distance to
        input = Graph.graph;

        Node node = new Node(3267, -1);
        for (int key : Graph.graph.keySet()) {
            unvisitedmap.put(key, node);
        }

        unvisitedmap.put(0, new Node(0, -1));

        parent = 0;
        while (!unvisitedmap.isEmpty()) {
            
            Entry<Integer, Node> min = Collections.min(unvisitedmap.entrySet(), new NodeComp());//nlogn
            parent = min.getKey();
            if (parent != 0) {
                visitedmap.put(parent, unvisitedmap.get(parent).getNeighbor());
            }
            unvisitedmap.remove(parent);

            for (Edge edge : input.get(parent)) {
                int v = (edge.sourceID == parent) ? edge.targetID : edge.sourceID;
                if (unvisitedmap.containsKey(v) && edge.weight < unvisitedmap.get(v).getMindist()) {
                    node = new Node(edge.weight, parent);
                    unvisitedmap.put(v, node);
                }
            }

        }
    }

}

class NodeComp implements Comparator<Entry<Integer, Node>> {

    @Override
    public int compare(Entry<Integer, Node> p, Entry<Integer, Node> q) {
        return p.getValue().getMindist().compareTo(q.getValue().getMindist());
    }
}

class Node {

    int mindist;
    int neighbor;

    public Node(int mindist, int neighbor) {
        this.mindist = mindist;
        this.neighbor = neighbor;

    }

    public Integer getMindist() {
        return mindist;
    }

    public void setMindist(int mindist) {
        this.mindist = mindist;
    }

    public int getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(int neighbor) {
        this.neighbor = neighbor;
    }
}
