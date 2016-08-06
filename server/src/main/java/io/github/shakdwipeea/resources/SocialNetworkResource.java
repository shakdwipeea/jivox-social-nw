package io.github.shakdwipeea.resources;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import io.github.shakdwipeea.api.AddFriendRequest;
import io.github.shakdwipeea.core.Graph;
import io.github.shakdwipeea.core.JSONHelper;
import org.neo4j.driver.v1.*;


@Path("/")
@Produces("application/json")
@Consumes("application/json")
public class SocialNetworkResource {
    private final Driver db;

    public SocialNetworkResource(Driver dbDriver) {
        this.db = dbDriver;
    }

    /**
     *
     * @param request AddFriendRequest Contains details about node to be added
     * @return HTTP Response
     */
    @Path("add/friend")
    @POST
    public Response addFriends(@Valid AddFriendRequest request) {
        Session session = db.session();

        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName().toLowerCase());

        // TODO Improve query error handling

        // Possible race condition https://github.com/neo4j/neo4j/issues/5091
        try (Transaction tx = session.beginTransaction()){
            // create a node
            tx.run("MERGE (p {name: {name}})", params);

            // add friend
            for (String friend : request.getFriends()) {
                params.put("friend", friend.toLowerCase());
                System.out.println("Adding " + friend);

                tx.run("MATCH (p {name: {name}}) WITH p " +
                        "MERGE (q {name: {friend}}) " +
                        "MERGE (p)-[:CONNECT]->(q)",
                        params);
            }

            tx.success();
        }

        session.close();
        return Response.ok("Node added").build();
    }

    @Path("friends/{name}")
    @GET
    public Response getFriends(@PathParam("name") String name) {
        Session session = db.session();

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        String neoQuery = "MATCH p=({name: {name}})-[*]->() RETURN nodes(p) AS node, relationships(p) AS rels";
        StatementResult result = session.run(neoQuery, params);
        Graph graph = JSONHelper.getJSONGraph(result);

        session.close();
        return Response.ok(new Gson().toJson(graph)).build();
    }

}
