import com.ggp.noob.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;

/**
 * @Author:ggp
 * @Date:2020/10/30 16:42
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJedis {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void test_set_data(){
        redisTemplate.opsForValue().set("name","ggp");
    }
    @Test
    public void test_get_data(){
        redisTemplate.opsForValue().get("name");
    }
}
