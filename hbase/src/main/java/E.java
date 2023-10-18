import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: E
 * @Author wangtao
 * @Date 2023/8/31 11:31
 * @description:
 */
public class E {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");



        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
    }
}
