package io.github.shakdwipeea.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 6/20/16.
 */
public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public Graph() {
        //For jackson
    }


    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}
