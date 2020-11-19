import com.ggp.Application;
import com.ggp.Sender;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @Author:ggp
 * @Date:2020/9/29 10:34
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRocketMQ {
    @Autowired
    private Sender sender;
    @Autowired
    private DefaultLitePullConsumer consumer;

    public void test_send() throws Exception{
        sender.send("hello world");
    }
    @Test
    public void test_consumer()throws Exception{
        sender.send("hello world");
        while (true){
            List<MessageExt> msgs = consumer.poll();
            for(MessageExt msg:msgs){
                System.out.println("pull message:"+new String(msg.getBody()));
            }
        }
    }

}
