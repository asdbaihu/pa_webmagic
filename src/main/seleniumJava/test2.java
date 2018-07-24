package seleniumJava;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class test2  implements PageProcessor {
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
        Map<String, String> fields = new LinkedHashMap();
        page.addTargetRequests(page.getHtml().links().regex("https://ssl.mall.cmbchina.com/_CL5_/Product/*.*").all());

        String st = page.getUrl().toString();
        System.out.println("测试1："+  page.getHtml());
/*        try {
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
        }*/


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
        Spider spider1 = Spider.create(new test2())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=48&categoryName=12345625E812345625B6123456258512345625E6123456259E123456258112345625E6123456259C12345625AC&pushwebview=1")
                .thread(5);

        spider1.run();


    }
}