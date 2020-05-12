package designModel.observer;

public class CurrentConditions implements Observer {
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
        System.out.println("temperatrue：" + temperatrue);
        System.out.println("pressure：" + pressure);
        System.out.println("humidity：" + humidity);
    }
}
