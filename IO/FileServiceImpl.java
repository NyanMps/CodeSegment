package srm.cxf.server.wsfs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.axis.encoding.Base64;
import org.springframework.util.FileCopyUtils;
import srm.cxf.server.doc8d.model.ReturnMsg;

import javax.jws.WebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * TODO 提供 WS 根据 URL 获取文件流的具体实现
 * Created by 冰封承諾Andy on 2018/11/17.
 */
@WebService(endpointInterface = "srm.cxf.server.wsfs.FileService", serviceName = "fileService")
public class FileServiceImpl implements FileService {
    @Override
    public String getFile(String url) {
        ReturnMsg returnMsg = fileToBinStr(url);
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.processAnnotations(ReturnMsg.class);
        return xstream.toXML(returnMsg);
    }

    /**
     * 文件转为二进制字符串，使用 Base64 编码
     *
     * @return
     */
    private ReturnMsg fileToBinStr(String url) {
        ReturnMsg returnMsg = new ReturnMsg();

        String path = this.getClass().getResource("/").getPath();
        path = path.substring(1, path.indexOf("WEB-INF/classes")) + url;
        File file = new File(path);
        if (!file.exists()) {
            returnMsg.setRESTYPE("1");
            returnMsg.setMSG("文件不存在");
            return returnMsg;
        }
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            returnMsg.setRESTYPE("0");
            returnMsg.setMSG(Base64.encode(bytes));
            return returnMsg;
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin String 出错", ex);
        }
    }

    /**
     * 二进制字符串转文件
     *
     * @param bin
     * @param fileName
     * @param parentPath
     * @return
     */
    public static File binToFile(String bin, String fileName, String parentPath) {
        try {
            File file = new File(parentPath, fileName);
            file.createNewFile();
            // byte[] bytes1 = bin.getBytes(StandardCharsets.UTF_8);
            byte[] bytes1 = Base64.decode(bin);
            FileCopyUtils.copy(bytes1, file);

            //FileOutputStream outs = new FileOutputStream(file);
            //outs.write(bytes1);
            //outs.flush();
            //outs.close();

            return file;
        } catch (Exception ex) {
            throw new RuntimeException("transform bin into File 出错", ex);
        }
    }
}
