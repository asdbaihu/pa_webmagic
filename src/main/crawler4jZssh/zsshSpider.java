package crawler4jZssh;

import com.alibaba.fastjson.JSON;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2018/1/25.
 */
public class zsshSpider implements PageProcessor {
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
        Map<String, String> fields = new LinkedHashMap();
        String s = page.getHtml().toString();
        System.out.println(s);
        try {
            out = new FileWriter("D:\\data\\spider3.txt", true);
            out.write(JSON.toJSONString((Object)s));
            out.write("\r\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("write file error:" + e);
        }
       /* String st = page.getUrl().toString();
        System.out.println(st);
        String s = page.getHtml().toString();
        fields.put(st, s);
        try {
                out = new FileWriter("D:\\data\\spider.txt", true);
                out.write(JSON.toJSONString(fields));
                out.write("\r\n");
                out.flush();
                out.close();
        } catch (Exception e) {
            System.err.println("write file error:" + e);
        }*/

//        page.addTargetRequests(page.getHtml().links().regex("https://ssl.mall.cmbchina.com/_CL5_/Category/*.*").all());
//        page.addTargetRequests(page.getHtml().links().regex("http://www.meishij.net/.*").all());
/*        String st = page.getUrl().toString();
        try {
        Thread.sleep(300);
        } catch (Exception e) {
            System.err.println("" + e);
        }
        if (st.contains("https://ssl.mall.cmbchina.com")) {
            String s = page.getHtml().toString();
            fields.put(st, s);
            try {
               *//* out = new FileWriter("D:\\data\\spider.txt", true);
                out.write(JSON.toJSONString(fields));
                out.write("\r\n");
                out.flush();
                out.close();*//*
} catch (Exception e) {
        System.err.println("write file error:" + e);
        }

        // page.putField(page.getUrl().toString(), page.getHtml());
        }*/
        // page.addTargetRequests(page.getHtml().links().regex("http://fenlei.baike.com/.*").all());

        }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }

    public static void spiderRun(String url) throws Exception{
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(

                new Proxy("47.254.22.115  ",8080),
                new Proxy("158.69.150.164",3128)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new zsshSpider())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();

    }
    public static void main(String[] args) throws Exception {
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=97&navigationKey=&sort=70&pageIndex=";
        for (int i = 1; i <20 ; i++) {
            spiderRun(url+String.valueOf(i));
        }
        // https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=6


    }
}
