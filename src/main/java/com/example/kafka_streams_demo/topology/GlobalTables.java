package com.example.kafka_streams_demo.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalTables {

  public static final String CODE_DESCRIPTION_STORE = "code-description-store";

  public GlobalTables() {
  }

  @Autowired
  public void globalTable(StreamsBuilder builder) {
    builder.globalTable(
        "codes",
        Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as(CODE_DESCRIPTION_STORE)
            .withKeySerde(Serdes.String())
            .withValueSerde(Serdes.String()));
  }
}
