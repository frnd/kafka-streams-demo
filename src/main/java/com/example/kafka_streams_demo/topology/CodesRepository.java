package com.example.kafka_streams_demo.topology;

import static com.example.kafka_streams_demo.topology.GlobalTables.CODE_DESCRIPTION_STORE;

import java.util.Optional;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;
import org.springframework.stereotype.Repository;

@Repository
public class CodesRepository {

  private final KafkaStreamsInteractiveQueryService interactiveQueryService;

  @Autowired
  public CodesRepository(KafkaStreamsInteractiveQueryService interactiveQueryService) {
    this.interactiveQueryService = interactiveQueryService;
  }

  public Optional<String> findDescription(String value) {
    ReadOnlyKeyValueStore<String, String> codeStore = interactiveQueryService
        .retrieveQueryableStore(
            CODE_DESCRIPTION_STORE,
            QueryableStoreTypes.keyValueStore()
        );
    return Optional.ofNullable(value)
        .map(codeStore::get);
  }

}
