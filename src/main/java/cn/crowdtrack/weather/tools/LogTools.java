package cn.crowdtrack.weather.tools;

import cn.crowdtrack.weather.tools.DateTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class LogTools {
    public static void writeLog(String message)  {
        Date date = new Date();
        String ymd = DateTools.dateToStr(date,"yyyy-MM-dd");// 2018-09-20
        String hmsS = DateTools.dateToStr(date,"HH:mm:ss SSS ");// 12:00:00

        String str_log = ymd + " " + hmsS + " " + message + "\r\n";
        System.out.print(str_log);
        try {
            File file = new File("log");
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            String log_name = "log\\" + ymd + ".log";
            RandomAccessFile randomFile = new RandomAccessFile(log_name, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(str_log);
            randomFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
