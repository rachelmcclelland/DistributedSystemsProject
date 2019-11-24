package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import io.dropwizard.setup.Bootstrap;

public class UserApiApplication extends Application<UserApiConfig> {

    @Override
    public void initialize(Bootstrap<UserApiConfig> b) {
    }

    public static void main(String[] args) throws Exception{
        new UserApiApplication().run(args);
    }

    public void run(UserApiConfig userApiConfig, Environment environment) throws Exception {

        final UserApiResource resource = new UserApiResource(environment.getValidator());
        final LoginAPIResource resource1 = new LoginAPIResource(environment.getValidator());
        final HealthChecker healthCheck = new HealthChecker();

        environment.jersey().register(resource);

        // used for /login  url
        environment.jersey().register(resource1);

        environment.healthChecks().register("health", healthCheck);
    }
}
