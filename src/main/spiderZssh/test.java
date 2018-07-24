package spiderZssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//https://ssl.mall.cmbchina.com/_cl4_/Product/Detail?productCode=S1H-40T-0TB_026&pushwebview=1&productIndex=1
public class test {
    //
    //seleniumJava
    public static void main(String[] args) throws Exception{
      String path = System.getProperty("user.dir");
        File fileIn = new File(path+"\\src\\main\\seleniumJava\\1.txt");
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
        BufferedReader bin = new BufferedReader(inStream);
        String line ="";

        while ((line = bin.readLine()) != null  ) {
            if(!"".equals(line)) {
                System.out.println(line);


            }
        }

    }
}
