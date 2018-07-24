package spiderZssh;



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
public class spiderPage implements PageProcessor {
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    static String title = "";
    static String subCategory = "";
    static String totalPageNumber ="";
    public static void spiderPag(String titles,String subCategorys,String totalPageNumbers) throws Exception{
        title = titles;
        subCategory = subCategorys;
        totalPageNumber = totalPageNumbers;
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory="+subCategorys+"&navigationKey=&sort=70&pageIndex=";
        for (int i = 1; i <Integer.parseInt(totalPageNumbers)+1 ; i++) {
            spiderRun(url+String.valueOf(i));
        }

    }


    @Override
    public void process(Page page) {
        String s = page.getHtml().toString();
        System.out.println(s);
        try {
            System.out.println(title);
            String t = title.replaceAll("/","or");
            System.out.println(t);
            out = new FileWriter("D:\\data\\"+subCategory+"-"+t+"-"+totalPageNumber+".txt", true);
            out.write(title+"##"+JSON.toJSONString((Object)s));
            out.write("\r\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("write file error:" + e);
        }

    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }

    public static void spiderRun(String url) throws Exception{
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("31.173.209.111",8080),
                new Proxy("212.237.50.160",3128)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new spiderPage())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();

    }
   /* public static void main(String[] args) throws Exception {
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=97&navigationKey=&sort=70&pageIndex=";
        for (int i = 1; i <20 ; i++) {
            spiderRun(url+String.valueOf(i));
        }


    }*/
}
