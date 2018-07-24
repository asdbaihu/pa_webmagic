package crawler.analyer;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

public class testdesc implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().toString());
    }
    // System.out.println(page.getHtml().toString());




    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Linux; Android 6.0.1; DUK-AL20 Build/MXC89K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36");
        return site;
    }
    public static void main(String[] args){

        try {
            Spider.create(new testdesc()).addUrl("https://ssl.mall.cmbchina.com/_CL5_/Product/Detail?productCode=S1H-70E-21J-02_026&pushwebview=1&productIndex=1").thread(5).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
