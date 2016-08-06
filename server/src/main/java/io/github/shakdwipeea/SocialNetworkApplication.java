package io.github.shakdwipeea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.shakdwipeea.db.NeoManager;
import io.github.shakdwipeea.health.NeoHealthCheck;
import io.github.shakdwipeea.resources.SocialNetworkResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.neo4j.driver.v1.Driver;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class SocialNetworkApplication extends Application<SocialNetworkConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SocialNetworkApplication().run(args);
    }

    @Override
    public String getName() {
        return "Social Network";
    }

    @Override
    public void initialize(final Bootstrap<SocialNetworkConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final SocialNetworkConfiguration configuration,
                    final Environment environment) {

        // Neo4J Objects
        final Driver dbDriver = configuration.getGraphDB();
        final NeoManager neoManager = new NeoManager(dbDriver);
        final NeoHealthCheck neoCheck = new NeoHealthCheck(dbDriver);
        environment.lifecycle().manage(neoManager);

        // HealthChecks
        environment.healthChecks().register("neo4j", neoCheck);

        // Resources
        final SocialNetworkResource resource = new SocialNetworkResource(dbDriver);
        environment.jersey().register(resource);

        // enable CORS
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS",CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        //filter.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,Location");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "*");
    }

}
