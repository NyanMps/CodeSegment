package decent.ejiaofei;

import decent.ejiaofei.cache.CacheManager;
import decent.ejiaofei.cache.impl.CacheManagerImpl;
import decent.ejiaofei.task.CacheListener;

public class CacheTest {
    public static void main(String[] args) {
        CacheManager cacheManager = new CacheManagerImpl();
        cacheManager.putCache("1", "first", 10 * 1000L);
        cacheManager.putCache("2", "two", 6 * 1000L);
        cacheManager.putCache("3", "th", 20 * 1000L);
        cacheManager.putCache("4", "xd", 0L);
        CacheListener cacheListener = new CacheListener(cacheManager);


        cacheListener.startListen();
    }
}
