package io.github.shakdwipeea;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SocialNetworkConfiguration extends Configuration {

    @Valid
    @NotNull
    private String dbPath;

    public Driver getGraphDB() {
        return GraphDatabase.driver("bolt://social-network-db", AuthTokens.basic("neo4j", "root"));
    }

    @JsonProperty("dbPath")
    public String getDBPath() {
        return dbPath;
    }

    @JsonProperty("dbPath")
    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
}
