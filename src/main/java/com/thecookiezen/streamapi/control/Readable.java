package com.thecookiezen.streamapi.control;

import com.thecookiezen.streamapi.entity.StreamData;

import java.util.Collection;

public interface Readable {
    StreamData getById(long id);

    Collection<StreamData> getAll();
}
