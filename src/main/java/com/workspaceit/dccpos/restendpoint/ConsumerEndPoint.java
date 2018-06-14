package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.service.ConsumerService;
import com.workspaceit.dccpos.validation.form.consumer.ConsumerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/consumer")
@CrossOrigin
public class ConsumerEndPoint {
    private ConsumerService consumerService;

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
    @RequestMapping(value = "/get-all",method = RequestMethod.GET)
    public ResponseEntity<List<Consumer>> getAll(){
        return ResponseEntity.ok(this.consumerService.getAll());
    }
    @RequestMapping(value = "/get-by-consumer-id/{consumerId}",method = RequestMethod.GET)
    public ResponseEntity<Consumer> create(@PathVariable String consumerId){
        return ResponseEntity.ok(this.consumerService.getByConsumerId(consumerId));
    }
    @RequestMapping(value = "/get-new-consumer-form",method = RequestMethod.GET)
    public ResponseEntity<ConsumerForm> create(){
        return ResponseEntity.ok(this.consumerService.requestNewConsumerForm());
    }


}