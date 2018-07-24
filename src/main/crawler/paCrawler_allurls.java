package crawler;

import com.alibaba.fastjson.JSON;
import crawler.util.GetAllfile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spiderZssh.newSpider.spiderPage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;


import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class paCrawler_allurls implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    static List<List<String>> allurls;

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
/*        for (int i = 1; i <totalPage+1 ; i++) {
            String urls ="";
            urls = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory="+subCategorys+"&navigationKey=&sort=70&pageIndex="+i;
            System.out.println(urls);
            if(i == 1){
                try{
                   urls =page.getUrl().toString();
                }catch (Exception e) {
                    System.err.println(e);
                }
            }else{
                try {
                    urls =urls;
                } catch (Exception e) {
                    System.err.println(e);
                }
            }


        }
        try {
            spiderPage.spiderPag(titles,subCategorys,totalPageNumbers);
        } catch (Exception e) {
            System.err.println( e);
        }*/

   /*     try {
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("write file error:" + e);
        }*/

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

        Spider spider1 = Spider.create(new paCrawler_allurls())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();
        return allurls;

    }

    public static void getAllUrls(String url) throws Exception {

        String path = System.getProperty("user.dir");
        FileWriter out = null;
        List<List<String>> allurl = spiderRun(url);
        for (List<String> u : allurl) {
            String title = u.get(1);
            String subCategory = u.get(2);
            String totalPageNumber = u.get(3);
            String urll = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=" + subCategory + "&navigationKey=&sort=70&pageIndex=";
            for (int i = 1; i < Integer.parseInt(totalPageNumber) + 1; i++) {
                String ur = "";
                if (i == 1) {
                    ur = u.get(0);
                } else {
                    ur = urll + String.valueOf(i);
                }
                String getpage = paCrawler_deatilPage.spiderRun(ur);
                try {
                    out = new FileWriter(path + "\\src\\main\\crawler\\out\\allpage2.txt", true);
                    out.write(title + "##" + subCategory + "##" + totalPageNumber + "##" + getpage);
                    out.write("\r\n");
                    out.flush();
                    out.close();
                    //0:43
                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                } catch (Exception e) {
                    System.err.println("write file error:" + e);
                    //System.out.println(getpage);
                }
                //paCrawler_deatilPage.spiderPag(u);
            }
        }
  /*  public static void main(String[] args) throws Exception {
        // String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1" ;
        //String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=20&categoryName=12345625E812345625BD12345625A612345625E512345625851234562585/12345625E51234562585123456258512345625E7123456259412345625B512345625E5123456259912345625A8&pushwebview=1";
        //String url ="";
        //  手机  url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1";
        // url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=844&categoryName=12345625E5123456258F12345625B712345625E5123456258D12345625A1&pushwebview=1";
        String path = System.getProperty("user.dir");
        List<File> listFile = GetAllfile.GetDirectory("\\src\\main\\crawler\\urls");
        for (File file : listFile) {
            File fileIn = new File(file.getAbsolutePath());
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader bin = new BufferedReader(inStream);
            String line = "";
            while ((line = bin.readLine()) != null) {
                if (!"".equals(line)) {
                    spiderRun(line);
                }
            }
        }

        *//* for (int i = 1; i <20 ; i++) {
            spiderRun(url+String.valueOf(i));
        }*//*

    }*/
    }
}
