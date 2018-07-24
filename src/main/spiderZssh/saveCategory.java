package spiderZssh;



import com.alibaba.fastjson.JSON;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
 *爬取总分类页面解析出，产品类别以及类别下商品类别和商品编号（商品下是具体商品）
 * 保存说明： 分类id  产品类别  商品编号 商品名称
 * 一个分类下多个产品类别
 * 一个产品类别包含多个商品类别
 * 一个商品包含多个具体商品
 */
public class saveCategory implements PageProcessor {
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
        String s = page.getHtml().toString();
        String ur = page.getUrl().get();
        String data_id = ur.substring(ur.indexOf("=")+1);
        String lb2 = ""; //类别二名称
        String bh = ""; //类别3编号
        String lb3 = ""; //类别三名称
        Document newdoc = Jsoup.parse(s);
        Elements catalog_list =newdoc.getElementsByClass("catalog_list");
        for (int i = 0; i <catalog_list.size() ; i++) {
            Element cata = catalog_list.get(i);
            Elements lb = cata.getElementsByTag("dt");
            lb2 = lb.text();
            System.out.println(lb.text());//类别2
            Elements lbs = cata.getElementsByTag("dd");

            for (int j = 0; j <lbs.size() ; j++) {
                Element lball = lbs.get(j);
                Elements aa = lball.getElementsByTag("a");
                for (int k = 0; k <aa.size(); k++) {
                    bh = aa.get(k).attr("C3Id");
                    lb3 = aa.get(k).text();
                    try {
                        out = new FileWriter("D:\\data\\saveCategory.txt", true);
                        out.write(data_id+"  "+lb2+"  "+bh+"  "+lb3);
                        out.write("\r\n");
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        System.err.println("write file error:" + e);
                    }
                    System.out.println(aa.get(k).attr("C3Id"));//类别3编号
                    System.out.println(aa.get(k).text());//类别三名称
                }
            }

           // System.out.println(cata.toString());
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

                new Proxy("47.254.22.115  ",8080),
                new Proxy("158.69.150.164",3128)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new saveCategory())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();

    }
    public static void main(String[] args) throws Exception {
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=";
        for (int i = 5; i <16 ; i++) {
            spiderRun(url+String.valueOf(i));
        }
        // https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=6


    }
}
