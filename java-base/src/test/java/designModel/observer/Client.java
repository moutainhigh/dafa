package designModel.observer;

public class Client {
    /**
     * 观察者 模式
     */
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditions currentConditions = new CurrentConditions();

        weatherData.registerObserver(currentConditions);
        weatherData.setData(10, 11, 12);

        weatherData.removeObserver(currentConditions);

        BaiduSite baiduSite = new BaiduSite();
        weatherData.registerObserver(baiduSite);
        weatherData.setData(20, 21, 22);
    }
}
