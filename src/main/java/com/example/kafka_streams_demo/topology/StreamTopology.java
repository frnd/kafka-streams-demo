package com.example.kafka_streams_demo.topology;

import java.util.Optional;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class StreamTopology {

  private final CodesRepository codesRepository;

  @Autowired
  public StreamTopology(CodesRepository codesRepository) {
    this.codesRepository = codesRepository;
  }

  @Autowired
  public void configure(StreamsBuilder builder) {
    final KStream<String, String> inputStream = builder.stream("input");

    inputStream
        .mapValues(codesRepository::findDescription)
        .filter(value(Optional::isPresent))
        .mapValues(Optional::get)
        .to("output");
  }

  <K, V> Predicate<? super K, ? super V> value(java.util.function.Predicate<V> valueConsumer) {
    return (_, value) -> valueConsumer.test(value);
  }

}
