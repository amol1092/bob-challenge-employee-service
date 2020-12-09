package com.takeaway.challenge.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.takeaway.challenge.kafka.events.EmployeeEvent;

@Configuration
public class KafkaProducerConfig {
	
	@Value(value =  "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Value(value = "${spring.kafka.producer.value-serializer}")
	private String valueSerializerConfig;

    @Bean
    public ProducerFactory<String, EmployeeEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrapAddress);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          valueSerializerConfig);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, EmployeeEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
