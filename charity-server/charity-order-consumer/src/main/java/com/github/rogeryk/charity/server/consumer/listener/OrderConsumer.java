package com.github.rogeryk.charity.server.consumer.listener;

import com.github.rogeryk.charity.server.consumer.service.OrderService;
import com.github.rogeryk.charity.server.core.mq_data.OrderEvent;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(
        topic = "order-event",
        consumerGroup = "order-group",
        consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumer implements RocketMQListener<OrderEvent> {

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(OrderEvent orderEvent) {
        log.info("接受到消息", orderEvent);

        //消息已经处理过直接返回
        if (orderService.isProcessed(orderEvent)) return;

        switch (orderEvent.getOrderType()) {
            case OrderEvent.RECHARGE:
                orderService.recharge(orderEvent);
                break;
            case OrderEvent.DONATION:
                orderService.donate(orderEvent);
                break;
            default:
                log.error("无法处理类型", orderEvent);
                throw new RuntimeException("无法处理消息");
        }

    }

}
