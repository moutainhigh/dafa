package designModel.improve;

/***
 * 策略模式
 * */
public class BjDuck extends Duck {

    public BjDuck() {
        flyBehavior = new GoodFlyBehavior();
    }
}
