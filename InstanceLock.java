package decent.ejiaofei.config;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * <p>
 * <b>文件锁</b>
 * <p>
 * <pre>
 * 文件锁，起到只能有一个实例运行的效果
 * 使用 getLock，若为null则锁失败，反之锁成功
 * 锁失败，则有可能已有程序对此文件加独占锁，既已有程序正在运行
 * </pre>
 *
 */
public class InstanceLock {

    private RandomAccessFile rf;
    private FileLock lock;

    public InstanceLock(File file) {
        try {
            if (null == file) {
                return;
            }

            if (!file.exists()) {
                if (file.createNewFile()) {
                    return;
                }
            }

            if (!file.isFile()) {
                return;
            }

            rf = new RandomAccessFile(file, "rws");
            FileChannel channel = rf.getChannel();
            // 锁定
            lock = channel.tryLock(0, Long.MAX_VALUE, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (null != rf) {
            try {
                rf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (null != lock) {
            try {
                lock.release();
                lock = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileLock getLock() {
        return lock;
    }
}