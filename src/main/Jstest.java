import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Jstest {


    public static String getAjaxCotnent(String url) throws IOException {

//  调用命令行运行phantomjs来执行s.js文件，这里的路径需要写全，否则是没有办法运行的，具体原因就不去考察了
//  通过此方法返回的就是把AJAX页面完全加载之后的浏览器的内容，以字符串的形式返回

        Runtime rt = Runtime.getRuntime();

        Process p = rt.exec("D:\\runajian\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe D:\\work\\java\\phantomjstest.js "+url);

        InputStream is = p.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuffer sbf = new StringBuffer();

        String tmp = "";

        while((tmp = br.readLine())!=null){

            sbf.append(tmp);
        }
        System.out.println(sbf.toString());

        return sbf.toString();
    }
    public static void main(String[] args) throws Exception{
        System.out.println(getAjaxCotnent("https://ssl.mall.cmbchina.com/_CL5_/Category/GetAllCategories?channel=_CL5_&pushwebview=1"));

    }

}