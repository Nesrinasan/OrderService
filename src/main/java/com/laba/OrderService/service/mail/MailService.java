package com.laba.OrderService.service.mail;

import com.laba.OrderService.dto.UserInfoResponseDto;
import com.laba.OrderService.dto.kafka.MailDto;
import com.laba.OrderService.entity.Order;
import com.laba.OrderService.service.kafka.KafkaMailService;
import com.laba.OrderService.service.ReplaceFunction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MailService {

    private final KafkaMailService kafkaService;

    public MailService(KafkaMailService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Async
    public void sendMailUser(Order order, UserInfoResponseDto users) {

        ReplaceFunction replaceFunction = (template, name, orderNumber)  -> template.replace("NAME", name).replace("ORDERNUMBER", orderNumber);

        String orderNumber = order.getOrderNumber();
        String name = users.name();
        String email = users.email();

        String mailBody = null;
        if(StringUtils.hasText(orderNumber)){
            mailBody = "sevgili NAME siparişini aldık. numarası: ORDERNUMBER . detaylar emailinize gelmiştir.";
            mailBody = replaceFunction.replace(mailBody, name, orderNumber);

        }
        kafkaService.sendMessageKafka(new MailDto(mailBody, email));




        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
