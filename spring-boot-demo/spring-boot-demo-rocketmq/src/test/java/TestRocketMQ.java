import com.ggp.Application;
import com.ggp.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    @Test
    public void test_send() throws Exception{
        sender.send("hello world");
    }
}
