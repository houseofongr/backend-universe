package com.hoo.universe.adapter.out.internal.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.universe.adapter.out.internal.message.file.DeleteFileEventKafkaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
@Configuration
public class InternalMessageConfig {

    @Bean
    public DeleteFileEventKafkaAdapter fileDeleteEventKafkaAdapter(
            KafkaTemplate<String, String> kafkaTemplate,
            InternalMessageMapper internalMessageMapper
    ) {
        return new DeleteFileEventKafkaAdapter(kafkaTemplate, internalMessageMapper);
    }

    @Bean
    public InternalMessageMapper internalMessageMapper(
            ObjectMapper internalMessageObjectMapper
    ) {
        return new InternalMessageMapper(internalMessageObjectMapper);
    }

    @Bean
    public ObjectMapper internalMessageObjectMapper() {
        return new ObjectMapper();
    }
}
