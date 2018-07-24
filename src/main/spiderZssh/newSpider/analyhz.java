package spiderZssh.newSpider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.json.Json;
import seleniumJava.testPost2;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class analyhz {
    public static  Map<String,  Map<String, String>>  analy(String page){
        String sts= null;
        Document newdoc = Jsoup.parse( page, "UTF-8");

        Map<String,  Map<String, String>> fs = new LinkedHashMap();

        //类别
        Elements cx=   newdoc.getElementsByClass("group-buy-item");
        for (int i = 0; i <cx.size() ; i++) {
            Map<String, String> fields = new LinkedHashMap();
            Map<String, String> fieldmap = new LinkedHashMap();
            Element el = cx.get(i);
            //h4
            Elements h4= el.getElementsByTag("h4");
            String h ="";
            if(h4.size()>0){
                h = h4.eachText().get(0);
            }
            System.out.println(h);
            fields.put("h",h);

            //price
            Elements price= el.getElementsByClass("price");
            for (int j = 0; j <price.size() ; j++) {
                Element pric = price.get(j);
                Elements allprice = pric.getAllElements();
                for (int k = 1; k <allprice.size() ; k++) {
                    Element pr = allprice.get(k);
                    System.out.print(pr.text().toString());
                    fields.put("price"+k,pr.text().toString());
                }
            }
            System.out.println();

            Elements clearfix= el.getElementsByClass("clearfix");
            for (int m = 0; m<clearfix.size() ; m++) {
                Element clearfixin = clearfix.get(m);
                Elements allclearfix = clearfixin.getAllElements();
                for (int n = 1; n <allclearfix.size() ; n++) {
                    Element cf = allclearfix.get(n);
                    if(n ==2){
                        Attributes ats = cf.attributes();
                        System.out.println(ats.get("productid"));
                        String pid = ats.get("productid");
                        fields.put("tzid",pid);
                        fieldmap = testPost2.test(pid);
                    }
                    fields.put("state",cf.text().toString());


                    //System.out.println(cf.text().toString());
                }
            }
            fs.put("proc"+i,fields);
            fs.put("descpro"+i,fieldmap);
        }

//        System.out.println();
//        Json jj = new Json();
//        jj.toJson(fields);
//        jj.toJson(sts);
        return fs;
    }
}
