package com.thecookiezen.streamapi.control;

import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremill.react.kafka.ConsumerProperties;
import com.softwaremill.react.kafka.ReactiveKafka;
import com.thecookiezen.streamapi.ApplicationConfiguration;
import com.thecookiezen.streamapi.entity.StreamData;
import kafka.message.MessageAndMetadata;
import org.reactivestreams.Publisher;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.data.DataRoot;

public class StreamProvider {

    private final Publisher<MessageAndMetadata<byte[], String>> publisher;
    private final ActorMaterializer materializer;
    private final ObjectMapper mapper;
    private final PersistenceController<DataRoot<Writable, Writable>, Writable> controllerStorage;

    public StreamProvider(ApplicationConfiguration configuration, PersistenceController<DataRoot<Writable, Writable>, Writable> controllerStorage) {
        ReactiveKafka kafka = new ReactiveKafka();
        final ConsumerProperties<String> build = configuration.build();
        publisher = kafka.consume(build, configuration.getSystem());
        materializer = ActorMaterializer.create(configuration.getSystem());
        mapper = new ObjectMapper();
        this.controllerStorage = controllerStorage;
    }

    public void run() {
        Source
                .from(publisher)
                .map(m -> mapper.readValue(m.message(), StreamData.class))
                .to(Sink.foreach(streamData -> controllerStorage.execute(v -> v.getDataObject().add(streamData))))
                .run(materializer);
    }
}
