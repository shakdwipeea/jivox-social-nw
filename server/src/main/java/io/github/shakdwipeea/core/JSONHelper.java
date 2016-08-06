package io.github.shakdwipeea.core;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

import jersey.repackaged.com.google.common.collect.Lists;
import org.neo4j.driver.v1.types.Relationship;

import java.util.Iterator;

/**
 * Created by akash on 6/20/16.
 */
public class JSONHelper {
    public static String NODE_KEY = "node";
    public static String REL_KEY = "rels";

    public static Graph getJSONGraph(StatementResult result) {
        Graph graph = new Graph();

        while (result.hasNext()) {
            Record row = result.next();


            if (row.get(NODE_KEY).values() != null) {
                // Retrieve all nodes
                Iterator<Value> nodeIter = row.get(NODE_KEY).values().iterator();
                while (nodeIter.hasNext()) {
                    Node node = new Node();

                    org.neo4j.driver.v1.types.Node nodeRep = nodeIter.next().asNode();
                    node.setLabels(Lists.newArrayList(nodeRep.labels()));
                    node.setId(nodeRep.id());
                    node.setName(nodeRep.get("name").asString());

                    if (node.getLabels().contains(NeoLabels.RESTAURANT)) {
                        node.setSize(1);
                    } else {
                        node.setSize(2);
                    }

                    if (!graph.getNodes().contains(node)) {
                        graph.addNode(node);
                    }
                }
            }



            if (row.get(REL_KEY).values() != null) {
                // Retrieve all edges
                Iterator<Value> relIter = row.get(REL_KEY).values().iterator();
                while (relIter.hasNext()) {
                    Edge edge = new Edge();

                    Relationship rel = relIter.next().asRelationship();
                    edge.setId(rel.id());
                    edge.setFrom(rel.startNodeId());
                    edge.setTo(rel.endNodeId());

                    if (!graph.getEdges().contains(edge)) {
                        graph.addEdge(edge);
                    }
                }
            }

        }

        return graph;
    }
}
