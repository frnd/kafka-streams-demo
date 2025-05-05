package com.example.kafka_streams_demo.topology;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TypeExcludeFilters(TopologyTypeExcludeFilter.class)
@Import(value = {
    TopologyTestDriverConfiguration.class})
@ActiveProfiles(value = {"test"})
public @interface TopologyTest {

  ComponentScan.Filter[] includeFilters() default {};

  @SuppressWarnings("unused")
  ComponentScan.Filter[] excludeFilters() default {};

}
