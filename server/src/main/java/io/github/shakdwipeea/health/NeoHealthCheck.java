package io.github.shakdwipeea.health;

import com.codahale.metrics.health.HealthCheck;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

/**
 * Created by akash on 6/15/16.
 */
public class NeoHealthCheck extends HealthCheck {
    private final Driver db;

    public NeoHealthCheck(Driver db) {
        this.db = db;
    }

    @Override
    protected Result check() throws Exception {
        Session session = db.session();
        Result res = session != null ? Result.healthy() : Result.unhealthy("Session cannot be formed");
        session.close();
        return res;
    }
}
