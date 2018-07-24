package crawler.anewCrawler;

import com.alibaba.fastjson.JSON;
import crawler.util.crawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
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


import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class crawler_pa  implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
   // private static List<String> urls ;
   static String path = System.getProperty("user.dir");
    static FileWriter out = null;



    @Override
    public void process(Page page) {
        System.out.println(page.getUrl().toString());
        if(page.getUrl().toString().contains("https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=")){
            //urls = new ArrayList<>();
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
                        String categoryName ="";
                        try {
                            categoryName = crawlerUtil.decodeHan(lb3,"%","123456");
                        } catch (Exception e) {
                            System.err.println( e);
                        }
                        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory="+bh+"&categoryName="+categoryName+"&pushwebview="+1;
                        //urls.add(url);
                        //page.addTargetRequest(url+"##"+data_id+"##"+lb2+"##"+bh+"##"+lb3);
                        page.addTargetRequest(url);


                    }
                }
            }
        }else if(page.getUrl().toString().contains("https://ssl.mall.cmbchina.com/_CL5_/Product/ProductList?subcategory")){
            String s = page.getHtml().toString();
            Document newdoc = Jsoup.parse(s);
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
            //Integer totalPage = Integer.parseInt(totalPageNumber.attr("value"));
           // System.out.println(totalPageNumber.attr("value"));
            String urll = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory="+subCategorys+"&navigationKey=&sort=70&pageIndex=";

            for (int i = 1; i < Integer.parseInt(totalPageNumbers) + 1; i++) {
                String ur = "";
                if (i == 1) {
                    ur = page.getUrl().toString();
                } else {
                    ur = urll + String.valueOf(i);
                }
                page.addTargetRequest(ur);
            }
                //String getpage = paCrawler_deatilPage.spiderRun(ur);
        }else if(page.getUrl().toString().contains("https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=")){

            getProduct_post(page);
            try {
               out = new FileWriter(path + "\\src\\main\\crawler\\out\\allpage0722.txt", true);
                out.write(page.getUrl().toString()+ "##" + JSON.toJSONString(page.getHtml().toString()));
                out.write("\r\n");
                out.flush();
                out.close();
            } catch (Exception e) {
                try {
                    Thread.sleep(1000*20);
                } catch (Exception e1) {
                    System.err.println("" + e1);
                }
                System.err.println("write file error:" + e);
                //System.out.println(getpage);
            }

        }else if(page.getUrl().toString().contains("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale")){
            Document newdoc = Jsoup.parse(page.getHtml().toString());
            Elements detitle = newdoc.getElementsByClass("describe-title");
            //rest +=detitle.text()+" ";
            System.out.println( detitle.text());
            //fields.put("describe-title",detitle.text());
            Elements detable = newdoc.getElementsByClass("detail-table");
            for (int i = 0; i < detable.size(); i++) {
                Element el = detable.get(i);
                Elements trs = el.getElementsByTag("tr");
                for (int j = 0; j <trs.size() ; j++) {
                    System.out.println( trs.get(j).text());
                   // sb.append("%"+trs.get(j).text());
                    //rest +=  trs.get(j).text()+"";
                    //fields.put("tr"+j,trs.get(j).text());
                    //System.out.println("-----");
                }

            }
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
        Spider spider1 = Spider.create(new crawler_pa())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(1);

        spider1.run();
        //return urls;

    }
    public  static void getProduct_post(Page page){
        String url = page.getUrl().toString();
        String st = url.substring( url.indexOf("=")+1, url.indexOf("&"));
        String ss =url.split("=")[url.split("=").length-1];

        //String st = page.getUrl().toString().split("=")[1];

        StringBuffer sbs2 = new StringBuffer();
        Document newdoc = Jsoup.parse( page.getHtml().toString(), "UTF-8");
        // Map<String,  Map<String, String>> fs = new LinkedHashMap();

        //类别
        Elements cx=   newdoc.getElementsByClass("group-buy-item");
        for (int i = 0; i <cx.size() ; i++) {
            StringBuffer sbs = new StringBuffer();
            // Map<String, String> fields = new LinkedHashMap();
            // Map<String, String> fieldmap = new LinkedHashMap();
            Element el = cx.get(i);
                Elements ahref = el.getElementsByTag("a");
                String ahre = ahref.get(0).attributes().get("href");
                String ur = ahre.split("&")[1].split("=")[1];
                //解析 每个商品的跳转页面 详情，配送咨询
                StringBuffer sb1 = crawlerUtil.getAhref(ur);
                System.out.println(sb1);
                sbs.append("#"+sb1);


  /*
                  //h4
                Elements h4= el.getElementsByTag("h4");
                String h ="";
                if(h4.size()>0){
                    h = h4.eachText().get(0);
                }
                sbs.append("##"+h);
                System.out.println(h);
                // fields.put("h",h);

                //price
                Elements price= el.getElementsByClass("price");
                for (int j = 0; j <price.size() ; j++) {
                    Element pric = price.get(j);
                    Elements allprice = pric.getAllElements();
                    for (int k = 1; k <allprice.size() ; k++) {
                        Element pr = allprice.get(k);
                        System.out.print("price"+pr.text().toString());
                        sbs.append("##"+pr.text());
                        if(k==2){
                            sbs.append("##"+pr.text());
                        }
                    }
                }
                System.out.println();*/

            Elements clearfix= el.getElementsByClass("clearfix");
            for (int m = 0; m<clearfix.size() ; m++) {
                Element clearfixin = clearfix.get(m);
                Elements allclearfix = clearfixin.getAllElements();
                for (int n = 1; n <allclearfix.size() ; n++) {
                    Element cf = allclearfix.get(n);
                    if(n ==2){
                        Attributes ats = cf.attributes();
                        //System.out.println(ats.get("productid"));
                        String pid = ats.get("productid");
                        System.out.println("productid  "+pid);
                        //可以太调用 post请求代码  写道文件
                        StringBuffer sbuf =crawler_pa_post.test(pid);
                       // System.out.println(sbuf);
 /*                           try {
                                //page.addTargetRequests(page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class=‘post_item_body‘]/h3/a/@href").all());

                                //模拟post请求
                                Request req = new Request();
                                req.setMethod(HttpConstant.Method.POST);
                                req.setUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/GetScale");
                                req.setRequestBody(HttpRequestBody.json("{productCode:"+pid+"}", "utf-8"));
                                page.addTargetRequest(req);
                                //System.out.println(page.getHtml().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                        sbs.append("##"+pid);
                        sbs.append("##"+JSON.toJSONString(sbuf));
                        try {
                            out = new FileWriter(path + "\\src\\main\\crawler\\out\\prodetialpage0722.txt", true);
                            out.write(st+ "##" +ss+ sbs);
                            out.write("\r\n");
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            try {
                                Thread.sleep(1000*20);
                            } catch (Exception e1) {
                                System.err.println("" + e1);
                            }
                            System.err.println("write file error:" + e);
                            //System.out.println(getpage);
                        }
                        //fields.put("tzid",pid);
                        //fieldmap = testPost2.test(pid);
                    }
                    //fields.put("state",cf.text().toString());
//                    System.out.println("state  " +cf.text().toString());
//                    sbs.append("##"+cf.text());
                }
            }
            //sbs2.append(sbs);
        }


        System.out.println("$$$$$$$"+page.getUrl().toString());
        try {
            Thread.sleep(1000*3);
        } catch (Exception e) {
            System.err.println("" + e);
        }
    }
    public static void main(String[] args) throws Exception{

        String url = "https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=";
        //获得父分类下  类目录下  的第一个url
        List<List<String>> ls = new ArrayList<>();
        for (int i = 5; i <16 ; i++) {
           spiderRun(url+String.valueOf(i));
        }
        //通过第一个url解析该类目的页数 保存页面具体信息
      /*  for(List<String> u:ls){
            for (String st :u){
                paCrawler_allurls.getAllUrls(st);
            }
        }*/
    }
}