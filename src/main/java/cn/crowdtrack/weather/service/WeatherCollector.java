package cn.crowdtrack.weather.service;

import cn.crowdtrack.weather.domain.Weather;
import cn.crowdtrack.weather.tools.DateTools;
import cn.crowdtrack.weather.tools.LogTools;
import cn.crowdtrack.weather.dao.WeatherSQL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;


public class WeatherCollector {
    public static void getWeather() {
        //为什么迭代，因为他分三层，每一层都有特殊情况
        parserXml_china("china");
    }

    public static void parserXml_china(String keywords) {
        String uri = "http://flash.weather.com.cn/wmaps/xml/" + keywords + ".xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
             Document document = builder.parse(uri);
            Element root = document.getDocumentElement();//获取根元素
            //System.out.println(root);
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    //System.out.printf(((Element) node).getAttribute("cityname"));//节点属性
                    String aeraEn = ((Element) node).getAttribute("pyName");
                    //System.out.println(node.getNodeName() + " " + node.getTextContent());//节点名称
                    //解析出来的节点里面，西沙xisha，南沙nanshadao，钓鱼岛diaoyudao是特殊区域。他不是34省直辖市自治区的一员
                    if (aeraEn.equals("xisha")) {
                        System.out.println("解析到特殊地点：西沙");
                    } else if (aeraEn.equals("nanshadao")) {
                        System.out.println("解析到特殊地点：南沙岛");
                    } else if (aeraEn.equals("diaoyudao")) {
                        System.out.println("解析到特殊地点：钓鱼岛");
                    } else {
                        System.out.println(aeraEn);//节解析到的是省
                        parserXml_provice(aeraEn);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void parserXml_provice(String keywords) {
        String uri = "http://flash.weather.com.cn/wmaps/xml/" + keywords + ".xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(uri);
            Element root = document.getDocumentElement();//获取根元素
            //System.out.println(root);
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    String aeraEn = ((Element) node).getAttribute("pyName");
                    //System.out.println(node.getNodeName() + " " + node.getTextContent());//节点名称
                    //解析出来的节点里面，西沙xisha，南沙nanshadao，钓鱼岛diaoyudao是特殊区域。他不是34省直辖市自治区的一员
                    String proviceName = keywords;
                    String cityName = aeraEn;
                    String countyName = "";
                    String stateDetailed = ((Element) node).getAttribute("stateDetailed");
                    String tem1 = ((Element) node).getAttribute("tem1");
                    String tem2 = ((Element) node).getAttribute("tem2");
                    String temNow = ((Element) node).getAttribute("temNow");
                    String windState = ((Element) node).getAttribute("windState");
                    String windDir = ((Element) node).getAttribute("windDir");
                    String windPower = ((Element) node).getAttribute("windPower");
                    String humidity = ((Element) node).getAttribute("humidity");
                    String time = ((Element) node).getAttribute("time");
                    Date today_date = new Date();
                    String today_str = DateTools.dateToStr(today_date,"yyyy-MM-dd");// 2018-09-20
                    String url = ((Element) node).getAttribute("url");

                    Weather weather = new Weather();
                    weather.setProviceName(proviceName);
                    weather.setCityName(cityName);
                    weather.setCountyName(countyName);
                    weather.setStateDetailed(stateDetailed);
                    weather.setTem1(tem1);
                    weather.setTem2(tem2);
                    weather.setTemNow(temNow);
                    weather.setWindState(windState);
                    weather.setWindDir(windDir);
                    weather.setWindPower(windPower);
                    weather.setHumidity(humidity);
                    weather.setTime(time);
                    weather.setDate(today_str);
                    weather.setUrl(url);

                    WeatherSQL.insert(weather);

                    //LogTools.writeLog(weather.toString());
                    //System.out.println(weather.toString());

                    if (keywords.equals("beijing")) {
                        System.out.println("解析到特殊地点：北京:" + aeraEn);
                    } else if (keywords.equals("tianjin")) {
                        System.out.println("解析到特殊地点：天津:" + aeraEn);
                    } else if (keywords.equals("shanghai")) {
                        System.out.println("解析到特殊地点：上海:" + aeraEn);
                    } else if (keywords.equals("chongqing")) {
                        System.out.println("解析到特殊地点：重庆:" + aeraEn);
                    } else if (keywords.equals("hainan")) {
                        System.out.println("解析到特殊地点：海南:" + aeraEn);
                    } else if (keywords.equals("xianggang")) {
                        System.out.println("解析到特殊地点：香港:" + aeraEn);
                    } else if (keywords.equals("aomen")) {
                        System.out.println("解析到特殊地点：澳门:" + aeraEn);
                    } else if (keywords.equals("taiwan")) {
                        System.out.println("解析到特殊地点：台湾:" + aeraEn);
                    } else {
                        System.out.println(keywords + ":" + aeraEn);//节点属性
                        parserXml_city(aeraEn, proviceName);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void parserXml_city(String keywords, String provice) {
        String uri = "http://flash.weather.com.cn/wmaps/xml/" + keywords + ".xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(uri);
            Element root = document.getDocumentElement();//获取根元素
            //System.out.println(root);
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    String aeraEn = ((Element) node).getAttribute("pyName");
                    //System.out.println(node.getNodeName() + " " + node.getTextContent());//节点名称
                    //解析出来的节点里面，西沙xisha，南沙nanshadao，钓鱼岛diaoyudao是特殊区域。他不是34省直辖市自治区的一员
                    String proviceName = provice;
                    String cityName = keywords;
                    String countyName = aeraEn;
                    String stateDetailed = ((Element) node).getAttribute("stateDetailed");
                    String tem1 = ((Element) node).getAttribute("tem1");
                    String tem2 = ((Element) node).getAttribute("tem2");
                    String temNow = ((Element) node).getAttribute("temNow");
                    String windState = ((Element) node).getAttribute("windState");
                    String windDir = ((Element) node).getAttribute("windDir");
                    String windPower = ((Element) node).getAttribute("windPower");
                    String humidity = ((Element) node).getAttribute("humidity");
                    String time = ((Element) node).getAttribute("time");
                    Date today_date = new Date();
                    String today_str = DateTools.dateToStr(today_date,"yyyy-MM-dd");// 2018-09-20
                    String url = ((Element) node).getAttribute("url");

                    Weather weather = new Weather();
                    weather.setProviceName(proviceName);
                    weather.setCityName(cityName);
                    weather.setCountyName(countyName);
                    weather.setStateDetailed(stateDetailed);
                    weather.setTem1(tem1);
                    weather.setTem2(tem2);
                    weather.setTemNow(temNow);
                    weather.setWindState(windState);
                    weather.setWindDir(windDir);
                    weather.setWindPower(windPower);
                    weather.setHumidity(humidity);
                    weather.setTime(time);
                    weather.setDate(today_str);
                    weather.setUrl(url);

                    WeatherSQL.insert(weather);

                    //LogTools.writeLog(weather.toString());
                    //System.out.println(weather.toString());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
