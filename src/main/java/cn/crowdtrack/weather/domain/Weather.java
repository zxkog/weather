package cn.crowdtrack.weather.domain;


public class Weather {
    private String flag;//标记地区等级直辖市下辖区，地级市，县级市

    private String proviceName;
    private String cityName;
    private String countyName;

    private String stateDetailed;
    private String tem1;
    private String tem2;
    private String temNow;
    private String windState;
    private String windDir;
    private String windPower;
    private String humidity;
    private String time;
    private String date;
    private String url;

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setStateDetailed(String stateDetailed) {
        this.stateDetailed = stateDetailed;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public void setTemNow(String temNow) {
        this.temNow = temNow;
    }

    public void setWindState(String windState) {
        this.windState = windState;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

////////////////////////////////////////////////////

    public String getProviceName() {
        return proviceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getStateDetailed() {
        return stateDetailed;
    }

    public String getTem1() {
        return tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public String getTemNow() {
        return temNow;
    }

    public String getWindState() {
        return windState;
    }

    public String getWindDir() {
        return windDir;
    }

    public String getWindPower() {
        return windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    //public void setPassword(String password) {
    //    this.password = password == null ? null : password.trim();
    //}

    @Override
    public String toString() {
        return "weatherInfo [url=" + url + ", proviceName=" + proviceName + ", cityName="
                + cityName + ", countyName=" + countyName + ", stateDetailed=" + stateDetailed + ", tem1=" + tem1 + ", tem2="
                + tem2 + ", temNow=" + temNow + ", windState=" + windState + ", windDir=" + windDir + ", windPower="
                + windPower + ", humidity=" + humidity + ", time=" + time + ", date=" + date + "]";
    }
}
