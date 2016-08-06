package io.github.shakdwipeea.db;

import io.dropwizard.lifecycle.Managed;
import org.neo4j.driver.v1.Driver;

/**
 * Created by akash on 6/15/16.
 */
public class NeoManager implements Managed {

    private final Driver dbDriver;

    public NeoManager(Driver dbDriver) {
        this.dbDriver = dbDriver;
    }

    @Override
    public void start() throws Exception {
        // DB already started
    }

    @Override
    public void stop() throws Exception {
        dbDriver.close();
    }
}
