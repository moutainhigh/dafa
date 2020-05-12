package designModel.observer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherData implements Subject {
    private float temperatrue;
    private float pressure;
    private float humidity;
    //CurrentConditions currentConditions;
    private List<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    public void setData(float temperatrue, float pressure, float humidity){
        this.temperatrue = temperatrue;
        this.pressure = pressure;
        this.humidity = humidity;
        dataChange();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if(observers.contains(o))
            observers.remove(o);
    }

    @Override
    public void notiifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this.temperatrue,this.pressure,this.humidity);
        }
    }
    /**
     * 通知
     * */
    public void dataChange() {
        notiifyObservers();
    }

}
