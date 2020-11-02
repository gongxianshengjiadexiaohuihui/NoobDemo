import com.ggp.noob.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author:ggp
 * @Date:2020/11/2 19:04
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class testRedisson {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test_get_lock() throws Exception{
        /**
         * 锁超时时间
         */
        Long timeout=1000L;
        /**
         * 锁过期时间
         */
        Long expire=30L;
        /**
         * 并发时间
         */
        Integer size = 1000;
        /**
         * 锁名字
         */
        String key="test_lock";
        /**
         * 线程池
         */
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        /**
         * 门栓  保证所有线程执行完毕
         */
        CountDownLatch latch = new CountDownLatch(size);
        /**
         * 计数器
         */
        LongAdder success = new LongAdder();
        LongAdder fail = new LongAdder();
        for (int i = 0; i <size ; i++) {
            executorService.execute(()->{
                RLock lock = redissonClient.getLock(key);
                try {
                    if(lock.tryLock(timeout,expire,TimeUnit.MILLISECONDS)){
                        success.increment();
                        latch.countDown();
                    }else {
                        fail.increment();
                        latch.countDown();
                    }
                } catch (InterruptedException e) {
                    System.out.println("获取锁失败");
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                    }
                }
            });
        }
        latch.await();
        executorService.shutdown();
        redissonClient.shutdown();
        System.out.println("总共"+size+"获取锁");
        System.out.println("总共"+success+"获取锁成功");
        System.out.println("总共"+fail+"获取锁失败");
    }

}
