package crawler.analyer;

import crawler.paCrawler;
import crawler.util.crawlerUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;

import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class desc_crawler implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    @Override
    public void process(Page page) {
        try {
            //page.addTargetRequests(page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class=‘post_item_body‘]/h3/a/@href").all());

       //模拟post请求
            Request req = new Request();
            req.setMethod(HttpConstant.Method.POST);
            req.setUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale");
            req.setRequestBody(HttpRequestBody.json("{productCode:'S41-40S-02D_007'}", "utf-8"));
            page.addTargetRequest(req);
            System.out.println(page.getHtml().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       // System.out.println(page.getHtml().toString());




    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }
    public static void main(String[] args){

        try {
            Spider.create(new desc_crawler()).addUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=22&categoryName=12345625E9123456259712345625AA12345625E51234562585123456258912345625E7123456258112345625AF&pushwebview=1").thread(5).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
