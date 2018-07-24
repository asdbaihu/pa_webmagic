package crawler4jZssh;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ipSpider {
    public static void main(String[] args) throws Exception{

        List<IPMessage> ipMessages = urlParse3();  //获得ip
//        ipMessages = IPFilter.Filtertype(ipMessages); //http
//        System.err.println(ipMessages.size());
        System.err.println(ipMessages.size());
    }

    public static List<IPMessage> urlParse3() throws Exception{
        String url = "https://github.com/fate0/proxylist/blob/master/proxy.list";
        HttpClientBuilder bulider = HttpClients.custom();
        CloseableHttpClient client = bulider.build();

        HttpGet request3 = new HttpGet(url );
        //Thread.sleep(50);
        CloseableHttpResponse reponse3 = client.execute(request3);
        HttpEntity entity3 = reponse3.getEntity();
        String result3 = EntityUtils.toString(entity3, "gb2312");
        Document document = Jsoup.parse(result3);
        Elements els0  =  document.select("table[class=highlight tab-size js-file-line-container]").select("tbody").select("tr");
        // Elements els =  document.getElementsByClass("highlight tab-size js-file-line-container");
        List<IPMessage> ipMessages = new ArrayList<IPMessage>();
        for (int i = 0; i <els0.size() ; i++) {
            IPMessage ipMessage = new IPMessage();
            JSONObject jsonObject = new JSONObject(els0.get(i).select("td").get(1).text());
            String ipAddress = jsonObject.getString("host").toString();
            String ipPort = String.valueOf(jsonObject.getInt("port"));
            String ipType = jsonObject.getString("type").toString();
            String ipSpeed = String.valueOf(jsonObject.getDouble("response_time"));

            double Speed = Double.parseDouble(ipSpeed);
            if (ipType.equals("http") && Speed<=2) {
                ipMessage.setIPAddress(ipAddress);
                ipMessage.setIPPort(ipPort);
                ipMessage.setIPType(ipType);
                ipMessage.setIPSpeed(ipSpeed);

                ipMessages.add(ipMessage);
                System.out.println(jsonObject.getString("type") +" " + jsonObject.getString("host") +"  " +jsonObject.getInt("port")+ "   " +jsonObject.getDouble("response_time"));
            }


            // System.out.println(els0.get(i).select("td").get(1).text());

        }
        System.err.println(ipMessages.size());
        return ipMessages;
    }
}
