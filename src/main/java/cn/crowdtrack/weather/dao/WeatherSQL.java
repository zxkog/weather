package cn.crowdtrack.weather.dao;

import cn.crowdtrack.weather.domain.Weather;
import cn.crowdtrack.weather.tools.DateTools;

import java.sql.*;
import java.util.Date;

public class WeatherSQL {
    /*
    Statement 接口提供了三种执行 SQL 语句的方法：executeQuery、executeUpdate 和 execute。使用哪一个方法由 SQL 语句所产生的内容决定。
方法 executeQuery 用于产生单个结果集的语句，例如 SELECT 语句。
方法 executeUpdate 用于执行 INSERT、UPDATE 或 DELETE 语句以及 SQL DDL（数据定义语言）语句，例如 CREATE TABLE 和 DROP TABLE。INSERT、UPDATE 或 DELETE 语句的效果是修改表中零行或多行中的一列或多列。executeUpdate 的返回值是一个整数，指示受影响的行数（即更新计数）。对于 CREATE TABLE 或 DROP TABLE 等不操作行的语句，executeUpdate 的返回值总为零。
方法 execute 用于执行返回多个结果集、多个更新计数或二者组合的语句。因为多数程序员不会需要该高级功能，所以本概述后面将在单独一节中对其进行介绍。
执行语句的所有方法都将关闭所调用的 Statement 对象的当前打开结果集（如果存在）。这意味着在重新执行 Statement 对象之前，需要完成对当前 ResultSet 对象的处理。
     */


    static final String DB_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/cts_weather";
    static final String DB_USER = "cts_weather";
    static final String DB_PASS = "CTS2020@weather";

    public static void insert(Weather weather) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //第一步：注册驱动
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //第二步：建立连接
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Date today_date = new Date();
        String today = DateTools.dateToStr(today_date, "yyyyMMdd");
        String sql = "SELECT count(*) FROM public.weather_rec_" + today + " WHERE url='" + weather.getUrl() + "' AND date = '" + weather.getDate() + "'";
        //String sql = "INSERT INTO public.weather_rec (url,statedetailed,tem1,tem2,temNow,windstate,winddir,windpower,humidity,date,time) VALUES ('" + weather.getUrl() + "','" + weather.getStateDetailed() + "','" + weather.getTem1() + "','" + weather.getTem2() + "','" + weather.getTemNow() + "','" + weather.getWindState() + "','" + weather.getWindDir() + "','" + weather.getWindPower() + "','" + weather.getHumidity() + "','" + weather.getDate() + "','" + weather.getTime() + "')";
        //String sql = "insert into schema_weather.weatherrec values (\"123\")";
        //String sql = "select * from schema_weather.weatherrec";
        System.out.println(sql);

