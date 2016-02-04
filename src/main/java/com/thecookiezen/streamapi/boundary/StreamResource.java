package com.thecookiezen.streamapi.boundary;

import com.codahale.metrics.annotation.Timed;
import com.thecookiezen.streamapi.control.Readable;
import com.thecookiezen.streamapi.entity.StreamData;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.data.DataRoot;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/stream")
@Produces(MediaType.APPLICATION_JSON)
public class StreamResource {

    private final PersistenceController<DataRoot<Readable, Readable>, Readable> controllerStorage;

    public StreamResource(PersistenceController<DataRoot<Readable, Readable>, Readable> controllerStorage) {
        this.controllerStorage = controllerStorage;
    }

    @GET
    @Timed
    public Collection<StreamData> getAll() {
        return controllerStorage.query(Readable::getAll);
    }

    @GET
    @Path("/{id}")
    @Timed
    public StreamData getById(@PathParam("id") long id) {
        return controllerStorage.query(view -> view.getById(id));
    }
}
