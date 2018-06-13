package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/consumer")
public class ConsumerEndPoint {
    private ConsumerService consumerService;

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
    @RequestMapping(value = "/get-by-consumer-id/{consumerId}",method = RequestMethod.GET)
    public ResponseEntity<Consumer> create(@PathVariable String consumerId){
        return ResponseEntity.ok(this.consumerService.getByConsumerId(consumerId));
    }
}