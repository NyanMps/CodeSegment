package decent.ejiaofei.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 其他的使用到的工具类
 *
 * 包含判断时间是否超过五分钟
 */
public class OtherTools {

    public static boolean isOverstepMinute(Date date){
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.add(Calendar.MINUTE, +5);

        return cal1.compareTo(cal) <= 0;
    }
}
