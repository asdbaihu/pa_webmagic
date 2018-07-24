package crawler;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class test {
    public static void main(String[] args) throws Exception{
        hanDecode();


    }
    public static void hanDecode() throws Exception{
        String ed = "12345625E812345625BD12345625A612345625E512345625851234562585/12345625E51234562585123456258512345625E7123456259412345625B512345625E5123456259912345625A8";
        String ed2 = ed.replaceAll("123456","%");
        String ed3 =URLDecoder.decode(ed2,"UTF-8");

//        System.out.println(ed2);
//        System.out.println(ed3);
        System.out.println(URLDecoder.decode(ed3,"UTF-8"));
    }
    public static void decodeHan() throws Exception{
        String ed = "车充/充电器"; // 手机//12345625E812345625BD12345625A612345625E512345625851234562585/12345625E51234562585123456258512345625E7123456259412345625B512345625E5123456259912345625A8
        String sout = "";
        if(ed.contains("/")){
            String[] sts = ed.split("/");
            for (int i = 0; i <sts.length ; i++) {
                sout+= URLEncoder.encode(sts[i], "UTF-8");
                sout+="/";

            }
            sout = sout.substring(0,sout.length()-1);
        }else {
            sout = URLEncoder.encode(ed, "UTF-8");

        }

        System.out.println(sout.replaceAll("%","123456"));
//        String ed1 = URLEncoder.encode(ed, "UTF-8");
//        System.out.println(ed1);
//        System.out.println(ed1.replaceAll("%","123456"));
//        String ed2 = URLDecoder.decode(ed1,"UTF-8");
//        System.out.println(ed2.replaceAll("%","123456"));

    }
}
