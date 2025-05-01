package com.example.kafka_streams_demo.topology;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;

@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
public class TopologyTestDriverConfiguration {

  @Bean
  StreamsBuilder streamsBuilder(
      ObjectProvider<SslBundles> sslBundles,
      KafkaProperties kafkaProperties) {
    var streamsProperties = kafkaProperties.buildStreamsProperties(sslBundles.getIfAvailable());
    var streamsConfig = new StreamsConfig(streamsProperties);
    var topologyConfig = new TopologyConfig(streamsConfig);
    return new StreamsBuilder(topologyConfig);
  }

  @Bean
  Topology topology(
      StreamsBuilder streamsBuilder,
      ObjectProvider<KafkaStreamsInfrastructureCustomizer> infrastructureCustomizer) {
    infrastructureCustomizer.forEach(cust -> cust.configureBuilder(streamsBuilder));

    var topology = streamsBuilder.build();
    System.out.println("topology = " + topology.describe().toString());

    infrastructureCustomizer.forEach(cust -> cust.configureTopology(topology));

    return topology;
  }

  @Bean
  TopologyTestDriver defaultTopologyTestDriver(
      ObjectProvider<SslBundles> sslBundles,
      KafkaProperties kafkaProperties,
      Topology topology
  ) {
    var streamsProperties = kafkaProperties.buildStreamsProperties(sslBundles.getIfAvailable());
    var properties = new Properties();
    properties.putAll(streamsProperties);
    return new TopologyTestDriver(topology, properties);
  }

  @Bean
  public KafkaStreamsInteractiveQueryService kafkaStreamsInteractiveQueryService(
      ObjectProvider<TopologyTestDriver> topologyTestDriverProvider) {

    final KafkaStreamsInteractiveQueryService mock =
        mock(KafkaStreamsInteractiveQueryService.class);

    when(mock.retrieveQueryableStore(any(), any()))
        .then(invocation -> {
          final String store = invocation.getArgument(0, String.class);
          final TopologyTestDriver topologyTestDriver = topologyTestDriverProvider.getObject();
          return topologyTestDriver.getKeyValueStore(store);
        });

    return mock;
  }

}
