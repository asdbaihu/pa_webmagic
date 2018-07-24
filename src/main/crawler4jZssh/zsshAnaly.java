package crawler4jZssh;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import seleniumJava.testPost2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class zsshAnaly {
    public static void main(String[] args) throws Exception{
        rw();
    }

    public static void rw() throws Exception {
        File fileIn= new File("D:\\data\\spider3.txt");
        File fileOut= new File("D:\\data\\analyProduct2.txt");

        InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");

        OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(fileOut),"UTF-8");
        BufferedReader bin = new BufferedReader(inStream);
        BufferedWriter bou = new BufferedWriter(writerStream);
        String line = "";
        while ((line = bin.readLine()) != null  ) {
            if(!"".equals(line)){
                System.out.println(line);
                Map<String,List<String>> map = new HashMap();
                Document newdoc = Jsoup.parse( line.replaceAll("\\\\\"","\""), "UTF-8");

            /*    //菜名
                Elements viewTitle = newdoc.getElementsByTag("h1");
                if(viewTitle.size()>0){
                    List<String> st = new ArrayList<>();
                    st.add(viewTitle.eachText().get(0));
                    map.put("name", st);
                    System.out.print("标题：  " );
                    System.out.print(viewTitle.eachText().get(0));
                }
                System.out.println();*/
                //类别
                Elements cx=   newdoc.getElementsByClass("group-buy-item");
                for (int i = 0; i <cx.size() ; i++) {
                    Element el = cx.get(i);
                    //h4
                    Elements h4= el.getElementsByTag("h4");
                    String h ="";
                    if(h4.size()>0){
                        h = h4.eachText().get(0);
                    }
                    System.out.println(h);
                    bou.write(h);
                    bou.write("##");//4个空格
                    //price
                    Elements price= el.getElementsByClass("price");
                    for (int j = 0; j <price.size() ; j++) {
                        Element pric = price.get(j);
                        Elements allprice = pric.getAllElements();
                        for (int k = 1; k <allprice.size() ; k++) {
                            Element pr = allprice.get(k);
                            System.out.print(pr.text().toString());
                            bou.write(pr.text().toString());
                            bou.write("##");//1个空格


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
                                testPost2.test(ats.get("productid"));
                            }

                            System.out.println(cf.text().toString());
                            bou.write(cf.text().toString());
                            bou.write("##");//1个空格
                        }
                    }
                    //根据key得到的
                    // Element subCategory = newdoc.getElementById("subCategory");
                    //        System.out.println(subCategory.attr("value"));

                    System.out.println();
                    bou.write("\r\n");

        /*            Element el = cx.get(i);
                    Elements els =   el.getElementsByTag("li");
                    System.out.print("菜系：  " );
                    List<String> st = new ArrayList<>();
                    for (int j = 4; j <els.eachText().size() ; j++) {
                        st.add(els.eachText().get(j).replaceAll("#",""));
                        System.out.print(els.eachText().get(j).replaceAll("#",""));
                        System.out.print("  " );
                    }
                    map.put("caixi", st);*/

                }
                System.out.println();

                // System.out.println(JSONObject.toJSONString(map));
               // bou.write(JSONObject.toJSONString(map));
                bou.write("\r\n");


/*
            Elements zy =  newdoc.getElementsByClass("yj_tags clearfix");
            List<String> s = zy.eachText();
            for (int i = 0; i <s.size() ; i++) {
                System.out.print("作用：  " );
                System.out.print(s.get(i));
            }*/
            }

            bou.flush();

        }
        bou.close();
    }
}
