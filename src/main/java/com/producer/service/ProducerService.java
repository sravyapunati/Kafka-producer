package com.producer.service;

import com.producer.dto.EmployeeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProducerService {

    private static final Logger log = LogManager.getLogger(ProducerService.class);
    @Autowired
    private KafkaTemplate<String, EmployeeDTO> template;

//    public ProducerService(KafkaTemplate<String, EmployeeDTO> template){
//        this.template=template;
//    }

//    public void sendMessageToKafka(String message){
//        template.send("kafka_evt_enterprise",message);
//    }

    public void sendMessageToKafka(EmployeeDTO employeeDTO){

            CompletableFuture<SendResult<String, EmployeeDTO>> future= template.send("Java-Techie",employeeDTO);
            future.whenComplete((result,exception)->{
                if(exception!=null){
                    log.info(exception.getMessage());
                }else{
                    log.info("Message sent successfully to topic,{}"+result.getRecordMetadata().topic());
                    log.info("offset,{}"+result.getRecordMetadata().offset());
                    log.info("partition,{}"+result.getRecordMetadata().partition());
                }
            });
    }
}
