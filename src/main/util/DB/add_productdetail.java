package util.DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * productdetail添加数据
 */
public class add_productdetail {
    public static void main(String[] args) throws Exception{
        File fileIn = new File("D:\\work\\java\\WebMagic\\src\\main\\crawler\\out\\prodetialpage0722.txt");
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
        BufferedReader bin = new BufferedReader(inStream);
        String line ="";
        DB_Helper db = new DB_Helper();
        Connection connect= db.connect;
        while ((line = bin.readLine()) != null  ) {
            if(!"".equals(line)) {
                System.out.println(line);
                String[] st = line.split("#+");
                String productId = st[3];
                String categoryId = st[0];
                String url_post = st[2];
                String page = st[4];
                add_productdetail(connect,productId,categoryId,url_post,page);

            }
        }
        connect.close();

    }





    //插入操作
    public static void add_productdetail(Connection connect,String productId, String categoryId, String url_post, String page) {
        String sql="insert into product_desc (productId,categoryId,url_post,page) values (?,?,?,?)";
//        DB_Helper db = new DB_Helper();
//        Connection connect= db.connect;
        PreparedStatement preStmt = null;
        try {
            preStmt = (PreparedStatement) connect.prepareStatement(sql);
            preStmt.setString(1, productId);
            preStmt.setString(2, categoryId);
            preStmt.setString(3, url_post);
            preStmt.setString(4,page);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
