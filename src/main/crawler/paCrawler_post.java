package crawler;

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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class paCrawler_post implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    static List<List<String>> allurls;

    public static void rw() throws Exception {
        String path = System.getProperty("user.dir");
        File fileIn = new File(path+"\\src\\main\\crawler\\out\\allpage.txt");
        //File fileOut = new File("D:\\data\\222.txt");
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
        //OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(fileOut), "UTF-8");
        BufferedReader bin = new BufferedReader(inStream);
        //BufferedWriter bou = new BufferedWriter(writerStream);
        String line = "";
        while ((line = bin.readLine()) != null) {
            if (!"".equals(line)) {
                //System.out.println(line);
                Map<String, List<String>> map = new HashMap();
                String pageUrl = line.split("##")[3];
                System.out.println(line.split("##")[1]+"  "+line.split("##")[0]);
               // analy(pg.replaceAll("\\\\\"", "\""));
                System.out.println("==============================");
            }
        }
    }


    @Override
    public void process(Page page) {
        allurls = new ArrayList<>();
        String s = page.getHtml().toString();
        Document newdoc = Jsoup.parse(s);
        //System.out.println(page.getUrl());
        Elements title = newdoc.getElementsByTag("title");
        String titles = "";
        if (title.size() > 0) {
            titles = title.get(0).text();
            System.out.println(titles);
        }
        Element subCategory = newdoc.getElementById("subCategory");
        System.out.println(subCategory.attr("value"));
        // https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=370&navigationKey=&sort=70&pageIndex=2
        Element totalPageNumber = newdoc.getElementById("totalPageNumber");
        String subCategorys = subCategory.attr("value");
        String totalPageNumbers = totalPageNumber.attr("value");
        Integer totalPage = Integer.parseInt(totalPageNumber.attr("value"));
        System.out.println(totalPageNumber.attr("value"));
        List<String> url = new ArrayList<>();
        url.add(page.getUrl().toString());
        url.add(titles);
        url.add(subCategorys);
        url.add(totalPageNumbers);
        allurls.add(url);

    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }

    public static List<List<String>> spiderRun(String url) throws Exception {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("47.254.22.115  ", 8080),
                new Proxy("158.69.150.164", 3128)
        ));

        Spider spider1 = Spider.create(new paCrawler_post())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();
        return allurls;

    }



}

