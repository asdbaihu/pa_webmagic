package crawler.analyer;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.json.Json;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class analyerProduct_post {
    public static StringBuffer test(String productCode){
    //String rest = "";
    Map<String, String> fields = new LinkedHashMap();
    StringBuffer sb = new StringBuffer();
    CloseableHttpClient httpClient = HttpClients.createDefault();

    String entityStr = null;
    CloseableHttpResponse response = null;


    try{
        HttpPost httpPost = new HttpPost("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale");

        /*
         * 添加请求参数
         */
        // 创建请求参数
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("productCode", productCode);
        // BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
        list.add(param1);
        //list.add(param2);
        // 使用URL实体转换工具
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);

        /*
         * 添加请求头信息
         */
        // 浏览器表示
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        // 传输的类型
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        // 执行请求
        response = httpClient.execute(httpPost);
        // 获得响应的实体对象
        HttpEntity entity = response.getEntity();
        // 使用Apache提供的工具类进行转换成字符串
        entityStr = EntityUtils.toString(entity, "UTF-8");

        // System.out.println(Arrays.toString(response.getAllHeaders()));


    } catch (Exception e) {
        System.err.println("Http协议出现问题");
        e.printStackTrace();
    } finally {
        // 释放连接
        if (null != response) {
            try {
                response.close();
                httpClient.close();
            } catch (Exception e) {
                System.err.println("释放连接出错");
                e.printStackTrace();
            }
        }
    }
    //System.out.println(entityStr);
    Document newdoc = Jsoup.parse(entityStr);

    Elements detitle = newdoc.getElementsByClass("describe-title");
    //rest +=detitle.text()+" ";
    System.out.println( detitle.text());
    fields.put("describe-title",detitle.text());
    Elements detable = newdoc.getElementsByClass("detail-table");
    for (int i = 0; i < detable.size(); i++) {
        Element el = detable.get(i);
        Elements trs = el.getElementsByTag("tr");
        for (int j = 0; j <trs.size() ; j++) {
            System.out.println( trs.get(j).text());
            sb.append("%"+trs.get(j).text());
            //rest +=  trs.get(j).text()+"";
            //fields.put("tr"+j,trs.get(j).text());
            //System.out.println("-----");
        }

    }
    return sb;


}

}
