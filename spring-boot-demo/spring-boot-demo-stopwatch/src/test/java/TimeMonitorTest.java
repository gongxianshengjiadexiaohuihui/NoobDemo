import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2021/5/27 15:24
 * @Description:
 */
public class TimeMonitorTest {
    @Test
    public void test() throws Exception{
        TimeMonitor.powerSwitch = TimeMonitor.ON;
        TimeMonitor.printTimeType = TimeTypeEnum.MILLISECONDS_TYPE;
        TimeMonitor timeMonitor = new TimeMonitor("test");
        timeMonitor.start("task1");
        Thread.sleep(1000);
        timeMonitor.stop();
        timeMonitor.start("task2");
        Thread.sleep(1000);
        timeMonitor.stop();
        timeMonitor.prettyPrint();
        timeMonitor.averagePrint();
    }
    @Test
    public void test_print_date(){

    }

}
