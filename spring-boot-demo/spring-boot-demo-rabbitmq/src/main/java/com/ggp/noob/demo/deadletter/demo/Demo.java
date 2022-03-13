//package com.ggp.noob.demo.deadletter.demo;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import com.rabbitmq.client.Channel;
//
///**
// * @Author Created by gongguanpeng on 2022/3/7 11:07
// */
//@Component
//public class Demo {
//    @RabbitListener(bindings = {
//            @QueueBinding(
//            value = @Queue(value = "stock.backFillStockOutOrderLogisticsCarrierId", durable = "true", arguments = {
//                    @Argument(name = "x-dead-letter-exchange", value = "${spring.application.name}"+"-DLX"),
//                    @Argument(name = "x-dead-letter-routing-key", value = "delayed-topic-"+"${spring.application.name}"+".default-group-"+"${spring.application.name}")
//                    ,@Argument(name = "x-max-length", value = "2",type = "java.lang.Integer")
////                    ,@Argument(name = "x-message-ttl", value = "2000",type = "java.lang.Integer")
//                 }),
//                    exchange = @Exchange(name="int.stock.topic",value = "int.stock.topic", type = "topic", durable = "true"),
//                    key = "backFillStockOutOrderLogisticsCarrierId"
//            )
//            })
//    public void generatorLogisticsOrder(@Payload Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)throws Exception{
//        System.out.println("死信队列测试，消费失败");
//        while (true){
//
//        }
////        throw new RuntimeException(message.toString());
//    }
//
//}
