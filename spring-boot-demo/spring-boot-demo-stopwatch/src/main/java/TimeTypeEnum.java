/**
 * @Author:ggp
 * @Date:2021/5/27 16:49
 * @Description:
 */
public enum TimeTypeEnum {
    NANOS_TYPE(1,"nanos"),
    MILLISECONDS_TYPE(2,"milliseconds"),
    SECONDS_TYPE(3,"seconds")
    ;
    int type;
    String name;

    TimeTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
