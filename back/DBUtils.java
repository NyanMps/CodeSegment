package decent.ejiaofei.util;

import decent.ejiaofei.bean.Order;
import decent.ejiaofei.config.ApplicationConfig;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 操作数据库的工具类
 */
public class DBUtils {
    private static DataSource ds = null;
    private static Properties prop = new Properties();

    // 使用静态代码块保证只加载一次
    static {
        try {
            prop.load(DBUtils.class.getClassLoader().getResourceAsStream("dbcp.properties"));
            ds = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    static DataSource getDataSource() {
        return ds;
    }

    public static void insertExample(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner(getDataSource());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO orders(orderid,account,money,starttime,type,province,city,time,supid,status) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Object[] param = {order.getOrderid(), order.getAccount(), order.getMoney(), order.getStarttime(),
                order.getType(), order.getProvince(), order.getCity(),sf.format(new Date()), ApplicationConfig.SUPID,"3"};
        qr.update(sql, param);
    }

    public static void updateExample(Order order,int tag) throws SQLException {
        QueryRunner qr = new QueryRunner(getDataSource());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "UPDATE orders SET status=?,time=? WHERE orderid=?";
        Object[] param = {tag,sf.format(new Date()),order.getOrderid()};
        qr.update(sql, param);
    }
}