        //第三步：打开statement对象,插入数据用createStatement
        try {
            statement = connection.createStatement();
            //statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //第四步：执行sql语句
        try {
            resultSet = statement.executeQuery(sql);
            //statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //第五步：处理查询结果
        try {
            resultSet.next();
            //取出列值
            String columnCount = resultSet.getString(1);
            System.out.println("columnCount:" + columnCount);
            if (columnCount.equals("0")) {
                sql = "INSERT INTO public.weather_rec_" + today + " (url,statedetailed,tem1,tem2,temNow,windstate,winddir,windpower,humidity,date,time) VALUES ('" + weather.getUrl() + "','" + weather.getStateDetailed() + "','" + weather.getTem1() + "','" + weather.getTem2() + "','" + weather.getTemNow() + "','" + weather.getWindState() + "','" + weather.getWindDir() + "','" + weather.getWindPower() + "','" + weather.getHumidity() + "','" + weather.getDate() + "','" + weather.getTime() + "')";
                System.out.println(sql);
                try {
                    statement.executeUpdate(sql);//用于执行 INSERT、UPDATE 或 DELETE
                    //resultSet = statement.executeUpdate(sql);//用于执行 SELECT
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sql = "UPDATE public.weather_rec_" + today + " SET statedetailed='" + weather.getStateDetailed() + "',tem1='" + weather.getTem1() + "',tem2='" + weather.getTem2() + "',temNow='" + weather.getTemNow() + "',windstate='" + weather.getWindState() + "',winddir='" + weather.getWindDir() + "',windpower='" + weather.getWindPower() + "',humidity='" + weather.getHumidity() + "',time='" + weather.getTime()+ "',chg_time='" + today_date + "' WHERE url='" + weather.getUrl() + "' AND date = '" + weather.getDate() + "'";
                System.out.println(sql);
                try {
                    statement.executeUpdate(sql);//用于执行 INSERT、UPDATE 或 DELETE
                    //resultSet = statement.executeUpdate(sql);//用于执行 SELECT
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void create_partition() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //第一步：注册驱动
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //第二步：建立连接
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //String sql = "INSERT INTO schema_weather.weatherrec (url,statedetailed,tem1,tem2,temNow,windstate,winddir,windpower,humidity,date,time) VALUES ('" + weather.getUrl() + "','" + weather.getStateDetailed() + "','" + weather.getTem1() + "','" + weather.getTem2() + "','" + weather.getTemNow() + "','" + weather.getWindState() + "','" + weather.getWindDir() + "','" + weather.getWindPower() + "','" + weather.getHumidity()+ "','" + weather.getDate()+ "','" + weather.getTime() + "')";
        //String sql = "insert into schema_weather.weatherrec values (\"123\")";

        //String sql = "select * from schema_weather.weatherrec";
        Date today_date = new Date();
        String today = DateTools.dateToStr(today_date, "yyyyMMdd");
        String today_str = DateTools.dateToStr(today_date, "yyyy-MM-dd") + " 00:00:00";// 20180920
        String tomorrow_str = DateTools.dateToStr_tomorrow(today_date, "yyyy-MM-dd") + " 00:00:00";// 20180920
        String sql = "select count(*) from pg_class where relname = 'weather_rec_" + today + "'";
        //String sql = "INSERT INTO schema_weather.weatherrec (url,statedetailed,tem1,tem2,temNow,windstate,winddir,windpower,humidity,date,time) VALUES ('" + weather.getUrl() + "','" + weather.getStateDetailed() + "','" + weather.getTem1() + "','" + weather.getTem2() + "','" + weather.getTemNow() + "','" + weather.getWindState() + "','" + weather.getWindDir() + "','" + weather.getWindPower() + "','" + weather.getHumidity()+ "','" + weather.getDate()+ "','" + weather.getTime() + "')";
        //String sql = "insert into schema_weather.weatherrec values (\"123\")";
        //String sql = "select * from schema_weather.weatherrec";
        System.out.println(sql);

        //第三步：打开statement对象,插入数据用createStatement
        try {
            //
            statement = connection.createStatement();//
            //statement = connection.prepareStatement("insert into category values(null,?,?)";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //第四步：执行sql语句
        try {
            //resultSet1 = statement.executeUpdate(sql);//用于执行 INSERT、UPDATE 或 DELETE
            resultSet = statement.executeQuery(sql);//用于执行 SELECT
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //第五步：处理返回的结果
        try {
            resultSet.next();
            //取出列值
            String columnCount = resultSet.getString(1);
            System.out.println("columnCount:" + columnCount);
            if (columnCount.equals("0")) {
                /*
                CREATE TABLE public.weather_rec_20200608 PARTITION OF public.weather_rec
                FOR VALUES FROM ('2020-06-08 00:00:00') TO ('2020-06-09 00:00:00')
                TABLESPACE cts_weather;

                CREATE TABLE public.weather_rec_20200608 PARTITION OF public.weather_rec
                FOR VALUES FROM ('2020-06-08 00:00:00') TO ('2020-06-09 00:00:00');

CREATE TABLE public.weather_rec_20200609 PARTITION OF public.weather_rec
    FOR VALUES FROM ('2020-06-09 00:00:00') TO ('2020-06-10 00:00:00');

                 */
                sql = "CREATE TABLE public.weather_rec_" + today + " PARTITION OF public.weather_rec FOR VALUES FROM ('" + today_str + "') TO ('" + tomorrow_str + "') TABLESPACE cts_weather";
                System.out.println(sql);
                try {
                    statement.executeUpdate(sql);//用于执行 INSERT、UPDATE 或 DELETE
                    //resultSet = statement.executeUpdate(sql);//用于执行 SELECT
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
