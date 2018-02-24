package decent.ejiaofei.util;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于请求各种 API 的工具类
 *
 * 发起各种 HTTP 请求
 */
public class ApiService {
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 无参的GET请求，成功返回内容；不成功返回null
     * 不成功可能是 500、501、404 等
     */
    public static String doGet(String url) throws IOException, ParseException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    /**
     * 带参数的 GET 请求
     */
    public static String doGet(String url, Map<String, String> params) throws IOException, ParseException, URISyntaxException {
        // 定义请求的参数
        URIBuilder uriBuilder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.setParameter(entry.getKey(), entry.getValue());
        }
        return doGet(uriBuilder.build().toString());
    }

    /**
     * 带参数的 POST 请求
     * 请求失败会返回 null
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);

        if (null != params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

}
