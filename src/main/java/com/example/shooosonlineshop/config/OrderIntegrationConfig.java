//package com.example.shooosonlineshop.config;
//
//import org.mapstruct.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
//import org.springframework.integration.channel.DirectChannel;
//
//@Configuration
//@ImportResource("classpath:/integration/http-orders-integration.xml")
//public class OrderIntegrationConfig {
//
//    private final DirectChannel ordersChannel;
//
//    public OrderIntegrationConfig(@Qualifier("ordersChannel") DirectChannel ordersChannel) {
//        this.ordersChannel = ordersChannel;
//    }
//
//    public DirectChannel getOrdersChannel(){
//        return ordersChannel;
//    }
//
//}
