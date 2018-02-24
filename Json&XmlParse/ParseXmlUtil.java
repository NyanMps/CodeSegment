package decent.ejiaofei.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import decent.ejiaofei.bean.Order;
import decent.ejiaofei.bean.OrderResponse;
import decent.ejiaofei.config.ApplicationConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来解析 XML 的工具类
 * <p>
 * 使用的是 dom4j
 */
public class ParseXmlUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseXmlUtil.class);

    /**
     * 解析登录接口返回的 XML 数据
     */
    public static void parseLogin(String data) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
        /*获取xml文件的根节点*/
        Element rootElement = document.getRootElement();

        String orderid = rootElement.elementText("orderid");
        String tag = rootElement.elementText("tag");
        String time = rootElement.elementText("time");
        // 供货商当前余额
        String supBalance = rootElement.elementText("supBalance");
        String error = rootElement.elementText("error");
        String errorExplain = rootElement.elementText("errorExplain");

        if (Integer.parseInt(error) == 0){
            // 登录成功后设置盐值
            ApplicationConfig.TOKEN = orderid;
        }
    }

    /**
     * 解析获取到的订单，把结果填充到相应的 bean
     * 使用的是 dom4j 来进行解析
     */
    public static OrderResponse parseOrder(String data) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
		/*获取xml文件的根节点*/
        Element rootElement = document.getRootElement();
        OrderResponse orderResponse = new OrderResponse();

        try {
            int amount = Integer.parseInt(rootElement.elementText("amount"));
            String time = rootElement.elementText("time");
            int error = Integer.parseInt(rootElement.elementText("error"));
            String errorExplain = rootElement.elementText("errorExplain");

            if (amount != 0 && error == 0) {
                List orders = rootElement.elements("order");
                List<Order> items = new ArrayList<Order>();
                for (int i = 0; i < orders.size(); i++) {
                    Element element = (Element) orders.get(i);
                    Order order = new Order();
                    order.setOrderid(element.elementText("orderid"));
                    order.setAccount(element.elementText("account"));
                    order.setMoney(element.elementText("money"));
                    order.setStarttime(element.elementText("starttime"));
                    order.setType(element.elementText("type"));
                    order.setProvince(element.elementText("province"));
                    order.setCity(element.elementText("city"));

                    items.add(order);
                }
                orderResponse.setOrders(items);
            }

            orderResponse.setAmount(amount);
            orderResponse.setError(error);
            orderResponse.setTime(time);
            orderResponse.setErrorExplain(errorExplain);

            return orderResponse;
        } catch (Exception e) {
            LOGGER.info("解析出错" + e.getMessage());
        }
        return null;
    }

    /**
     * 使用 Jackson 来解析 XML 为 bean 对象
     *
     * @param data
     * @return
     */
    public static OrderResponse parseXML2Order(String data) {
        XmlMapper xmlMapper = new XmlMapper();
        OrderResponse orderResponse;
        try {
            orderResponse = xmlMapper.readValue(data, OrderResponse.class);
            return orderResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析返平台结果请求的 XML 数据
     *
     * 使用的是 dom4j 解析，如果请求失败会打印日志
     * 这是一个通用的 XML 结果解析，解析 error 和 errorExplain，如果 error 不为 0 就表示异常，打印日志
     * @param data
     * @throws DocumentException
     */
    public static void parsePTResultXML(String data, String msg) throws DocumentException, UnsupportedEncodingException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
        /*获取xml文件的根节点*/
        Element rootElement = document.getRootElement();

        String error = rootElement.elementText("error");
        String errorExplain = rootElement.elementText("errorExplain");

        if (Integer.parseInt(error) != 0) {
            LOGGER.info(msg + "：" + errorExplain);
            return;
        }

        LOGGER.info("向平台返结果成功");
    }
}
