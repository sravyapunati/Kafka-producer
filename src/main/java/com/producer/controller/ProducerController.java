package com.producer.controller;

import com.producer.dto.EmployeeDTO;
import com.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ProducerController.BASE_PATH,produces = "application/json")
public class ProducerController {

    public static final String BASE_PATH = "/v1/kafka";

    @Autowired
    private ProducerService service;

//    @GetMapping("/get/{message}")
//    public ResponseEntity<String> getMessage(@PathVariable String message){
//        service.sendMessageToKafka(message);
//        return ResponseEntity.ok().body("sentSucessfully");
//    }

    @GetMapping("/send")
    public ResponseEntity<String> sendJson(@RequestBody EmployeeDTO emp){
        for(int i=0;i<1000;i++){
            service.sendMessageToKafka(emp);
        }
        return ResponseEntity.ok().body("request sent Sucessfully");
    }
}
