package com.thecookiezen.streamapi.control;

import com.thecookiezen.streamapi.entity.StreamData;
import pl.setblack.airomem.core.WriteChecker;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Storage implements Readable, Writable, Serializable {

    private Map<Long, StreamData> store = new ConcurrentHashMap<>();

    @Override
    public void add(StreamData data) {
        assert WriteChecker.hasPrevalanceContext();
        this.store.put(new Random().nextLong(), data);
    }

    @Override
    public StreamData getById(long id) {
        return store.get(id);
    }

    @Override
    public Collection<StreamData> getAll() {
        return this.store.values();
    }
}
