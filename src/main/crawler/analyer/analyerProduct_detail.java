package crawler.analyer;

import crawler.util.crawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import seleniumJava.testPost2;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class analyerProduct_detail {
    public static void main(String[] args) throws Exception{
        rw();
    }
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
                StringBuffer stbs = new StringBuffer();
                stbs.append( line.split("##")[0]+"##"+ line.split("##")[1]);
                //System.out.println(line);
                Map<String, List<String>> map = new HashMap();
                String pg = line.split("##")[4];
                System.out.println(line.split("##")[1]+"  "+line.split("##")[0]);
                stbs.append(analy(pg.replaceAll("\\\\\"", "\"")));
                //analyerProduct_post
                System.out.println(stbs.toString());
                System.out.println("==============================");
            }
        }
    }
    public static  StringBuffer  analy(String page){
        StringBuffer sbs2 = new StringBuffer();
        Document newdoc = Jsoup.parse( page, "UTF-8");
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
            StringBuffer sb = crawlerUtil.getAhref(ur);
            System.out.println(sb);


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
            System.out.println();

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
                        sbs.append("##"+pid);
                       // sbs.append(analyerProduct_post.test(pid));
                        //fields.put("tzid",pid);
                        //fieldmap = testPost2.test(pid);
                    }
                    //fields.put("state",cf.text().toString());
                    System.out.println("state  " +cf.text().toString());
                    sbs.append("##"+cf.text());
                }
            }
            sbs2.append(sbs);
        }
        return sbs2;

//        System.out.println();
//        Json jj = new Json();
//        jj.toJson(fields);
//        jj.toJson(sts);
       //return fs;
    }
}
