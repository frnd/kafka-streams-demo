package com.example.kafka_streams_demo.topology;

import com.example.kafka_streams_demo.config.KafkaConfig;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

public class TopologyTypeExcludeFilter extends
    StandardAnnotationCustomizableTypeExcludeFilter<TopologyTest> {

  private final List<String> EXCLUSION_LIST = Stream.of(KafkaConfig.class)
      .map(Class::getName)
      .toList();

  protected TopologyTypeExcludeFilter(Class<TopologyTest> testClass) {
    super(testClass);
  }

  @Override
  protected boolean exclude(MetadataReader metadataReader,
      MetadataReaderFactory metadataReaderFactory) throws IOException {
    return isInExclusionList(metadataReader) || super.exclude(metadataReader,
        metadataReaderFactory);
  }

  private boolean isInExclusionList(MetadataReader metadataReader) {
    return EXCLUSION_LIST.contains(metadataReader.getClassMetadata().getClassName());
  }

}
