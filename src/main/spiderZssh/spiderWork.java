package spiderZssh;
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
import util.FileOperation;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取人工保存的部分商品的url 解析出该商品类别下的分页
 * 根据总页数调用spiderPage爬取网页（得到全部商品url）
 *
 * 注：第一次需要人工解析出部分url得到全部商品url；后期直接使用进行爬去进行原网站数据比较
 */
public class spiderWork implements PageProcessor{

    //https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1
    FileWriter out = null;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
       // Map<String, String> fields = new LinkedHashMap();
        try{
            out = new FileWriter("D:\\data\\aspideianaly\\spider.txt", true);
        }catch (Exception e) {
            System.err.println("write file error:" + e);
        }

        String s = page.getHtml().toString();
        Document newdoc = Jsoup.parse(s);
        //System.out.println(page.getUrl());
        Elements title = newdoc.getElementsByTag("title");
        String titles = "";
        if(title.size()>0){
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
        /*try{
            Map<String,String> map = new HashMap();
            map.put(titles,page.getUrl().toString());
            out.write(JSONObject.toJSONString(map));
            out.write("\r\n");
        }catch (Exception e) {
            System.err.println("write file error:" + e);
        }*/
        for (int i = 1; i <totalPage+1 ; i++) {
            String urls = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory="+subCategory.attr("value")+"&navigationKey=&sort=70&pageIndex="+i;
            System.out.println(urls);
            if(i == 1){
                try{
                    out.write(titles+"##"+page.getUrl().toString());
                    out.write("\r\n");
                }catch (Exception e) {
                    System.err.println("write file error:" + e);
                }
            }else{
                try {
//                Map<String,String> map1 = new HashMap();
//                map1.put(titles,urls);
//                 out.write(JSONObject.toJSONString(map1));
                    out.write(titles+"##"+urls);
                    out.write("\r\n");

                } catch (Exception e) {
                    System.err.println("write file error:" + e);
                }
            }


        }
        try {
            spiderPage.spiderPag(titles,subCategorys,totalPageNumbers);
        } catch (Exception e) {
            System.err.println( e);
        }

        try {
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

                new Proxy("47.254.22.115  ",8080),
                new Proxy("158.69.150.164",3128)

        ));

        Spider spider1 = Spider.create(new spiderWork())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();

    }
    public static void main(String[] args) throws Exception {
       // String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1" ;
       //String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=20&categoryName=12345625E812345625BD12345625A612345625E512345625851234562585/12345625E51234562585123456258512345625E7123456259412345625B512345625E5123456259912345625A8&pushwebview=1";
       String url ="";
        //  手机  url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=370&categoryName=12345625E61234562589123456258B12345625E6123456259C12345625BA&pushwebview=1";
       url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory=844&categoryName=12345625E5123456258F12345625B712345625E5123456258D12345625A1&pushwebview=1";
        List<File> listFile = FileOperation.traverseFolder("D:\\data\\1url");
        for (File file : listFile) {
            File fileIn = new File(file.getAbsolutePath());
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader bin = new BufferedReader(inStream);
            String line ="";
            while ((line = bin.readLine()) != null  ) {
                if(!"".equals(line)) {
                    spiderRun(line);
                }
            }
        }

        /* for (int i = 1; i <20 ; i++) {
            spiderRun(url+String.valueOf(i));
        }*/


    }
}

