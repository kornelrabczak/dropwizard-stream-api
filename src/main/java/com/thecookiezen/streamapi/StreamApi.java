package com.thecookiezen.streamapi;

import com.thecookiezen.streamapi.boundary.StreamResource;
import com.thecookiezen.streamapi.control.Storage;
import com.thecookiezen.streamapi.control.StreamProvider;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PersistenceFactory;
import pl.setblack.airomem.data.DataRoot;

public class StreamApi extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new StreamApi().run(args);
    }

    @Override
    public String getName() {
        return "StreamApi";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {
        PersistenceController controllerStorage = new PersistenceFactory().initOptional("streamApiStorage", () -> new DataRoot<>(new Storage()));

        final StreamResource resource = new StreamResource(controllerStorage);

        new StreamProvider(configuration, controllerStorage).run();

        environment.jersey().register(resource);
    }
}
