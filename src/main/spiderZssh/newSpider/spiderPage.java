package spiderZssh.newSpider;




import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2018/1/25.
 * 爬取每页内容，获得 商品id 和名字
 *
 * analyhz解析出 每页所有 物品的 价格 名称及利用testpost2解析出 物品详细信息
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
        Map<String, String> fields = new LinkedHashMap();
        //System.out.println(s);
        try {
            //System.out.println(title);
            String t = title.replaceAll("/","or");
            fields.put("title",t);
            fields.put("subCategory",subCategory);
            System.out.print(subCategory+"  ");
            System.out.print(t+"  ");
            System.out.println(totalPageNumber+"  ");
            fields.put("totalPageNumber",totalPageNumber);
            Map<String, Map<String, String>> sss = analyhz.analy(s);
            sss.put("bt",fields);
            System.out.println(JSON.toJSON(sss).toString());



//            out = new FileWriter("D:\\data\\"+subCategory+"-"+t+"-"+totalPageNumber+".txt", true);
            out = new FileWriter("D:\\data\\allsave4.txt", true);
            out.write(title+"##"+JSON.toJSON(sss).toString());
            out.write("\r\n");
            out.flush();
            out.close();
            //0:43
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
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
