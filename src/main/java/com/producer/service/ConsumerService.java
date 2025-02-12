package com.producer.service;

import com.producer.dto.EmployeeDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger log = LogManager.getLogger(ConsumerService.class);
    public ConsumerService(){
        log.info("Application started kafka");
    }
//    @KafkaListener(topics = "kafka_evt_enterprise", groupId = "evt-grp-1")
//    public void listen(String message) {
//        System.out.println("message"+message);
//        log.info("Message consumed: {}", message);
//        log.debug(message);
//    }

    //for example we should not do same thing in real time
    @KafkaListener(topics = "Java-Techie", groupId = "evt-grp-2",containerFactory = "consume")
    public void listen1(EmployeeDTO emp) {
        log.info("Message consumed C1   : {}", emp);
    }

    @KafkaListener(topics = "Java-Techie", groupId = "evt-grp-2", containerFactory = "consume")
    public void listen2(EmployeeDTO emp){
        log.info("Message consumed C2   : {}", emp);
    }

    @KafkaListener(topics = "Java-Techie", groupId = "evt-grp-2", containerFactory = "consume")
    public void listen3(EmployeeDTO emp){
        log.info("Message consumed C3   : {}", emp);
    }

}

