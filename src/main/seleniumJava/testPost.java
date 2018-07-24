package seleniumJava;


import com.alibaba.fastjson.JSON;
import crawler4jZssh.zsshSpider;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.FileWriter;
import java.util.*;

public class testPost   implements PageProcessor {
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
        Map<String, String> fields = new LinkedHashMap();
        String st = page.getUrl().toString();
        System.out.println(st);
       /* page.addTargetRequests(page.getHtml().links().regex("http://www.meishij.net/chufang/diy/*.*").all());
        page.addTargetRequests(page.getHtml().links().regex("http://www.meishij.net/jiankang/*.*").all());
        page.addTargetRequests(page.getHtml().links().regex("http://www.meishij.net/zuofa/*.*").all());
        // page.addTargetRequests(page.getHtml().links().regex("http://www.meishij.net/.*").all());

        try {
            Thread.sleep(300);
        } catch (Exception e) {
            System.err.println("" + e);
        }
        if (st.contains("http://www.meishij.net/zuofa")) {

            String s = page.getHtml().toString();
            fields.put(st, s);
            try {

                //  /bigdata/lzgSpider/meishijie/mesihi2.txt
                out = new FileWriter("/bigdata/lzgSpider/meishijie/meishi2.txt", true);
                out.write(JSON.toJSONString(fields));
                out.write("\r\n");
                out.flush();
                out.close();
            } catch (Exception e) {
                System.err.println("write file error:" + e);
            }

            // page.putField(page.getUrl().toString(), page.getHtml());
        }
        // page.addTargetRequests(page.getHtml().links().regex("http://fenlei.baike.com/.*").all());
*/
    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0");
        return site;
    }

    public static void main(String[] args) throws Exception {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("61.135.217.7", 80)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new testPost())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                //.addUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale")
                ;
        Map<String, Object> nameValuePair = new HashMap<String, Object>();
        NameValuePair[] values = new NameValuePair[1];
        values[0] = new BasicNameValuePair("pagesize", "10");
        nameValuePair.put("productCode", "S1H-40T-0TB_026");
        //url = "http://www.zjsfgkw.cn/Notice/NoticeSD?cbfy=&pageno="+String.valueOf(i)+"&pagesize=10";
        Request request = new Request("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale");
        request.setExtras(nameValuePair);
        request.setMethod(HttpConstant.Method.POST);
        spider1.addRequest(request);
        spider1.thread(5).run();


    }
}
