package crawler.analyer;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getDesc_post {
    public static void main(String[] args){
        //设置POST请求
        Request request = new Request("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale");
//只有POST请求才可以添加附加参数
        request.setMethod(HttpConstant.Method.POST);

        //设置POST参数
        List<NameValuePair> nvs = new ArrayList<>();
        nvs.add(new BasicNameValuePair("productCode", "S25-401-0BD_075"));


        //转换为键值对数组
        NameValuePair[] values = nvs.toArray(new NameValuePair[] {});

        //将键值对数组添加到map中
        Map<String, Object> params = new HashMap<>();
//key必须是：nameValuePair
        params.put("nameValuePair", values);

//设置request参数
        request.setExtras(params);

// 开始执行
        try {
            Spider.create(new desc_crawler()).addRequest(request).thread(5).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
