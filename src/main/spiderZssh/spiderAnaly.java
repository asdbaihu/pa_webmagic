package spiderZssh;

import util.DB.DB_Helper;
import util.GetAllfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.LinkedList;

public class spiderAnaly {
    public static void main(String[] args) throws Exception{
        DB_Helper db = new DB_Helper();
        Connection connect= db.connect;
        LinkedList<File> fileList =GetAllfile.GetDirectory("D:\\data\\productlist");
      /*  for (File file : fileList) {
            System.out.println(file.getName());
        }*/
        for (File file : fileList) {
            File fileIn = new File(file.getAbsolutePath());
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader bin = new BufferedReader(inStream);
            String line ="";
            String filename = fileIn.getName();
            String productId = filename.split("-")[0];
            while ((line = bin.readLine()) != null  ) {
                if(!"".equals(line)) {
                    String webpage = line.split("##")[1];
                    //add_productlist(connect,productId,webpage);
                }
            }
        }
        connect.close();
    }
}
