package com.producer.eventProducer;

import com.producer.dto.EmployeeDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic("Java-Techie", 3, (short) 1);
    }

    @Bean
    public ProducerFactory<String, EmployeeDTO> producerConfig() {
        Map<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        prop.put(ProducerConfig.ACKS_CONFIG, "all");

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, EmployeeDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerConfig());
    }

    @Bean
    public ConsumerFactory<String, EmployeeDTO> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "evt-grp-1");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Important for deserialization
//        JsonDeserializer<EmployeeDTO> deserializer = new JsonDeserializer<>(EmployeeDTO.class);
//        deserializer.addTrustedPackages("*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,false);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(EmployeeDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeDTO> consume() {
        ConcurrentKafkaListenerContainerFactory<String, EmployeeDTO> fact = new ConcurrentKafkaListenerContainerFactory<>();
        fact.setConsumerFactory(consumerConfig());
        return fact;
    }
}
