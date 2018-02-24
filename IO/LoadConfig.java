package decent.ejiaofei.util;

import decent.ejiaofei.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件
 * 保存到全局变量中，以便以后的使用
 */
public class LoadConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadConfig.class);

    public static void load() {
        try {
            Properties prop = new Properties();
            Map<String, String> config = new HashMap<String, String>(9);

            prop.load(LoadConfig.class.getClassLoader().getResourceAsStream("config.properties"));

            config.put("supid", prop.getProperty("SUPID"));
            config.put("pwd", prop.getProperty("PWD"));
            config.put("login", prop.getProperty("LOGIN"));
            config.put("getOrderURL", prop.getProperty("getOrderURL"));
            config.put("rechargeURL", prop.getProperty("rechargeURL"));
            config.put("statusURL", prop.getProperty("statusURL"));
            config.put("processURL", prop.getProperty("processURL"));
            config.put("balanceURL", prop.getProperty("balanceURL"));
            config.put("ptBalanceURL", prop.getProperty("PTbalanceURL"));
            config.put("GHSID", prop.getProperty("GHSID"));
            config.put("GHSPWD", prop.getProperty("GHSPWD"));

            for (Map.Entry<String, String> entry :
                    config.entrySet()) {
                if(entry.getValue().isEmpty()){
                    ApplicationConfig.isInit = false;
                    LOGGER.debug(entry.getKey() + "属性未配置，程序终止");
                    return;
                }
            }

            ApplicationConfig.isInit = true;
            ApplicationConfig.SUPID = config.get("supid");
            ApplicationConfig.PWD = config.get("pwd");
            ApplicationConfig.loginURL = config.get("login");
            ApplicationConfig.getOrderURL = config.get("getOrderURL");
            ApplicationConfig.rechargeURL = config.get("rechargeURL");
            ApplicationConfig.statusURL = config.get("statusURL");
            ApplicationConfig.processURL = config.get("processURL");
            ApplicationConfig.balanceURL = config.get("balanceURL");
            ApplicationConfig.PTbalanceURL = config.get("ptBalanceURL");
            ApplicationConfig.GHSID = config.get("GHSID");
            ApplicationConfig.GHSPWD = config.get("GHSPWD");

        } catch (Exception e) {
            LOGGER.debug("读取配置文件失败" + e.getMessage());
        }


    }
}
