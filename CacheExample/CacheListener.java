package decent.ejiaofei.task;

import decent.ejiaofei.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheListener{
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheListener.class);

    private CacheManager cacheManager;

    public CacheListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    public void startListen() {
        new Thread(() -> {
            while (true) {
                for(String key : cacheManager.getAllKeys()) {
                    if (cacheManager.isTimeOut(key)) {
                     cacheManager.clearByKey(key);
                     LOGGER.info(key + " 缓存被清除");
                 }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    
    }
}