package com.ssm.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil {

    private static final int SOCKET_TIME_OUT = 60000;    // 设置读取超时
    private static final int CONNECT_TIME_OUT = 60000;    // 设置连接超时

    /**
     * 设置请求的参数值
     *
     * @return
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
    }

    /**
     * 设置参数列表
     *
     * @param param
     * @return
     */
    private static List<NameValuePair> createParam(Map<String, Object> param) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (param != null) {
            for (String k : param.keySet()) {
                nvps.add(new BasicNameValuePair(k, param.get(k).toString()));
            }
        }
        return nvps;
    }

    /**
     * 创建一个SSL信任所有证书的httpClient对象
     *
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 默认信任所有证书
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslcsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        //如果创建失败，就创建一个默认的Http的连接
        return HttpClients.createDefault();
    }

    /**
     * 无需本地证书keyStore的SSL https带参数请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String httpsPost(String url, Map<String, Object> params, Map<String, String> headers) {
        //创建一个信任的连接
        CloseableHttpClient httpClient = createSSLInsecureClient();
        //发送请求的实体类
        HttpPost httpPost = new HttpPost(url);
        //接收返回值
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            // 设置客户端请求的头参数getParams已经过时,现在用requestConfig对象替换
            httpPost.setConfig(getRequestConfig());
            //设置请求的头信息
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                httpPost.setHeader(key, headers.get(key));
            }
            //添加参数， 设置编码格式
            httpPost.setEntity(new UrlEncodedFormEntity(createParam(params), Charset.forName("utf-8")));
            //发送请求
            HttpResponse response = httpClient.execute(httpPost);
            //接收返回值
            HttpEntity httpEntity = response.getEntity();
            //返回值处理
            br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "utf-8"));
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("读取流文件异常", e);
        } catch (Exception e) {
            throw new RuntimeException("通讯未知系统异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param url        调用的地址
     * @param jsonParams 调用的参数
     * @return
     * @throws Exception
     */
    public static String post(String url, String jsonParams) throws Exception {
        //创建一个默认的连接对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);

        String httpStr;
        try {
            StringEntity entity = new StringEntity(jsonParams);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            httpStr = EntityUtils.toString(response.getEntity(), "UTF-8");

        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return httpStr;
    }

    public static void main(String[] args) throws Exception {
       /* JSONObject json = new JSONObject();
        json.put("dev_token", "Z_eErE4DkvcKjDM1OVE4c4");
        String result = postJson("https://www.googleadservices.com/pagead/conversion/app/1.0", json.toJSONString());
        System.out.println(result);*/
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("X-Forwarded-For", "216.58.194.174");
        headers.put("User-Agent", "MyAnalyticsCompany/1.0.0 (iOS 10.0.2; en_US; iPhone9,1; Build/13D15; Proxy)");
        Map<String, Object> params = new HashMap<>();
        params.put("dev_token", "Z_eErE4DkvcKjDM1OVE4c4");
        String data = httpsPost("https://www.googleadservices.com/pagead/conversion/app/1.0", params, headers);
        System.out.println(data);
    }

}

