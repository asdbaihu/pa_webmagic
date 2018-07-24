package crawler;

import java.util.ArrayList;
import java.util.List;

public class paCrawlerController {
    public static void main(String[] args) throws Exception{

        String url = "https://ssl.mall.cmbchina.com/_CL5_/Category/GetCategories?id=";
        //获得父分类下  类目录下  的第一个url
        List<List<String>> ls = new ArrayList<>();
        for (int i = 5; i <16 ; i++) {
            ls.add(paCrawler.spiderRun(url+String.valueOf(i)));
        }
        //通过第一个url解析该类目的页数 保存页面具体信息
        for(List<String> u:ls){
            for (String st :u){
                paCrawler_allurls.getAllUrls(st);
            }

        }
    }
}
