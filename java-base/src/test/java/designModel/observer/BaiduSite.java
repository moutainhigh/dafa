package designModel.observer;

public class BaiduSite implements Observer {
    private float temperatrue;
    private float pressure;
    private float humidity;

    public void update(float temperatrue, float pressure, float humidity) {
        this.temperatrue = temperatrue;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }


    public void display() {
        System.out.println("百度 temperatrue：" + temperatrue);
        System.out.println("百度 pressure：" + pressure);
        System.out.println("百度 humidity：" + humidity);
    }
}
