import cn.crowdtrack.weather.dao.WeatherSQL;
import cn.crowdtrack.weather.service.WeatherCollector;
import cn.crowdtrack.weather.tools.LogTools;

import java.util.Timer;
import java.util.TimerTask;

public class main {
    public static class TimerTaskWork extends TimerTask {
        public void run() {
            System.out.println("Time's up!!!!");
            LogTools.writeLog("wake up!!!!");
            WeatherSQL.create_partition();
            WeatherCollector.getWeather();
        }
    }

    public static void main(String[] args) {
        System.out.println("timer begin....");
        Timer timer = new Timer();
        timer.schedule(new TimerTaskWork(), 0,120 * 60 * 1000);
    }
}
