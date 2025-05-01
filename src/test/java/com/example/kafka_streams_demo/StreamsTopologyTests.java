package com.example.kafka_streams_demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.REGEX;

import com.example.kafka_streams_demo.topology.TopologyTest;
import java.util.List;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@TopologyTest(
    includeFilters = {
        @ComponentScan.Filter(type = REGEX, pattern = {
            "com.example.kafka_streams_demo.topology.*"})})
public class StreamsTopologyTests {

  @Autowired
  private TopologyTestDriver driver;

  private TestInputTopic<String, String> input;
  private TestInputTopic<String, String> codes;
  private TestOutputTopic<String, String> output;

  @BeforeEach
  @SuppressWarnings("all")
  void setup() {
    input = driver.createInputTopic(
        "input",
        Serdes.String().serializer(),
        Serdes.String().serializer());
    codes = driver.createInputTopic(
        "codes",
        Serdes.String().serializer(),
        Serdes.String().serializer());
    output = driver.createOutputTopic(
        "output",
        Serdes.String().deserializer(),
        Serdes.String().deserializer());
  }

  @Test
  public void shouldMapCodesToValues() {
    // given
    codes.pipeInput("a", "A");
    codes.pipeInput("b", "B");

    // when
    input.pipeInput("key-1", "a");
    input.pipeInput("key-2", "b");
    input.pipeInput("key-3", "c");
    input.pipeInput("key-1", "a");
    input.pipeInput("key-2", "b");
    input.pipeInput("key-3", "c");

    // then
    final List<String> records = output.readValuesToList();

    assertThat(records)
        .containsExactly(
            "A",
            "B",
            "A",
            "B"
        );
  }

}
