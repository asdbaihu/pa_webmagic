package util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class get_productlist {
    public static void main(String[] args){
        getAll();
    }
    private static Integer getAll() {
        DB_Helper db = new DB_Helper();
        Connection conn= db.connect;
        String sql = "select * from productlistentity";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
             /*   for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }*/
                //String id = rs.get("id");
                String productId = rs.getString("productId");
                String webpage = rs.getString("webpage");
                //可以将查找到的值写入类，然后返回相应的对象
                //这里 先用输出的端口显示一下
                System.out.println( productId +"\t"+ webpage);
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
