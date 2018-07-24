package util.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DB_Helper {

    public static Connection connect = null;
    private static String addr;
    private static int port;
    private static String user;
    private static String passwd;

    //加载配置文件
    private static ResourceBundle sqldb = ResourceBundle.getBundle("db-config");

    //初始化连接
    static {
        addr = sqldb.getString("mysql.addr");
        port = Integer.parseInt(sqldb.getString("mysql.port"));
        passwd = sqldb.getString("mysql.passwd");
        user = sqldb.getString("mysql.user");

    }
        static {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
            connect = DriverManager.getConnection(
                    "jdbc:mysql://"+addr+":"+port+"/pa_zssh_web?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8", user, passwd);

            System.out.println("Success loading Mysql Driver!");
        } catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connect;
    }
    public  void close() throws Exception{
        if (connect != null) {
            connect.close();
        }
    }

}
