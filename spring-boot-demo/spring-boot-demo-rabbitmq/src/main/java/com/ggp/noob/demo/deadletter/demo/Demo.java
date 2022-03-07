package com.ggp.noob.demo.deadletter.demo;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

/**
 * @Author Created by gongguanpeng on 2022/3/7 11:07
 */
@Component
public class Demo {
    @RabbitListener(bindings = {@QueueBinding(
            exchange = @Exchange(name = "int.stock.topic", type = "topic", durable = "true"),
            value = @Queue(value = "stock.backFillStockOutOrderLogisticsCarrierId", durable = "true",
            arguments = {@Argument(name = "x-dead-letter-exchange", value = "rabbitmq-demo-DLX"),
                    @Argument(name = "x-dead-letter-routing-key", value = "delayed-topic-rabbitmq-demo.default-group-rabbitmq-demo")
                 }),
            key = "backFillStockOutOrderLogisticsCarrierId"),
            }
            )
    public void generatorLogisticsOrder(@Payload String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)throws Exception{
        System.out.println("死信队列测试，消费失败");
        throw new RuntimeException(message);
    }

}
