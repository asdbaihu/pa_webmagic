package util.DB;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONArray;
import org.openqa.selenium.json.Json;
import util.GetAllfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;

public class addproducts {
    public static void main(String[] args) throws Exception {
        DB_Helper db = new DB_Helper();
        Connection connect= db.connect;
        LinkedList<File> fileList =GetAllfile.GetDirectory("D:\\data\\productlist");

            File fileIn = new File("D:\\data\\allsave3.txt");
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader bin = new BufferedReader(inStream);
            String line ="";
            String filename = fileIn.getName();
            //String productId = filename.split("-")[0];
            while ((line = bin.readLine()) != null  ) {
                if(!"".equals(line)) {

                    String name = line.split("##")[0];
                    String detail = line.split("##")[1];
                    //JSONObject jsob = JSONObject.parseObject(detail);
                    JSONArray ja = new JSONArray();
                   // JSONObject jo =  JSON.parseObject(detail);
                    com.alibaba.fastjson.JSONArray jaa =  JSON.parseArray(detail);
                    System.out.println();
                   // add_productlist(connect,productId,webpage);
                }
            }

        connect.close();
    }

    //插入操作
    public static void add_productlist(Connection connect, String productId, String webpage) {
        String sql = "insert into productlistentity (productId,webpage) values (?,?)";
        PreparedStatement preStmt = null;
        try {
            preStmt = (PreparedStatement) connect.prepareStatement(sql);
            preStmt.setString(1, productId);
            preStmt.setString(2, webpage);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
