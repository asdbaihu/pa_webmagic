package crawler;

import crawler.util.crawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spiderZssh.saveCategory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.FileWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据分类url（手机数码）得到 categoryId和categoryName（手机）;
 * 根据category（手机）得到所有该category页数
 */
public class paCrawler implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private static List<String> urls ;


    @Override
    public void process(Page page) {

        urls = new ArrayList<>();
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
                    System.out.println(data_id+"  "+lb2+"  "+bh+"  "+lb3);
                    //  view-source:https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1
                    // subcategory=bh  categoryName = URLDecoder.decode(lb3,"UTF-8"); 注意斜杠  categoryName.replace(/%/g, "123456");
                   //解析 获得url
                    String categoryName ="";
                    try {
                        categoryName = crawlerUtil.decodeHan(lb3,"%","123456");
                    } catch (Exception e) {
                        System.err.println( e);
                    }
                    String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory="+bh+"&categoryName="+categoryName+"&pushwebview="+1;
                    urls.add(url);
                    //categoryName = URLDecoder.decode(lb3,"UTF-8");

                    /*  try {
                        out = new FileWriter("D:\\data\\saveCategory.txt", true);
                        out.write(data_id+"  "+lb2+"  "+bh+"  "+lb3);
                        out.write("\r\n");
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        System.err.println("write file error:" + e);
                    }*/
                    System.out.println(aa.get(k).attr("C3Id"));//类别3编号
                    System.out.println(aa.get(k).text());//类别三名称
                }
            }
        }
    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }

    public static List<String> spiderRun(String url) throws Exception{
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(

                new Proxy("47.254.22.115  ",8080),
                new Proxy("158.69.150.164",3128)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new paCrawler())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();
        return urls;

    }
    public static void sartCrawler(String url) throws Exception{
        spiderRun(url);
    }
    /*public static void main(String[] args) throws Exception {
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=";
        //获得父分类下  类目录下  的第一个url
        List<List<String>> ls = new ArrayList<>();
        for (int i = 5; i <16 ; i++) {
            ls.add(spiderRun(url+String.valueOf(i)));
        }
        //通过第一个url解析该类目的页数
        for(List<String> u:ls){
            for (String st :u){
                paCrawler_allurls.getAllUrls(st);
            }

        }
        // https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=6


    }*/
}