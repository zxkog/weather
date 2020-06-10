package cn.crowdtrack.weather.tools;

import cn.crowdtrack.weather.tools.DateTools;
import cn.crowdtrack.weather.tools.LogTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class HtmlTools {
    public static String getHtml(String keywords, String parent) {
        String path = "http://flash.weather.com.cn/wmaps/xml/" + keywords + ".xml";
        String xmlStr = null;

        try {
            URL realUrl = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();//初始化一个连接
            connection.setConnectTimeout(1 * 1000);//设置超时时间
            connection.setRequestMethod("GET");//设置请求类型GET
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");//模拟浏览器，防屏蔽

            InputStream htmlStr = connection.getInputStream();//输出流

            //先把要用的目录建好
            Date today_date = new Date();
            String filePath = System.getProperty("user.dir") + "/rec/";
            File saveDir = new File(filePath);//是否需要创建文件夹
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            filePath = System.getProperty("user.dir") + "/rec/" + DateTools.dateToStr(today_date,"yyyymmdd") + "/";
            saveDir = new File(filePath);//是否需要创建文件夹
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + keywords + DateTools.dateToStr(today_date,"hhmm") + ".xml");

            FileOutputStream out = new FileOutputStream(file);//实例输出一个对象

            byte[] bs = new byte[20480];//控制流的大小为20k
            int len = 0;//读取到的长度
            //循环判断，如果读取的个数b为空了，则is.read()方法返回-1，具体请参考InputStream的read();
            while ((len = htmlStr.read(bs)) != -1) {
                //将对象写入到对应的文件中
                out.write(bs, 0, len);
            }
            xmlStr = new String(bs);
            //刷新流
            out.flush();
            //关闭流
            out.close();
            htmlStr.close();
            connection.connect();

            LogTools.writeLog(parent + "/" + keywords + ":" + "download succeeded");

        } catch (MalformedURLException e) {
            LogTools.writeLog(parent + "/" + keywords + ":" + "download failed");
            e.printStackTrace(System.err);
        } catch (IOException e) {
            LogTools.writeLog(parent + "/" + keywords + ":" + "download failed");
            e.printStackTrace(System.err);
        }
        return xmlStr;

    }
}
