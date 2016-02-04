package com.thecookiezen.streamapi.control;

import com.thecookiezen.streamapi.entity.StreamData;

public interface Writable {
    void add(StreamData data);
}
