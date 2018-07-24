package util.DB;

import util.FileOperation;
import util.GetAllfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

public class add_productlistEntity {
    public static void main(String[] args) throws Exception {
        DB_Helper db = new DB_Helper();
        Connection connect= db.connect;
        LinkedList<File> fileList =GetAllfile.GetDirectory("D:\\data\\productlist");
      /*  for (File file : fileList) {
            System.out.println(file.getName());
        }*/
       // for (File file : fileList) {
            File fileIn = new File("D:\\work\\java\\WebMagic\\src\\main\\crawler\\out\\allpage0722.txt");
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader bin = new BufferedReader(inStream);
            String line ="";
            String filename = fileIn.getName();
            String productId = filename.split("-")[0];
            while ((line = bin.readLine()) != null  ) {
                if(!"".equals(line)) {
                    String webpage = line.split("##")[0];
                    String categoryId = webpage.substring( webpage.indexOf("=")+1, webpage.indexOf("&"));
                    String url = line.split("##")[0];
                    String page = line.split("##")[1];
                    add_productlist(connect,categoryId,url,page);
                }
            }
       // }
        connect.close();
    }

    //插入操作
    public static void add_productlist(Connection connect, String categoryId, String url,String page) {
        String sql = "insert into product_page (categoryId,url,page) values (?,?,?)";
        PreparedStatement preStmt = null;
        try {
            preStmt = (PreparedStatement) connect.prepareStatement(sql);
            preStmt.setString(1, categoryId);
            preStmt.setString(2, url);
            preStmt.setString(3, page);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
