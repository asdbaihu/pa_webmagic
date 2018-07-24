package crawler;

import com.alibaba.fastjson.JSON;
import spiderZssh.newSpider.analyhz;
import spiderZssh.newSpider.spiderPage;
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
import java.util.List;
import java.util.Map;

public class paCrawler_deatilPage  implements PageProcessor {
    FileWriter out = null;
    String path = System.getProperty("user.dir");
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    static String getpage;
    static String title;
    static String subCategory ;
    static String totalPageNumber;
   /* public static void spiderPag(List<String> list) throws Exception{
        title = list.get(1);
        subCategory = list.get(2);
        totalPageNumber = list.get(3);
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory="+subCategory+"&navigationKey=&sort=70&pageIndex=";
        for (int i = 1; i <Integer.parseInt(totalPageNumber)+1 ; i++) {
            String ur = "";
            if(i==1){
                ur = list.get(0);
            }else {
                url = url+String.valueOf(i);
            }
            spiderRun(ur);
        }

    }
*/

    @Override
    public void process(Page page) {
        String s = page.getHtml().toString();
        //保存页面
        getpage = page.getUrl().toString()+"##"+ JSON.toJSONString(s);

        //System.out.println(s);
/*        try {
            //System.out.println(title);
            String t = title.replaceAll("/","or");
            //解析详情页面  subCategory  tzid  page
            //Map<String, Map<String, String>> sss = analyhz.analy(s);

            //配送

            //评价

//            out = new FileWriter("D:\\data\\"+subCategory+"-"+t+"-"+totalPageNumber+".txt", true);
            out = new FileWriter(path+"\\src\\main\\crawler\\out\\allpage.txt", true);
            out.write(title+"##"+subCategory+"##"+totalPageNumber+"##"+JSON.toJSONString(s));
            out.write("\r\n");
            out.flush();
            out.close();
            //0:43
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        } catch (Exception e) {
            System.err.println("write file error:" + e);
        }*/
    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }

    public static String spiderRun(String url) throws Exception{
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("31.173.209.111",8080),
                new Proxy("212.237.50.160",3128)

        ));

        //   new Proxy("183.52.150.248",61234)));//120.132.9.178
        Spider spider1 = Spider.create(new paCrawler_deatilPage())
                //.setScheduler(new FileCacheQueueScheduler("D:\\util\\hanlp-1.5.1-release\\trunk\\baikeFenlei\\new"))
                // .setDownloader(httpClientDownloader)
//                .setScheduler(new QueueScheduler()
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(900000000)))
                .addUrl(url)
                .thread(5);

        spider1.run();
        return getpage;

    }
   /* public static void main(String[] args) throws Exception {
        String url = "https://ssl.mall.cmbchina.com/_CL5_/Product/ProductListAjaxLoad?subCategory=97&navigationKey=&sort=70&pageIndex=";
        for (int i = 1; i <20 ; i++) {
            spiderRun(url+String.valueOf(i));
        }


    }*/
}

