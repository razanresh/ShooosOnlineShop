package com.example.shooosonlineshop.service.impl;

import com.example.shooosonlineshop.model.EmailDetails;
import com.example.shooosonlineshop.model.Order;
import com.example.shooosonlineshop.model.dto.OrderIntegrationDTO;
import com.example.shooosonlineshop.repository.OrderRepository;
import com.example.shooosonlineshop.service.EmailService;
import com.example.shooosonlineshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final EmailService emailService;
//    private final OrderIntegrationConfig integrationConfig;

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order saveOrder = orderRepository.save(order);
//        sendIntegrationNotify(saveOrder);
        sendMail(saveOrder);
    }

//    private void sendIntegrationNotify(Order order) {
//        OrderIntegrationDTO dto = new OrderIntegrationDTO();
//        dto.setUsername(order.getUser().getName());
//        dto.setAddress(order.getAddress());
//        dto.setOrderId(order.getId());
//        List<OrderIntegrationDTO.OrderDetailsDTO> details = order.getDetails().stream()
//                .map(OrderIntegrationDTO.OrderDetailsDTO::new).collect(Collectors.toList());
//        dto.setDetails(details);
//
//        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
//                .setHeader("Content-type", "application/json")
//                .build();
//
//        integrationConfig.getOrdersChannel().send(message);
//    }

    //  Отправка уведомления через email
    public void sendMail(Order order){
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(order.getUser().getEmail());
        emailDetails.setSubject("New order #" + order.getId());
        emailDetails.setMessageBody("Your order successfully created! Order's number: " + order.getId());

        emailService.sendSimpleMail(emailDetails);
    }
    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
